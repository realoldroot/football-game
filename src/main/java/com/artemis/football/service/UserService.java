package com.artemis.football.service;

import com.artemis.football.model.entity.User;

/**
 * @author zhengenshen
 * @date 2018-05-22 18:15
 */

public interface UserService {

    User login(String username, String password);
}
