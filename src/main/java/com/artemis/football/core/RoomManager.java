package com.artemis.football.core;

import com.artemis.football.model.BasePlayer;
import com.artemis.football.model.MatchRoom;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author zhengenshen
 * @date 2018-05-23 16:25
 */

public class RoomManager {

    public static final int FIVE = 5;
    public static final int TEN = 10;

    /**
     * 5钻区的玩家队列
     */
    public static final Queue<BasePlayer> FIVE_ROOM = new ConcurrentLinkedDeque<>();

    /**
     * 10钻区的玩家队列
     */
    public static final Queue<BasePlayer> TEN_ROOM = new ConcurrentLinkedDeque<>();

    /**
     * 用来保存双人房间的
     */
    public static final Map<Integer, MatchRoom> ROOMS = new ConcurrentHashMap<>();

    /**
     * 为两位玩家创建房间
     */
    public static void createRoom(MatchRoom matchRoom) {
        ROOMS.put(matchRoom.getId(), matchRoom);
    }


    /**
     * 查询房间
     *
     * @param id 房间号
     * @return 房间
     */
    public static MatchRoom getRoom(int id) {
        return ROOMS.getOrDefault(id, null);
    }
}
