package com.artemis.football.model;

import com.artemis.football.common.ActionType;
import lombok.Data;

/**
 * @author zhengenshen
 * @date 2018-05-23 18:17
 */

public class MessageFactory {

    private static final Response SUCCESS = new Response(0, "success");
    private static final Response ERROR = new Response(1, "error");


    public static Message success() throws Exception {
        return new Message(ActionType.DEFAULT, SUCCESS);
    }

    public static Message success(int type) throws Exception {
        return new Message(type, SUCCESS);
    }

    public static Message success(int type, Object body) throws Exception {
        return new Message(ActionType.DEFAULT, body);
    }

    public static Message error() throws Exception {
        return new Message(ActionType.DEFAULT, ERROR);
    }

    public static Message error(int type) throws Exception {
        return new Message(type, ERROR);
    }


    public static Message authSuccess() throws Exception {
        return new Message(ActionType.AUTH, SUCCESS);
    }

    public static Message authError() throws Exception {
        return new Message(ActionType.AUTH, ERROR);
    }


    @Data
    static class Response {

        private int code;
        private String message;

        Response(int code, String message) {
            this.code = code;
            this.message = message;
        }


    }


}
