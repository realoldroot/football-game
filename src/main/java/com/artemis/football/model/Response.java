package com.artemis.football.model;

import com.artemis.football.common.JsonTools;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhengenshen
 * @date 2018-05-21 16:54
 */

@Slf4j
public class Response {

    public static final String SPLIT_CHAR = "->";

    public static final String NEXT_LINE = "\n";

    public static final String SUCCESS = "SUCCESS";

    public static final String ERROR = "ERROR";

    public static final String BROADCAST = "BROADCAST";

    public static String success(Object o) throws Exception {
        return beanToString(SUCCESS, o);
    }

    /**
     * 成功
     *
     * @return str
     */
    public static String success() {
        return SUCCESS + NEXT_LINE;
    }

    /**
     * 校验失败
     *
     * @return str
     */
    public static String authError() {
        try {
            String content = JsonTools.toJson(new ErrMsg(1, "AUTH_ERROR"));
            return ERROR + SPLIT_CHAR + content + NEXT_LINE;
        } catch (Exception e) {
            log.error(e.getMessage());
            return jsonParseError();
        }
    }


    /**
     * 广播
     *
     * @param o 待广播对象
     * @return str
     */
    public static String broadcast(Object o) {
        return beanToString(BROADCAST, o);
    }


    /**
     * 解析json 并且加上类型
     *
     * @param type 消息类型
     * @param o    待转json对象
     * @return str
     */
    private static String beanToString(String type, Object o) {
        try {
            String content = JsonTools.toJson(o);
            return type + SPLIT_CHAR + content + NEXT_LINE;
        } catch (Exception e) {
            log.error(e.getMessage());
            return jsonParseError();
        }
    }

    /**
     * json 解析失败
     *
     * @return str
     */
    public static String jsonParseError() {
        return ERROR + SPLIT_CHAR + "{\"code\":1,\"json解析失败\"}" + NEXT_LINE;
    }

    @Data
    static class ErrMsg {
        private int code;
        private String message;

        public ErrMsg(int code, String message) {
            this.code = code;
            this.message = message;
        }
    }
}
