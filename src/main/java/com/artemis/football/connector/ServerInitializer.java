package com.artemis.football.connector;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
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

    @Override
    protected void initChannel(SocketChannel ch) {

        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
        pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        // pipeline.addLast(new StringDecoder(Charset.forName("UTF-8")));
        // pipeline.addLast(new StringEncoder(Charset.forName("UTF-8")));
        pipeline.addLast(new MessageEncoder());
        pipeline.addLast(new MessageDecoder());
        // pipeline.addFirst(new LineBasedFrameDecoder(65535));
        pipeline.addLast(messageHandler);

    }
}
