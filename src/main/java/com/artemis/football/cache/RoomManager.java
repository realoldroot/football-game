package com.artemis.football.cache;

import com.artemis.football.core.BattleFactory;
import com.artemis.football.model.IBaseCharacter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author zhengenshen
 * @date 2018-05-22 16:12
 */
@Service
public class RoomManager {


    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    public static final int FIVE = 5;
    public static final int TEN = 10;

    public static final Queue<IBaseCharacter> FIVE_ROOM = new ConcurrentLinkedDeque<>();

    public static final Queue<IBaseCharacter> TEN_ROOM = new ConcurrentLinkedDeque<>();


    public void add(IBaseCharacter character, int type) {
        if (FIVE == type) {
            FIVE_ROOM.offer(character);
        } else if (TEN == type) {
            TEN_ROOM.offer(character);
        }

        taskScheduler.execute(() -> {
            synchronized (FIVE_ROOM) {
                if (FIVE_ROOM.size() >= 2 && FIVE_ROOM.size() % 2 == 0) {
                    IBaseCharacter player1 = FIVE_ROOM.poll();
                    IBaseCharacter player2 = FIVE_ROOM.poll();
                    BattleFactory.create(player1, player2);
                }
            }
        });
    }

}
