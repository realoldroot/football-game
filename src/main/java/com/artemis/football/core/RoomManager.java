package com.artemis.football.core;

import com.artemis.football.model.BasePlayer;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author zhengenshen
 * @date 2018-05-23 16:25
 */

public class RoomManager {

    public static final int FIVE = 5;
    public static final int TEN = 10;

    public static final Queue<BasePlayer> FIVE_ROOM = new ConcurrentLinkedDeque<>();

    public static final Queue<BasePlayer> TEN_ROOM = new ConcurrentLinkedDeque<>();
}
