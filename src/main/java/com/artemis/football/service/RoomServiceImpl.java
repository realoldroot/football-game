package com.artemis.football.service;

import com.artemis.football.common.ActionType;
import com.artemis.football.connector.SessionManager;
import com.artemis.football.core.BattleFactory;
import com.artemis.football.core.RoomManager;
import com.artemis.football.model.*;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import static com.artemis.football.core.RoomManager.*;


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
        if (FIVE == type) {
            FIVE_ROOM.offer(player);
        } else if (TEN == type) {
            TEN_ROOM.offer(player);
        }

        taskScheduler.execute(() -> {
            if (FIVE_ROOM.size() >= 2 && FIVE_ROOM.size() % 2 == 0) {
                synchronized (FIVE_ROOM) {
                    if (FIVE_ROOM.size() >= 2 && FIVE_ROOM.size() % 2 == 0) {
                        BasePlayer player1 = FIVE_ROOM.poll();
                        BasePlayer player2 = FIVE_ROOM.poll();
                        MatchRoom matchRoom = BattleFactory.create(player1, player2, FIVE);
                    }
                }
            }

        });
    }

    @Override
    public void ready(BasePlayer player, Integer id) {
        MatchRoom mr = RoomManager.getRoom(id);
        synchronized (mr) {
            player.setStatus(1);
            mr.putPlayer(player.getId(), player);
        }


        //TODO 这里暂时会有问题，没有考虑到并发情况，应该针对对象加锁
        taskScheduler.execute(new Task(mr));

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
        synchronized (mr) {
            if (mr.getPriority() == null) {
                mr.setPriority(sf.getUid());
            } else {
                if (mr.getPlayer(mr.getPriority()).getJigsawTime() < sf.getJigsawTime()) {
                    mr.setPriority(sf.getUid());
                }
            }
            BasePlayer player = mr.getPlayer(sf.getUid());
            player.setJigsawTime(sf.getJigsawTime());
        }

        taskScheduler.execute(() -> {

            long count = mr.getPlayers().values().stream().filter(v -> v.getJigsawTime() != null).count();
            if (count >= 2) {
                Message m = MessageFactory.success(ActionType.SCRAMBLE_END, mr);
            }
        });

    }

    class Task implements Runnable {

        private MatchRoom mr;

        public Task(MatchRoom mr) {
            this.mr = mr;
        }

        @Override
        public void run() {
            // long count = mr.getPlayers().entrySet().parallelStream().filter(v -> v.getValue().getStatus() == 1).count();
            long count = mr.getPlayers().values().parallelStream().filter(v -> v.getStatus() == 1).count();
            if (count >= 2) {
                Message m = MessageFactory.success(ActionType.ALL_READY, mr);
                mr.getPlayers().keySet().parallelStream().forEach(key -> {
                    Channel ch = SessionManager.getChannel(key);
                    if (ch != null) {
                        ch.writeAndFlush(m);
                    }
                });
            }
        }
    }
}
