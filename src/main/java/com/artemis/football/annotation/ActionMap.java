package com.artemis.football.annotation;

import java.lang.annotation.*;

/**
 * @author zhengenshen
 * @date 2018-05-22 11:21
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface ActionMap {

    int value();
}
