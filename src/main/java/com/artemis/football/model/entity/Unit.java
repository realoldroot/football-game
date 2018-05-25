package com.artemis.football.model.entity;

import lombok.Data;

/**
 * @author zhengenshen
 * @date 2018-05-24 14:55
 */

@Data
public class Unit {

    private float x;
    private float y;

    public Unit(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Unit() {
    }
}
