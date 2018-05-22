package com.artemis.football.controller;

import com.artemis.football.annotation.ActionMap;
import com.artemis.football.annotation.NettyController;
import com.artemis.football.common.ActionType;
import com.artemis.football.common.Config;
import com.artemis.football.common.JsonTools;
import com.artemis.football.connector.IBaseConnector;
import com.artemis.football.connector.SessionManager;
import com.artemis.football.model.IBaseCharacter;
import com.artemis.football.model.Message;
import com.artemis.football.model.entity.User;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhengenshen
 * @date 2018-05-22 11:21
 */

@Slf4j
@NettyController
public class UserAction {

    @ActionMap(ActionType.AUTH)
    public void login(ChannelHandlerContext ctx, Message message) throws Exception {

        Message response;
        User user = JsonTools.toBean(message.getBody(), User.class);

        Channel ch = ctx.channel();

        if (SessionManager.notContains(ch)) {
            // if (StringUtils.equals(user.getUsername(), "admin") && StringUtils.equals(user.getPassword(), "admin")) {
                SessionManager.add(ch);
                IBaseCharacter player = Config.getPlayerFactory().getPlayer();
                player.sChannel(ch);
                // player.setId(user.getId());
                player.setId(1);
                ch.attr(IBaseConnector.PLAYER).set(player);

                response = Message.success();
            // } else {
            //     response = Message.error();
            // }
        } else {
            response = Message.success();
        }
        ChannelFuture future = ch.write(response);
        // future.addListener(ChannelFutureListener.CLOSE);

    }
}
