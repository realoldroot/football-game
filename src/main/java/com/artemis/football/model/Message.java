package com.artemis.football.model;

import com.artemis.football.common.JsonTools;
import com.artemis.football.connector.MessageDecoder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhengenshen
 * @date 2018-05-22 11:23
 */

@Data
@Slf4j
public class Message {

    private byte tag;
    /*  编码*/
    private byte encode;
    /*加密*/
    private byte encrypt;
    /* 类型**/
    private int command;
    /*包的长度*/
    private int length;

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


    public Message(int command, Object body) {
        this.tag = MessageDecoder.PACKAGE_TAG;
        this.encode = 0x01;
        this.encrypt = 0x01;
        this.command = command;
        String json;
        try {
            json = JsonTools.toJson(body);
            this.length = json.getBytes("UTF-8").length;
            this.body = json;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Message() {
    }

    public Message(byte tag, byte encode, byte encrypt, int command) {
        this.tag = tag;
        this.encode = encode;
        this.encrypt = encrypt;
        this.command = command;
    }

    public Message(int command) {
        this.tag = MessageDecoder.PACKAGE_TAG;
        this.encode = 0x01;
        this.encrypt = 0x01;
        this.command = command;
    }
}
