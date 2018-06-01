package com.artemis.football.common;

/**
 * @author zhengenshen
 * @date 2018-05-22 14:54
 */

public class ActionType {

    // 校验
    public static final int AUTH = 1;

    /**
     * 匹配房间
     */
    public static final int MATCH = 3;

    /**
     * 匹配成功
     */
    public static final int MATCH_SUCCESS = 4;

    /**
     * 准备
     */
    public static final int READY = 5;

    /**
     * 准备
     */
    public static final int ALL_READY = 6;

    /**
     * 争夺抢球权
     */
    public static final int SCRAMBLE = 7;

    /**
     * 争夺抢球权完毕
     */
    public static final int SCRAMBLE_END = 8;

    /**
     * 玩家 断开连接
     */
    public static final int PLAYER_OFFLINE = 9;

    /**
     * 数据包
     */
    public static final int DATA_PACK = 10;

    public static final int GAME_OVER = 11;

    /**
     * 匹配超时
     */
    public static final int MATCHING_TIME_OUT = 12;

    public static final int DEFAULT = 0;
}
