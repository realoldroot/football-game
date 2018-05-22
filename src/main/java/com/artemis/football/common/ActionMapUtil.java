package com.artemis.football.common;

import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhengenshen
 * @date 2018-05-22 11:39
 */

@Service
public class ActionMapUtil {

    private static Map<Integer, Action> map = new HashMap<>();

    public Object invoke(Integer key, Object... args) throws Exception {
        Action action = map.get(key);
        if (action != null) {
            Method method = action.getMethod();
            return method.invoke(action.getObject(), args);
        }
        return null;
    }

    public void put(Integer key, Action action) {
        map.put(key, action);
    }
}
