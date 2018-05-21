package com.artemis.football.common;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author zhengenshen
 * @date 2018-05-21 16:34
 */

public class JsonTools {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
    }

    public static <T> T toBean(String o, Class<T> valueType) throws Exception {
        try {
            return objectMapper.readValue(o, valueType);
        } catch (Exception e) {
            throw new Exception("json解析失败");
        }
    }

    public static String toJson(Object value) throws Exception {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (Exception e) {
            throw new Exception("json转换失败");
        }
    }

}
