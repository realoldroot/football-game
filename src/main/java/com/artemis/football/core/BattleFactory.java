package com.artemis.football.core;

import com.artemis.football.common.ActionType;
import com.artemis.football.connector.IBaseConnector;
import com.artemis.football.model.BasePlayer;
import com.artemis.football.model.MatchRoom;
import com.artemis.football.model.Message;
import com.artemis.football.model.MessageFactory;

/**
 * 对战工厂
 *
 * @author zhengenshen
 * @date 2018-05-22 16:25
 */

public class BattleFactory {

    /**
     * 创建房间
     *
     * @param player1 用户1
     * @param player2 用户2
     * @param score   积分
     * @return 房间
     */
    public static MatchRoom create(BasePlayer player1, BasePlayer player2, int score) {

        Integer id = Math.abs(player1.hashCode() + player2.hashCode());
        player1.setRoomId(id);
        player2.setRoomId(id);
        MatchRoom matchRoom = new MatchRoom();
        matchRoom.setScore(score);
        matchRoom.putPlayer(player1.getId(), player1);
        matchRoom.putPlayer(player2.getId(), player2);
        matchRoom.setId(id);

        try {
            Message m = MessageFactory.success(ActionType.MATCH_SUCCESS, matchRoom);
            player1.getChannel().attr(IBaseConnector.ROOM_ID).set(id);
            player2.getChannel().attr(IBaseConnector.ROOM_ID).set(id);
            player1.getChannel().writeAndFlush(m);
            player2.getChannel().writeAndFlush(m);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RoomManager.createRoom(matchRoom);

        return matchRoom;
    }
}
