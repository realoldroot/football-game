package com.artemis.football.connector;

import com.artemis.football.model.BasePlayer;
import com.artemis.football.model.entity.User;
import io.netty.util.AttributeKey;

/**
 * @author zhengenshen
 * @date 2018-05-19 10:40
 */

public interface IBaseConnector {

    AttributeKey<User> USER = AttributeKey.valueOf("user");

    AttributeKey<Integer> ROOM_ID = AttributeKey.valueOf("room_id");

    AttributeKey<BasePlayer> PLAYER = AttributeKey.valueOf("player");

    void start();


    void stop();
}
