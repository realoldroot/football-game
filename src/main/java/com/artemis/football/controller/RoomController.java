package com.artemis.football.controller;

import com.artemis.football.annotation.ActionMap;
import com.artemis.football.annotation.NettyController;
import com.artemis.football.cache.RoomManager;
import com.artemis.football.common.ActionType;
import com.artemis.football.common.JsonTools;
import com.artemis.football.connector.IBaseConnector;
import com.artemis.football.model.IBaseCharacter;
import com.artemis.football.model.MatchRoom;
import com.artemis.football.model.Message;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhengenshen
 * @date 2018-05-22 16:09
 */

@Slf4j
@NettyController
public class RoomController {

    @Autowired
    private RoomManager roomManager;

    @ActionMap(ActionType.MATCH)
    public void searchRoom(ChannelHandlerContext ctx, Message message) throws Exception {
        Channel ch = ctx.channel();
        MatchRoom matchRoom = JsonTools.toBean(message.getBody(), MatchRoom.class);

        IBaseCharacter player = ch.attr(IBaseConnector.PLAYER).get();

        roomManager.add(player, matchRoom.getScore());

        ch.writeAndFlush(Message.success());

    }

}
