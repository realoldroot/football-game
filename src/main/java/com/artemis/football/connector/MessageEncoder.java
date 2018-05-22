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
        out.writeByte(MessageDecoder.PACKAGE_TAG);
        out.writeByte(msg.getEncode());
        out.writeByte(msg.getEncrypt());
        out.writeInt(msg.getCommand());
        out.writeInt(msg.getLength());
        out.writeBytes(msg.getBody().getBytes("UTF-8"));
    }
}
