package com.artemis.football.udp;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhengenshen
 * @date 2018-05-29 15:52
 */

@Slf4j
public class UdpMessageHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) {

        System.out.println("消息来源" + msg.sender().getHostString() + ":" + msg.sender().getPort());

        // 消息处理。。。。。

        //消息发送。。。。
        DatagramPacket dp = new DatagramPacket(Unpooled.copiedBuffer("消息".getBytes()), msg.sender());
        ctx.channel().writeAndFlush(dp);
    }
}
