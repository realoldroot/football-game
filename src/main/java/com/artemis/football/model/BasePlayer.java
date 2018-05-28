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

    /**
     * 拼图时间
     */
    protected Integer JigsawTime;
    /**
     * 玩家准备状态 0 未准备  1 准备完毕
     */
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
