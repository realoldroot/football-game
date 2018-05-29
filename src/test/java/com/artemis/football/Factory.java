package com.artemis.football;

import com.artemis.football.common.ActionType;
import com.artemis.football.common.JsonTools;
import com.artemis.football.model.*;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.RandomUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhengenshen
 * @date 2018-05-25 14:49
 */

public class Factory {

    public static void match(ChannelHandlerContext ctx) throws Exception {
        Map<String, Integer> map = new HashMap<>();
        map.put("score", 10);
        Message m = MessageFactory.success(ActionType.MATCH, map);
        System.out.println("发送匹配用户" + m);
        ctx.writeAndFlush(m);
    }

    public static void ready(ChannelHandlerContext ctx, Message message, int id) throws Exception {

        MatchRoom matchRoom = JsonTools.toBean(message.getBody(), MatchRoom.class);
        BasePlayer player = matchRoom.getPlayer(id);
        player.setUnits(new Units().random());
        player.setStatus(1);
        Message m = MessageFactory.success(ActionType.READY, player);
        System.out.println("发送准备好了" + m);
        ctx.writeAndFlush(m);
    }

    public static void scramble(ChannelHandlerContext ctx, Message message, int uid) throws Exception {

        MatchRoom matchRoom = JsonTools.toBean(message.getBody(), MatchRoom.class);
        Integer roomId = matchRoom.getId();
        ScrambleFirst sf = new ScrambleFirst();
        sf.setRoomId(roomId);
        sf.setUid(uid);
        sf.setJigsawTime(RandomUtils.nextInt(1000, 1500));
        ctx.channel().writeAndFlush(MessageFactory.success(ActionType.SCRAMBLE, sf));
    }
}
