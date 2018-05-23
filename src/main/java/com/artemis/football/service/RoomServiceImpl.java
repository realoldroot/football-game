package com.artemis.football.service;

import com.artemis.football.core.BattleFactory;
import com.artemis.football.model.BasePlayer;
import com.artemis.football.model.MatchRoom;
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
                        // redisTemplate.opsForValue().set(matchRoom.getId() + "", matchRoom);
                    }
                }
            }

        });
    }
}
