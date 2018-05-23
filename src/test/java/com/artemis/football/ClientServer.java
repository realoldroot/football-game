package com.artemis.football;

import com.artemis.football.common.ActionType;
import com.artemis.football.common.JsonTools;
import com.artemis.football.connector.MessageDecoder;
import com.artemis.football.connector.MessageEncoder;
import com.artemis.football.model.MatchRoom;
import com.artemis.football.model.Message;
import com.artemis.football.model.MessageFactory;
import com.artemis.football.model.entity.User;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhengenshen
 * @date 2018-05-21 17:01
 */

public class ClientServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientServer.class);

    public static void main(String[] args) {

        new ClientServer().start("192.168.0.120", 8888);
    }

    void start(String address, int port) {

        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ClientChannelInitializer());

        try {
            ChannelFuture future = bootstrap.connect(address, port).sync();
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

    }

    private class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {


        @Override
        protected void initChannel(SocketChannel ch) {

            ChannelPipeline pipeline = ch.pipeline();

            // pipeline.addLast("framer", new DelimiterBasedFrameDecoder(2048, delimiter));
            // pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
            // pipeline.addLast("decoder", new StringDecoder(Charset.forName("UTF-8")));
            // pipeline.addLast("encoder", new StringEncoder(Charset.forName("UTF-8")));
            pipeline.addLast(new MessageEncoder());
            pipeline.addLast(new MessageDecoder());
            // pipeline.addFirst(new LineBasedFrameDecoder(65535));

            // 客户端的逻辑
            pipeline.addLast("handler", new ClientHandler());

        }
    }

    private class ClientHandler extends ChannelInboundHandlerAdapter {


        boolean flag = true;

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            LOGGER.info("ssssssssss + " + msg.toString());
            if (flag) {
                MatchRoom matchRoom = new MatchRoom();
                matchRoom.setScore(5);
                Map<String, Integer> map = new HashMap<>();
                map.put("score", 5);
                Message m = MessageFactory.success(ActionType.MATCH, map);
                System.out.println(m);
                ctx.writeAndFlush(m);
            }
            flag = false;
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            User user = new User();
            user.setUsername("18500340169");
            user.setPassword("E10ADC3949BA59ABBE56E057F20F883E");

            String a = JsonTools.toJson(user);
            Message m = new Message((byte) 45, (byte) 0x01, (byte) 1, 1, a.length(), a);

            ctx.writeAndFlush(m);
        }
    }
}
