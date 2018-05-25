package com.artemis.football;

import com.artemis.football.connector.MessageDecoder;
import com.artemis.football.connector.MessageEncoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

/**
 * @author zhengenshen
 * @date 2018-05-25 15:27
 */

public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {


    private ChannelInboundHandlerAdapter channelHandler;

    public ClientChannelInitializer(ChannelInboundHandlerAdapter channelHandler) {
        this.channelHandler = channelHandler;
    }

    @Override
    protected void initChannel(SocketChannel ch) {

        ChannelPipeline pipeline = ch.pipeline();
        ByteBuf delimiter = Unpooled.copiedBuffer("\r\n".getBytes());
        pipeline.addFirst(new DelimiterBasedFrameDecoder(8192, delimiter));
        pipeline.addLast(new MessageEncoder());
        pipeline.addLast(new MessageDecoder());
        // pipeline.addFirst(new LineBasedFrameDecoder(65535));

        // 客户端的逻辑
        pipeline.addLast(channelHandler);

    }
}