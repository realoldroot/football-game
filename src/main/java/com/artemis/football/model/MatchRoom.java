package com.artemis.football.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Id;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 匹配房间
 *
 * @author zhengenshen
 * @date 2018-05-22 16:16
 */

@Data
public class MatchRoom {

    @Id
    private Integer id;
    /**
     * 房间积分
     */
    private int score;

    /**
     * 发球人
     */
    private Integer priority;

    /**
     * 角度
     */
    private Float angle;

    /**
     * 力度
     */
    private Float power;

    @JsonIgnore
    private Lock lock = new ReentrantLock();

    /**
     * 用户 key ： 用户id  value ： 用户信息
     */
    private Map<Integer, BasePlayer> players = new HashMap<>();

    private int winner;

    // public void putPlayer(Integer key, BasePlayer value) {
    //     players.put(key, value);
    // }
    //
    // public BasePlayer getPlayer(Integer key) {
    //     return players.get(key);
    // }

    public void lock() {
        this.lock.lock();
    }

    public void unlock() {
        this.lock.unlock();
    }
}
