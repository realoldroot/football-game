package com.artemis.football.model;

import com.artemis.football.model.entity.Unit;
import lombok.Data;

/**
 * @author zhengenshen
 * @date 2018-05-24 15:35
 */

@Data
public class Units {

    private Unit unit1;
    private Unit unit2;
    private Unit unit3;
    private Unit unit4;
    private Unit unit5;

    public Units random() {
        this.unit1 = new Unit(10, 10);
        this.unit2 = new Unit(10, 10);
        this.unit3 = new Unit(10, 10);
        this.unit4 = new Unit(10, 10);
        this.unit5 = new Unit(10, 10);
        return this;
    }
}
