package com.artemis.football.service;

import com.artemis.football.model.MatchRoom;
import com.artemis.football.repository.MatchRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhengenshen
 * @date 2018-05-30 15:43
 */

@Service
public class MatchRoomServiceImpl implements MatchRoomService {

    @Autowired
    private MatchRoomRepository roomRepository;

    @Override
    public void asyncSave(MatchRoom matchRoom) {
        if (matchRoom != null) {
            roomRepository.save(matchRoom);
        }
    }
}
