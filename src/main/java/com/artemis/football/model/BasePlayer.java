package com.artemis.football.model;

import com.artemis.football.model.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.netty.channel.Channel;
import lombok.Data;

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
    protected Integer roomId;
    protected String teamName;
    protected Units units = new Units();

    protected int status;

    public BasePlayer(User user, Channel ch) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.teamName = user.getTeamName();
        this.channel = ch;
    }

    public BasePlayer() {
    }
}
