package com.artemis.football.common;

import java.lang.reflect.Method;

/**
 * @author zhengenshen
 * @date 2018-05-22 11:40
 */

public class Action {

    private Method method;

    private Object object;

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

}
