package com.artemis.football.model;

import lombok.Data;

import javax.persistence.Id;

/**
 * 匹配房间
 *
 * @author zhengenshen
 * @date 2018-05-22 16:16
 */

@Data
public class MatchRoom {

    @Id
    private Long id;
    private int score;
    private BasePlayer player1;

    private BasePlayer player2;

    private int winner;
}
