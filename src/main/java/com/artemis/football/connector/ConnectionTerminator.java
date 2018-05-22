package com.artemis.football.connector;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhengenshen
 * @date 2018-05-21 17:55
 */
@Slf4j
public class ConnectionTerminator implements Runnable {

    ChannelHandlerContext ctx;

    public ConnectionTerminator(ChannelHandlerContext ctx) {

        this.ctx = ctx;
    }

    @Override
    public void run() {
        if (SessionManager.notContains(ctx.channel())) {
            log.error("这么长时间过去了，依旧没有校验，关闭！");
            ctx.close();
        }
    }
}
