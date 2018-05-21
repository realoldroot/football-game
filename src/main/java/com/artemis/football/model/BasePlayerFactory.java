package com.artemis.football.model;

/**
 * @author zhengenshen
 * @date 2018-05-21 16:30
 */

public class BasePlayerFactory implements IBasePlayerFactory {

    @Override
    public IBaseCharacter getPlayer() {
        return new BasePlayer();
    }
}
