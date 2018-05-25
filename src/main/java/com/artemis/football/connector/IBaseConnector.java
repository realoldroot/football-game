package com.artemis.football.connector;

import com.artemis.football.model.entity.User;
import io.netty.util.AttributeKey;

/**
 * @author zhengenshen
 * @date 2018-05-19 10:40
 */

public interface IBaseConnector {

    AttributeKey<User> USER = AttributeKey.valueOf("user");

    void start();

    void stop();
}
