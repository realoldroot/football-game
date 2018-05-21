package com.artemis.football;

import com.artemis.football.common.JsonTools;
import com.artemis.football.model.entity.User;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

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

            ByteBuf delimiter = Unpooled.copiedBuffer("\t".getBytes());
            // pipeline.addLast("framer", new DelimiterBasedFrameDecoder(2048, delimiter));
            pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
            pipeline.addLast("decoder", new StringDecoder(Charset.forName("UTF-8")));
            pipeline.addLast("encoder", new StringEncoder(Charset.forName("UTF-8")));

            // 客户端的逻辑
            pipeline.addLast("handler", new ClientHandler());

        }
    }

    private class ClientHandler extends ChannelInboundHandlerAdapter {


        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            LOGGER.info("ssssssssss + " + msg.toString());
            super.channelRead(ctx, msg);
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            User user = new User();
            user.setUsername("admin");
            user.setPassword("password");

            String a = JsonTools.toJson(user) + "\n" + "ddddddddddddddd\ncccccccccccccc\n";
            ctx.writeAndFlush(a);
        }
    }
}
