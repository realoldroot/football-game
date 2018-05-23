package com.artemis.football.connector;

import com.artemis.football.model.BasePlayer;
import io.netty.util.AttributeKey;

/**
 * @author zhengenshen
 * @date 2018-05-19 10:40
 */

public interface IBaseConnector {

    AttributeKey<BasePlayer> PLAYER = AttributeKey.valueOf("player");

    void start();

    void stop();
}
