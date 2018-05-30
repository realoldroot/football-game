package com.artemis.football.common;

import com.artemis.football.model.BasePlayer;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author zhengenshen
 * @date 2018-05-29 16:36
 */

public class PlayerQueue extends LinkedBlockingQueue<BasePlayer> {


    @Override
    public boolean offer(BasePlayer e) {
        boolean flag = super.offer(e);
        int size = size();
        if (size >= 2) {
        }
        return flag;
    }


}
