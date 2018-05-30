package com.artemis.football.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

/**
 * @author zhengenshen
 * @date 2018-05-29 17:07
 */
@Component
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        taskScheduler.execute(() -> {
            // Queue<BasePlayer> queue = RoomManager.getQueue(RoomManager.FIVE);
            // if (queue != null) {
            //     int size = queue.size();
            //     if (size >= 2 && size % 2 == 0) {
            //         synchronized (queue) {
            //             if (queue.size() >= 2 && queue.size() % 2 == 0) {
            //                 BasePlayer player1 = queue.poll();
            //                 BasePlayer player2 = queue.poll();
            //                 MatchRoom matchRoom = BattleFactory.create(player1, player2, FIVE);
            //             }
            //         }
            //     }
            // }

            // while (true) {
            //     try {
            //         BasePlayer player1 = RoomManager.getQueue(RoomManager.FIVE).take();
            //         BasePlayer player2 = RoomManager.getQueue(RoomManager.FIVE).take();
            //         BattleFactory.create(player1, player2, RoomManager.FIVE);
            //     } catch (InterruptedException e) {
            //         e.printStackTrace();
            //     }
            //
            // }


        });
    }
}
