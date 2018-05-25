package com.artemis.football;

import com.artemis.football.common.ActionType;
import com.artemis.football.common.JsonTools;
import com.artemis.football.model.Message;
import com.artemis.football.model.entity.User;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhengenshen
 * @date 2018-05-21 17:01
 */

public class ClientServer2 {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientServer2.class);

    public static void main(String[] args) {

        new ClientServer2().start("192.168.0.133", 8888);
    }

    void start(String address, int port) {

        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new com.artemis.football.ClientChannelInitializer(new ClientHandler()));

        try {
            ChannelFuture future = bootstrap.connect(address, port).sync();
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

    }


    private class ClientHandler extends ChannelInboundHandlerAdapter {


        int count = 0;

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            Message message = (Message) msg;
            if (message.getCommand() == ActionType.AUTH) {
                LOGGER.info("登陆成功 + " + msg.toString());
                //登陆成功 进入5钻区
                Factory.match(ctx);
                count++;
            } else if (message.getCommand() == ActionType.MATCH_SUCCESS) {
                //双方都准备成功了
                LOGGER.info("匹配成功 + " + msg.toString());
                Factory.ready(ctx, message, 4);
            } else if (message.getCommand() == ActionType.ALL_READY) {
                LOGGER.info("大家都准备好了 {}", message);
            } else {
                LOGGER.info("收到消息 + " + msg.toString());
            }
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            User user = new User();
            user.setUsername("18519207690");
            user.setPassword("E10ADC3949BA59ABBE56E057F20F883E");

            String a = JsonTools.toJson(user);
            Message m = new Message((byte) 45, (byte) 0x01, (byte) 1, 1, a.length(), a);

            ctx.writeAndFlush(m);
            count++;

        }


    }
}
