package com.artemis.football.route;

import com.artemis.football.annotation.ActionMap;
import com.artemis.football.annotation.NettyController;
import com.artemis.football.common.ActionType;
import com.artemis.football.common.JsonTools;
import com.artemis.football.connector.IBaseConnector;
import com.artemis.football.connector.SessionManager;
import com.artemis.football.model.Message;
import com.artemis.football.model.MessageFactory;
import com.artemis.football.model.entity.User;
import com.artemis.football.service.UserService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhengenshen
 * @date 2018-05-22 11:21
 */

@Slf4j
@NettyController
public class UserAction {

    @Autowired
    private UserService userService;

    @ActionMap(ActionType.AUTH)
    public void login(ChannelHandlerContext ctx, Message message) throws Exception {

        Message response;
        User user = JsonTools.toBean(message.getBody(), User.class);

        Channel ch = ctx.channel();

        if (!ch.hasAttr(IBaseConnector.USER)) {
            User resp = userService.login(user.getUsername(), user.getPassword());
            if (resp != null) {
                SessionManager.add(ch, resp);
                ch.attr(IBaseConnector.USER).set(resp);
                response = MessageFactory.authSuccess();
            } else {
                response = MessageFactory.authError();
            }

        } else {
            response = MessageFactory.authSuccess();
        }
        ChannelFuture future = ch.write(response);
        // future.addListener(ChannelFutureListener.CLOSE);

    }

    @ActionMap(ActionType.DEFAULT)
    public void heart(ChannelHandlerContext ctx, Message message) {
        ctx.channel().writeAndFlush(MessageFactory.success(ActionType.DEFAULT));
    }
}
