package com.artemis.football.service;

import com.artemis.football.model.MatchRoom;

/**
 * @author zhengenshen
 * @date 2018-05-30 15:43
 */

public interface MatchRoomService {

    void asyncSave(MatchRoom matchRoom);

    void asyncBegin(Integer id, Integer angle, Integer power);
}
