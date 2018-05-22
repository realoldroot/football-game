package com.artemis.football.connector;

import com.artemis.football.common.ActionMapUtil;
import com.artemis.football.model.Message;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author zhengenshen
 * @date 2018-05-22 14:48
 */

@Slf4j
@Service
@ChannelHandler.Sharable
public class MessageHandler extends ChannelInboundHandlerAdapter {

    private boolean close = false;

    @Autowired
    private ActionMapUtil actionMapUtil;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable t) {
        log.error(t.getMessage(), t);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Channel ch = ctx.channel();
        String ip = ch.remoteAddress().toString();
        String[] ipArr = ip.split(":");
        String realIp = ipArr[0].substring(ipArr[0].indexOf("/") + 1);
        log.info("channel hash : " + ch.hashCode());
        log.info("ManagerConnector connected " + ip);
        if (SessionManager.notContains(ch)) {
            ctx.executor().schedule(() -> {
                if (SessionManager.notContains(ch)) {
                    log.error(ch.hashCode() + "");
                    SessionManager.checked.forEach(System.out::println);
                    log.error("这么长时间过去了，依旧没有校验，关闭！");
                    ch.close();
                }
            }, 5, TimeUnit.SECONDS);
        }

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Channel ch = ctx.channel();
        SessionManager.remove(ch);
        log.info("ManagerConnector closed " + ch.remoteAddress());

    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        SessionManager.show();
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.debug("msg------------------->{}", msg);
        Message message = (Message) msg;
        actionMapUtil.invoke(message.getCommand(), ctx, message);
        ReferenceCountUtil.release(msg);
    }

}
