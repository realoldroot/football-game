package com.artemis.football.common;

import com.artemis.football.model.BasePlayerFactory;
import com.artemis.football.model.IBasePlayerFactory;

/**
 * @author zhengenshen
 * @date 2018-05-22 15:48
 */

public class Config {


    private static IBasePlayerFactory playerFactory = new BasePlayerFactory();


    /**
     * 获取player的工厂，默认是BasePlayerFactory
     *
     */
    public static IBasePlayerFactory getPlayerFactory() {
        return playerFactory;
    }
}
