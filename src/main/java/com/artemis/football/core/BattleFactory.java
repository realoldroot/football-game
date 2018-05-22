package com.artemis.football.core;

import com.artemis.football.common.JsonTools;
import com.artemis.football.model.IBaseCharacter;
import com.artemis.football.model.Message;

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

    public static void create(IBaseCharacter player1, IBaseCharacter player2)  {

        List<IBaseCharacter> players = Stream.of(player1, player1).collect(Collectors.toList());
        Message m;
        try {
            m = new Message(JsonTools.toJson(players));
            player1.gChannel().writeAndFlush(m);
            player2.gChannel().writeAndFlush(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
