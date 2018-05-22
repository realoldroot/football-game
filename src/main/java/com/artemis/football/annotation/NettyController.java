package com.artemis.football.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author zhengenshen
 * @date 2018-05-22 11:19
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface NettyController {
}
