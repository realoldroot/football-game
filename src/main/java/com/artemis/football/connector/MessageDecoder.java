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
    public static final int HEAD_LENGHT = 7;
    /**
     * 标志头
     **/
    public static final byte PACKAGE_TAG = 0x01;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
        buf.markReaderIndex();
        int readableLength = buf.readableBytes();
        if (readableLength < HEAD_LENGHT) {
            throw new CorruptedFrameException("包长度太短 : " + buf.readableBytes());
        }
        byte tag = buf.readByte();
        if (tag != PACKAGE_TAG) {
            throw new CorruptedFrameException("标志错误");
        }
        byte encode = buf.readByte();
        byte encrypt = buf.readByte();
        int command = buf.readInt();

        //心跳包只有7个字节长
        if (readableLength == 7) {
            Message message = new Message(tag, encode, encrypt, command);
            out.add(message);
        } else {
            int length = buf.readInt();
            byte[] data = new byte[length];
            buf.readBytes(data);
            Message message = new Message(tag, encode, encrypt, command, length, new String(data, "UTF-8"));
            out.add(message);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
    }
}
