package com.artemis.football.connector;

import com.artemis.football.common.JsonTools;
import com.artemis.football.model.Response;
import com.artemis.football.model.entity.User;
import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author zhengenshen
 * @date 2018-05-19 10:46
 */
@Slf4j
@Service
@ChannelHandler.Sharable
public class ManagerHandler extends ChannelInboundHandlerAdapter {

    private boolean close = false;

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
        if (OnlineManage.notContains(ch)) {
            ctx.executor().schedule(() -> {
                if (OnlineManage.notContains(ch)) {
                    log.error("这么长时间过去了，依旧没有校验，关闭！");
                    ch.close();
                }
            }, 5, TimeUnit.SECONDS);
        }

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Channel ch = ctx.channel();
        OnlineManage.remove(ch);
        log.info("ManagerConnector closed " + ch.remoteAddress());
        log("channelInactive");

    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        log("channelReadComplete");
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        log.debug("msg------------------->{}", msg);

        Channel ch = ctx.channel();
        String request = msg.toString();
        String response;
        if (request.length() > 0) {
            Integer cid = ch.hashCode();
            if (OnlineManage.notContains(ch)) {
                response = login(request, ch);
            } else {
                String[] params = request.split(Response.SPLIT_CHAR);
                response = handler(request);
            }

            ChannelFuture future = ch.write(response);
            if (close) {
                future.addListener(ChannelFutureListener.CLOSE);
            }
        }
    }


    private String login(String request, Channel ch) {
        User user;
        try {
            user = JsonTools.toBean(request, User.class);
        } catch (Exception e) {
            return Response.jsonParseError();
        }
        String response;
        if (user.check()) {
            OnlineManage.add(ch);
            response = Response.success();
        } else {
            response = Response.authError();
        }
        return response;
    }

    private String handler(String request) {

        return "";
    }

    private void log(String str) {
        // log.info("{}校验map里存储{}个 : ", str, ManagerService.getAuthStep().size());
        // ManagerService.getAuthStep().forEach((k, v) -> log.info("key : " + k + " ---> value : " + v));
    }
}
