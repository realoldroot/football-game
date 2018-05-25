package com.artemis.football.service;

import com.artemis.football.model.BasePlayer;

/**
 * @author zhengenshen
 * @date 2018-05-23 16:21
 */

public interface RoomService {

    void addPlayer(BasePlayer player, int type);

    void ready(BasePlayer player, Long id);
}
