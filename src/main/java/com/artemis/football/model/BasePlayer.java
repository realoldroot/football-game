package com.artemis.football.model;

import io.netty.channel.Channel;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhengenshen
 * @date 2018-05-21 16:26
 */

@Data
public class BasePlayer implements IBaseCharacter {

    protected int id;
    protected Channel channel;
    protected String name;
    private BasePosition position;

    private Map<Integer, BasePosition> actions = new HashMap<>();

    protected boolean isInQueue = false;

    @Override
    public BasePosition gPosition() {
        return position;
    }

    @Override
    public void sPosition(BasePosition position) {
        this.position = position;
    }


    @Override
    public void removeAction(int id) {

    }

    @Override
    public Channel gChannel() {
        return this.channel;
    }

    @Override
    public void sChannel(Channel channel) {

        this.channel = channel;
    }

    @Override
    public void logoutHook() {

    }

    @Override
    public void loginHook() {

    }

    @Override
    public void setIsInQueue(boolean isInQueue) {

    }
}
