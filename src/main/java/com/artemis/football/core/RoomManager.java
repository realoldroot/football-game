package com.artemis.football.core;

import com.artemis.football.common.ActionType;
import com.artemis.football.connector.IBaseConnector;
import com.artemis.football.model.BasePlayer;
import com.artemis.football.model.MatchRoom;
import com.artemis.football.model.Message;
import com.artemis.football.model.MessageFactory;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author zhengenshen
 * @date 2018-05-23 16:25
 */

public class RoomManager {

    public static final int FIVE = 5;
    public static final int TEN = 10;

    /**
     * 5钻区的玩家队列
     */
    public static final Queue<BasePlayer> FIVE_ROOM = new ConcurrentLinkedDeque<>();

    /**
     * 10钻区的玩家队列
     */
    public static final Queue<BasePlayer> TEN_ROOM = new ConcurrentLinkedDeque<>();

    /**
     * 用来保存双人房间的
     */
    private static final Map<Integer, MatchRoom> ROOMS = new ConcurrentHashMap<>();

    /**
     * 为两位玩家创建房间
     */
    public static void createRoom(MatchRoom matchRoom) {
        ROOMS.put(matchRoom.getId(), matchRoom);
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

    public static Queue<BasePlayer> getQueue(int type) {
        Queue<BasePlayer> queue = null;
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
                if (mr.getPlayers() != null) {
                    mr.getPlayers().entrySet().stream()
                            .filter(entry -> entry.getKey() != uid)
                            .forEach(entry -> {
                                Channel channel = entry.getValue().getChannel();
                                if (channel != null) {
                                    channel.writeAndFlush(m);
                                }
                            });
                }
            }

            removeRoom(roomId);
        }
    }
}
