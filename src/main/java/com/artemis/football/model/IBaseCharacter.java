package com.artemis.football.model;

import io.netty.channel.Channel;

/**
 * @author zhengenshen
 * @date 2018-05-19 10:41
 */

public interface IBaseCharacter {

    int getId();

    void setId(int id);

    BasePosition gPosition();

    void sPosition(BasePosition position);

    String getName();

    void setName(String name);

    String getTeamName();

    void setTeamName(String teamName);

    void removeAction(int id);

    Channel gChannel();

    void sChannel(Channel channel);

    void logoutHook();

    void loginHook();

    boolean isInQueue();

    void setIsInQueue(boolean isInQueue);

}
