package com.artemis.football.udp;

import com.artemis.football.connector.IBaseConnector;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * @author zhengenshen
 * @date 2018-05-29 15:47
 */

// @Service("udpServer")
public class UdpServer implements IBaseConnector {


    @Override
    public void start() {

        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();//udp不能使用ServerBootstrap
            b.group(workerGroup).channel(NioDatagramChannel.class)//设置UDP通道
                    .handler(new UdpServerInitializer())//初始化处理器
                    .option(ChannelOption.SO_BROADCAST, true)// 支持广播
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .option(ChannelOption.SO_RCVBUF, 1024 * 1024)// 设置UDP读缓冲区为1M
                    .option(ChannelOption.SO_SNDBUF, 1024 * 1024);// 设置UDP写缓冲区为1M

            b.bind("192.168.0.133",9998).sync();
            System.out.println("[UDP 启动了]");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();

        }
    }

    @Override
    public void stop() {

    }
}
