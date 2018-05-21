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
}
