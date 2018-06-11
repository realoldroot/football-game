package com.artemis.football.thread;

import com.artemis.football.connector.IBaseConnector;
import com.artemis.football.core.RoomManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

/**
 * 子线程开启netty服务
 * <p>
 * 子线程处理每个房间等待队列中的玩家
 *
 * @author zhengenshen
 * @date 2018-06-11 17:18
 */

@Service
public class MyCommandLineRunner implements CommandLineRunner {

    @Autowired
    private IBaseConnector managerConnector;

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    @Override
    public void run(String... args) {
        taskScheduler.execute(() -> managerConnector.start());


        //每一个线程阻塞出队
        taskScheduler.execute(() -> RoomManager.match(RoomManager.FIVE));
        taskScheduler.execute(() -> RoomManager.match(RoomManager.TEN));
        taskScheduler.execute(() -> RoomManager.match(RoomManager.FIFTEEN));
        taskScheduler.execute(() -> RoomManager.match(RoomManager.TWENTY_FIVE));

    }
}
