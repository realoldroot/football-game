package com.artemis.football.model;

import com.artemis.football.connector.MessageDecoder;
import lombok.Data;

/**
 * @author zhengenshen
 * @date 2018-05-22 11:23
 */

@Data
public class Message {

    private byte tag;
    /*  编码*/
    private byte encode;
    /*加密*/
    private byte encrypt;
    /* 类型**/
    private int command;
    /*包的长度*/
    private int length = 1024;

    /*内容*/
    private String body;

    public Message(byte tag, byte encode, byte encrypt, int command, int length, String body) {
        this.tag = tag;
        this.encode = encode;
        this.encrypt = encrypt;
        this.command = command;
        this.length = length;
        this.body = body;
    }

    public Message(String body) {
        this.tag = MessageDecoder.PACKAGE_TAG;
        this.encode = 0x01;
        this.encrypt = 0x01;
        this.command = 0;
        this.length = body.length();
        this.body = body;
    }

    public Message() {
    }

    public static Message success() {
        String body = "{\"code\":\"success\"}";
        return new Message(body);
    }

    public static Message error() {
        String body = "{\"code\":\"error\"}";
        return new Message(body);
    }

    public static Message typeBuild(int command, String body) {
        Message m = new Message();
        m.tag = MessageDecoder.PACKAGE_TAG;
        m.encode = 0x01;
        m.encrypt = 0x01;
        m.command = command;
        m.length = body.length();
        m.body = body;
        return m;
    }
}
