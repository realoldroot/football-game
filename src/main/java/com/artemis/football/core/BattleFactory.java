package com.artemis.football.core;

import com.artemis.football.common.ActionType;
import com.artemis.football.model.BasePlayer;
import com.artemis.football.model.MatchRoom;
import com.artemis.football.model.Message;
import com.artemis.football.model.MessageFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        List<BasePlayer> players = Stream.of(player1, player1).collect(Collectors.toList());

        MatchRoom matchRoom = new MatchRoom();
        matchRoom.setScore(score);
        matchRoom.setPlayer1(player1);
        matchRoom.setPlayer2(player2);
        matchRoom.setId(System.currentTimeMillis());

        try {
            Message m = MessageFactory.success(ActionType.MATCH_SUCCESS, matchRoom);
            player1.getChannel().writeAndFlush(m);
            player2.getChannel().writeAndFlush(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return matchRoom;
    }
}
