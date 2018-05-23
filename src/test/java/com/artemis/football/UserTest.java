package com.artemis.football;

import com.artemis.football.model.entity.User;
import com.artemis.football.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhengenshen
 * @date 2018-05-22 18:26
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Autowired
    private UserService userService;

    @Test
    public void login() {
        User user = userService.login("18500340169", "E10ADC3949BA59ABBE56E057F20F883E");
        System.out.println(user);
    }
}
