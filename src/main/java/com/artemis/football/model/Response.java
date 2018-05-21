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

    private static final String SPLIT_CHAR = "->";

    private static final String NEXT_LINE = "\n";

    public static final String SUCCESS = "SUCCESS";

    public static final String ERROR = "ERROR";

    public static String success(Object o) throws Exception {
        return SUCCESS + JsonTools.toJson(o);
    }

    public static String success() {
        return SUCCESS + NEXT_LINE;
    }

    public static String authError() {
        try {
            String content = JsonTools.toJson(new ErrMsg(1, "AUTH_ERROR"));
            return ERROR + SPLIT_CHAR + content + NEXT_LINE;
        } catch (Exception e) {
            log.error(e.getMessage());
            return jsonParseError();
        }
    }

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
