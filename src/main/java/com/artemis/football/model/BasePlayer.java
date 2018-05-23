package com.artemis.football.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.netty.channel.Channel;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhengenshen
 * @date 2018-05-21 16:26
 */

@Data
public class BasePlayer {

    protected int id;
    @JsonIgnore
    protected Channel channel;
    protected String username;
    protected String nickname;
    protected String teamName;
    private BasePosition position;

    private Map<Integer, BasePosition> actions = new HashMap<>();

    protected boolean isInQueue = false;

}
