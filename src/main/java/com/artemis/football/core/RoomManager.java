package com.artemis.football.core;

import com.artemis.football.common.ActionType;
import com.artemis.football.connector.IBaseConnector;
import com.artemis.football.connector.SessionManager;
import com.artemis.football.model.BasePlayer;
import com.artemis.football.model.MatchRoom;
import com.artemis.football.model.Message;
import com.artemis.football.model.MessageFactory;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author zhengenshen
 * @date 2018-05-23 16:25
 */
@Slf4j
public class RoomManager {

    public static final int FIVE = 5;
    public static final int TEN = 10;

    /**
     * 5钻区的玩家队列
     */
    public static final LinkedBlockingQueue<BasePlayer> FIVE_ROOM = new LinkedBlockingQueue<>();

    /**
     * 10钻区的玩家队列
     */
    public static final LinkedBlockingQueue<BasePlayer> TEN_ROOM = new LinkedBlockingQueue<>();

    /**
     * 用来保存双人房间的
     */
    private static final ConcurrentHashMap<Integer, MatchRoom> ROOMS = new ConcurrentHashMap<>();

    /**
     * 为两位玩家创建房间
     */
    public static void createRoom(MatchRoom matchRoom) {
        ROOMS.put(matchRoom.getId(), matchRoom);
        log.info("房间时****************{}", matchRoom.getPlayers());
    }


    /**
     * 查询房间
     *
     * @param id 房间号
     * @return 房间
     */
    public static MatchRoom getRoom(int id) {
        return ROOMS.getOrDefault(id, null);
    }

    /**
     * 移除房间
     *
     * @param id 房间号
     */
    public static void removeRoom(int id) {
        ROOMS.remove(id);
    }

    /**
     * 存入匹配房间队列
     *
     * @param player 玩家
     * @param type   房间类型
     */
    public static void matching(BasePlayer player, int type) {
        switch (type) {
            case FIVE:
                FIVE_ROOM.offer(player);
                break;
            case TEN:
                TEN_ROOM.offer(player);
                break;
            default:
                break;
        }
    }

    public static int size(int type) {
        int size = 0;
        switch (type) {
            case FIVE:
                size = FIVE_ROOM.size();
                break;
            case TEN:
                size = TEN_ROOM.size();
                break;
            default:
                break;
        }
        return size;
    }

    public static LinkedBlockingQueue<BasePlayer> getQueue(int type) {
        LinkedBlockingQueue<BasePlayer> queue = null;
        switch (type) {
            case FIVE:
                queue = FIVE_ROOM;
                break;
            case TEN:
                queue = TEN_ROOM;
                break;
            default:
                break;
        }
        return queue;
    }

    /**
     * 用户退出或掉线时触发
     * 去连接attr里查询用户当前房间号
     * 如果用户有房间的话，通知匹配的玩家
     * 移除房间
     *
     * @param ch 连接
     */
    public static void quit(Channel ch) {

        if (ch.hasAttr(IBaseConnector.ROOM_ID)) {
            int roomId = ch.attr(IBaseConnector.ROOM_ID).get();
            MatchRoom mr = getRoom(roomId);
            Message m = MessageFactory.success(ActionType.PLAYER_QUIT);

            //取出当前掉线用户的id
            if (ch.hasAttr(IBaseConnector.USER)) {
                int uid = ch.attr(IBaseConnector.USER).get().getId();
                //房间中筛选出另外一个用户，通知他掉线了。
                if (mr != null && mr.getPlayers() != null) {
                    mr.getPlayers().entrySet().stream()
                            .filter(entry -> entry.getKey() != uid)
                            .forEach(entry -> {
                                Channel channel = entry.getValue().getChannel();
                                if (channel != null) {
                                    channel.writeAndFlush(m);
                                } else {
                                    // SessionManager.userIdChannels.get(entry.getValue().getId());
                                    Channel channel1 = SessionManager.getChannels().get(entry.getValue().getId());
                                    log.info("使用备用方案，从userChannel里拿channel {}", channel1);
                                    channel1.writeAndFlush(m);

                                }
                            });
                }
            }

            removeRoom(roomId);
        }
    }


    /**
     * 创建房间
     *
     * @param player1 用户1
     * @param player2 用户2
     * @param score   积分
     * @return 房间
     */
    public static MatchRoom create(BasePlayer player1, BasePlayer player2, int score) {

        if (player1 != null && player2 != null) {
            Integer id = Math.abs(player1.hashCode() + player2.hashCode());
            player1.setRoomId(id);
            player2.setRoomId(id);
            MatchRoom matchRoom = new MatchRoom();
            matchRoom.setScore(score);
            matchRoom.getPlayers().put(player1.getId(), player1);
            matchRoom.getPlayers().put(player2.getId(), player2);
            matchRoom.setId(id);

            try {
                Message m = MessageFactory.success(ActionType.MATCH_SUCCESS, matchRoom);
                player1.getChannel().attr(IBaseConnector.ROOM_ID).set(id);
                player2.getChannel().attr(IBaseConnector.ROOM_ID).set(id);
                player1.getChannel().writeAndFlush(m);
                player2.getChannel().writeAndFlush(m);
            } catch (Exception e) {
                e.printStackTrace();
            }

            RoomManager.createRoom(matchRoom);
            return matchRoom;
        }
        return null;

    }

    public static void createRoom(int type) {

        LinkedBlockingQueue<BasePlayer> queue = getQueue(type);
        if (queue != null) {
            int size = queue.size();
            if (size >= 2 && size % 2 == 0) {
                synchronized (queue) {
                    if (queue.size() >= 2 && queue.size() % 2 == 0) {
                        BasePlayer player1 = queue.poll();
                        BasePlayer player2 = queue.poll();
                        create(player1, player2, type);
                    }
                }
            }
        }
    }

    public static void show() {
        ROOMS.values().forEach(v -> log.info("当前房间 : {}", v.getPlayers()));
    }
}
