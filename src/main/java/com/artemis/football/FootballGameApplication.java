package com.artemis.football;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FootballGameApplication{


    public static void main(String[] args) {
        SpringApplication.run(FootballGameApplication.class, args);

    }

}
