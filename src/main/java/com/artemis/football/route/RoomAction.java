package com.artemis.football.route;

import com.artemis.football.annotation.ActionMap;
import com.artemis.football.annotation.NettyController;
import com.artemis.football.common.ActionType;
import com.artemis.football.common.JsonTools;
import com.artemis.football.connector.IBaseConnector;
import com.artemis.football.model.*;
import com.artemis.football.model.entity.User;
import com.artemis.football.service.RoomService;
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
public class RoomAction {

    @Autowired
    private RoomService roomService;

    /**
     * 用户加入匹配房间
     */
    @ActionMap(ActionType.MATCH)
    public void searchRoom(ChannelHandlerContext ctx, Message message) throws Exception {
        Channel ch = ctx.channel();
        MatchRoom matchRoom = JsonTools.toBean(message.getBody(), MatchRoom.class);

        User user = ch.attr(IBaseConnector.USER).get();

        roomService.addPlayer(new BasePlayer(user, ch), matchRoom.getScore());

        ch.writeAndFlush(MessageFactory.success());

    }

    /**
     * 用户完成布局
     */
    @ActionMap(ActionType.READY)
    public void ready(ChannelHandlerContext ctx, Message message) throws Exception {
        Channel ch = ctx.channel();
        BasePlayer player = JsonTools.toBean(message.getBody(), BasePlayer.class);
        roomService.ready(player, player.getRoomId());
        ch.writeAndFlush(MessageFactory.success(ActionType.READY));
    }

    /**
     * 处理前端发送过来 用户争夺发球权的是时间
     */
    @ActionMap(ActionType.SCRAMBLE)
    public void scramble(ChannelHandlerContext ctx, Message message) throws Exception {
        Channel ch = ctx.channel();
        ScrambleFirst sf = JsonTools.toBean(message.getBody(), ScrambleFirst.class);
        if (ch.hasAttr(IBaseConnector.USER)) {
            Integer id = ch.attr(IBaseConnector.USER).get().getId();
            roomService.scramble(sf);
            ch.writeAndFlush(MessageFactory.success(ActionType.SCRAMBLE));
        }

    }

}