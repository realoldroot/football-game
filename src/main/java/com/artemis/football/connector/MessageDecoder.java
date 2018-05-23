package com.artemis.football.connector;

import com.artemis.football.model.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author zhengenshen
 * @date 2018-05-22 11:31
 */

@Slf4j
public class MessageDecoder extends ByteToMessageDecoder {

    /**
     * 包长度志头
     **/
    public static final int HEAD_LENGHT = 20;
    /**
     * 标志头
     **/
    public static final byte PACKAGE_TAG = 0x01;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
        buf.markReaderIndex();
        if (buf.readableBytes() < HEAD_LENGHT) {
            throw new CorruptedFrameException("包长度问题");
        }
        byte tag = buf.readByte();
        if (tag != PACKAGE_TAG) {
            throw new CorruptedFrameException("标志错误");
        }
        byte encode = buf.readByte();
        byte encrypt = buf.readByte();
        int command = buf.readInt();
        int length = buf.readInt();
        byte[] data = new byte[length];
        buf.readBytes(data);
        Message message = new Message(tag, encode, encrypt, command, length, new String(data, "UTF-8"));
        log.info(message.toString());
        out.add(message);
    }
}
