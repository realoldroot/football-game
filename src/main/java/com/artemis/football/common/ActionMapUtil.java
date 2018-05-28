package com.artemis.football.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhengenshen
 * @date 2018-05-22 11:39
 */

@Slf4j
@Service
public class ActionMapUtil {

    private static Map<Integer, Action> map = new HashMap<>();

    public Object invoke(Integer key, Object... args) throws Exception {
        Action action = map.get(key);
        if (action != null) {
            Method method = action.getMethod();
            log.info("method {}    {}", action, args);
            return method.invoke(action.getObject(), args);
        }
        return null;
    }

    public void put(Integer key, Action action) {
        map.put(key, action);
    }
}
