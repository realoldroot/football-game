package com.artemis.football.connector;

import com.artemis.football.model.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhengenshen
 * @date 2018-05-22 11:33
 */

@Slf4j
public class MessageEncoder extends MessageToByteEncoder<Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        log.info(msg.toString());
        out.writeByte(MessageDecoder.PACKAGE_TAG);
        out.writeByte(msg.getEncode());
        out.writeByte(msg.getEncrypt());
        out.writeInt(msg.getCommand());
        if (msg.getBody() != null && msg.getBody().length() > 0) {
            byte[] bytes = msg.getBody().getBytes("UTF-8");
            out.writeInt(bytes.length);
            out.writeBytes(bytes);
        }
        out.writeBytes(new byte[]{'\r', '\n'});
    }

    // @Override
    // public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
    //     log.info("MessageEncoder.close 方法执行");
    //     super.close(ctx, promise);
    // }
}
