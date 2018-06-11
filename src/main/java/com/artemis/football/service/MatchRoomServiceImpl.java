package com.artemis.football.service;

import com.artemis.football.common.ActionType;
import com.artemis.football.connector.SessionManager;
import com.artemis.football.core.RoomManager;
import com.artemis.football.model.MatchRoom;
import com.artemis.football.model.MessageFactory;
import com.artemis.football.repository.MatchRoomRepository;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author zhengenshen
 * @date 2018-05-30 15:43
 */

@Service
public class MatchRoomServiceImpl implements MatchRoomService {

    @Autowired
    private MatchRoomRepository roomRepository;

    @Async
    @Override
    public void asyncSave(MatchRoom matchRoom) {
        if (matchRoom != null) {
            roomRepository.save(matchRoom);
        }
    }

    @Async
    @Override
    public void asyncBegin(Integer id, Float angle, Float power) {
        MatchRoom room = RoomManager.getRoom(id);
        if (room == null) {
            return;
        }
        room.setAngle(angle);
        room.setPower(power);

        room.getPlayers().keySet().forEach(key -> {
            Channel channel = SessionManager.channels.get(key);
            if (channel != null) {
                channel.writeAndFlush(MessageFactory.success(ActionType.BEGIN_DOWN, room));
            }
        });

    }
}
