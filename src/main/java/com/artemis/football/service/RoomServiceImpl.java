package com.artemis.football.service;

import com.artemis.football.common.ActionType;
import com.artemis.football.connector.SessionManager;
import com.artemis.football.core.RoomManager;
import com.artemis.football.model.*;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;


/**
 * @author zhengenshen
 * @date 2018-05-23 16:21
 */

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;


    @Override
    public void addPlayer(BasePlayer player, int type) {
        RoomManager.matching(player, type);
        // if (FIVE == type) {
        //     FIVE_ROOM.matching(player);
        // } else if (TEN == type) {
        //     TEN_ROOM.matching(player);
        // }

        // if (RoomManager.getQueue(type).size() >= 2) {
        //     taskScheduler.execute(() -> RoomManager.createRoom(type));
        // }
    }

    @Override
    public void ready(BasePlayer player, Integer id) {
        MatchRoom mr = RoomManager.getRoom(id);
        //设置玩家准备状态
        player.setStatus(PlayerStatus.READY);
        try {
            mr.lock();
            //房间添加玩家
            mr.getPlayers().put(player.getId(), player);
            //查看玩家是否都已准备好
            long count = mr.getPlayers().values().parallelStream().filter(v -> v.getStatus() == PlayerStatus.READY).count();
            if (count == 2) {

                taskScheduler.execute(() -> {
                    Message m = MessageFactory.success(ActionType.ALL_READY, mr);
                    mr.getPlayers().values().forEach(v -> {
                        if (v.getChannel() != null) {
                            v.getChannel().writeAndFlush(m);
                        }
                    });
                    // mr.getPlayers().keySet().forEach(key -> {
                    //     Channel ch = SessionManager.getChannel(key);
                    //     if (ch != null) {
                    //         ch.writeAndFlush(m);
                    //     }
                    // });
                });

            }

        } finally {
            mr.unlock();
        }


        // taskScheduler.execute(new Task(mr));

        // taskScheduler.execute(() -> {
        //     long count = mr.getPlayers().entrySet().parallelStream()
        //             .filter(v -> v.getValue().getStatus() == 1).count();
        //     if (count >= 2) {
        //         try {
        //             Message m = MessageFactory.success(ActionType.ALL_READY, mr);
        //             mr.getPlayers().keySet().parallelStream().forEach(key -> {
        //                 Channel ch = SessionManager.getChannel(key);
        //                 if (ch != null) {
        //                     ch.writeAndFlush(m);
        //                 }
        //             });
        //         } catch (Exception e) {
        //             e.printStackTrace();
        //         }
        //     }
        // });
    }

    @Override
    public void scramble(ScrambleFirst sf) {
        MatchRoom mr = RoomManager.getRoom(sf.getRoomId());
        try {
            mr.lock();
            //如果当前优先发球人为空 设置当前用户优先发球人
            if (mr.getPriority() == null) {
                mr.setPriority(sf.getUid());
            } else {
                //如果当前发球人的滑块时间 小于 当前人的滑块时间 设置发球人为当前用户
                if (mr.getPlayers().get(mr.getPriority()).getJigsawTime() < sf.getJigsawTime()) {
                    mr.setPriority(sf.getUid());
                }
            }
            //保存当前用户的发球时间
            mr.getPlayers().get(sf.getUid()).setJigsawTime(sf.getJigsawTime());

            //累计两个用户的发球人是否都已争抢过发球权
            long count = mr.getPlayers().values().stream().filter(v -> v.getJigsawTime() != null).count();

            if (count == 2) {
                //通知双方 争抢发球权结果
                taskScheduler.execute(() -> {
                    Message msg = MessageFactory.success(ActionType.SCRAMBLE_END, mr);
                    mr.getPlayers().values().forEach(v -> v.getChannel().writeAndFlush(msg));
                });
            }
        } finally {
            mr.unlock();
        }

    }

    class Task implements Runnable {

        private MatchRoom mr;

        Task(MatchRoom mr) {
            this.mr = mr;
        }

        @Override
        public void run() {
            // long count = mr.getPlayers().entrySet().parallelStream().filter(v -> v.getValue().getStatus() == 1).count();
            long count = mr.getPlayers().values().parallelStream().filter(v -> v.getStatus() == 1).count();
            if (count >= 2) {
                Message m = MessageFactory.success(ActionType.ALL_READY, mr);
                mr.getPlayers().keySet().parallelStream().forEach(key -> {
                    Channel ch = SessionManager.getChannels().get(key);
                    if (ch != null) {
                        ch.writeAndFlush(m);
                    }
                });
            }
        }
    }
}
