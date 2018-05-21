package com.artemis.football.connector;

import com.artemis.football.common.JsonTools;
import com.artemis.football.model.Response;
import com.artemis.football.model.entity.User;
import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        ManagerService.goNextSetp(ch.hashCode());
        log.info("ManagerConnector connected " + ip);
        log("channelActive");

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Channel ch = ctx.channel();
        ManagerService.clear(ch.hashCode());
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
        String request = msg.toString().toLowerCase();
        String response = "Please i something.";
        if (request.length() > 0) {
            Integer cid = ch.hashCode();
            Integer step = ManagerService.getStep(cid);
            if (step == 1) {
                response = login(request, step, cid);
            } else {
                response = handler(request);
            }

            ChannelFuture future = ch.write(response);
            if (close) {
                future.addListener(ChannelFutureListener.CLOSE);
            }
        }
    }


    private String login(String request, Integer step, Integer cid) {

        User user;
        try {
            user = JsonTools.toBean(request, User.class);
        } catch (Exception e) {
            return Response.jsonParseError();
        }
        String response;
        if (user.check()) {
            ManagerService.goNextSetp(cid);
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
        log.info("{}校验map里存储{}个 : ", str, ManagerService.getAuthStep().size());
        ManagerService.getAuthStep().forEach((k, v) -> log.info("key : " + k + " ---> value : " + v));
    }
}
