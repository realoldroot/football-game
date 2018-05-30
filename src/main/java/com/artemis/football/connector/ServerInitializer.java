package com.artemis.football.connector;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhengenshen
 * @date 2018-05-19 10:44
 */

@Service
@ChannelHandler.Sharable
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    private MessageHandler messageHandler;


    // 设置6秒检测chanel是否接受过心跳数据
    private static final int READ_WAIT_SECONDS = 20;


    @Override
    protected void initChannel(SocketChannel ch) {

        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
        ByteBuf delimiter = Unpooled.copiedBuffer("\r\n".getBytes());
        pipeline.addFirst(new DelimiterBasedFrameDecoder(8192, delimiter));
        // pipeline.addLast(new IdleStateHandler(READ_WAIT_SECONDS, 0, 0, TimeUnit.SECONDS));
        pipeline.addLast(new MessageEncoder());
        pipeline.addLast(new MessageDecoder());
        // pipeline.addFirst(new LineBasedFrameDecoder(65535));
        pipeline.addLast(messageHandler);

    }
}
