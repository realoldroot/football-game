package com.artemis.football.connector;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;

/**
 * @author zhengenshen
 * @date 2018-05-19 10:41
 */
@Slf4j
@Service
public class ManagerConnector implements IBaseConnector {

    private final InetSocketAddress localAddress;
    private ServerBootstrap bootstrap;
    private NioEventLoopGroup bossGroup;
    private NioEventLoopGroup workerGroup;

    @Autowired
    private ServerInitializer serverInitializer;

    public ManagerConnector() {
        this.localAddress = new InetSocketAddress(8888);
    }

    @Override
    public void start() {

        if (bootstrap != null) {
            return;
        }

        bootstrap = new ServerBootstrap();
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup(1);

        try {
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(serverInitializer)
                    .bind(localAddress);
            log.info("Manager Service is running! port : " + localAddress.getPort());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void stop() {

        if (bootstrap == null) {
            return;
        }
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
        bootstrap = null;
    }
}
