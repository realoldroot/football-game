package com.artemis.football.connector;

import com.artemis.football.model.IBaseCharacter;
import io.netty.util.AttributeKey;

/**
 * @author zhengenshen
 * @date 2018-05-19 10:40
 */

public interface IBaseConnector {

    AttributeKey<IBaseCharacter> PLAYER = AttributeKey.valueOf("player");

    void start();

    void stop();
}
