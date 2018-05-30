package com.artemis.football.schedule;

import com.artemis.football.connector.SessionManager;
import com.artemis.football.core.RoomManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author zhengenshen
 * @date 2018-05-23 15:48
 */

@Component
@Slf4j
public class ScheduleTask {


    @Scheduled(cron = "0/10 * * * * ?")
    public void show() {
        SessionManager.show();
        RoomManager.show();
    }
}
