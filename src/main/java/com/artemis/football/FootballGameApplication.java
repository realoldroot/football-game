package com.artemis.football;

import com.artemis.football.connector.IBaseConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FootballGameApplication implements CommandLineRunner {

    @Autowired
    private IBaseConnector managerConnector;

    public static void main(String[] args) {
        SpringApplication.run(FootballGameApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        managerConnector.start();
        Thread.currentThread().join();
    }
}
