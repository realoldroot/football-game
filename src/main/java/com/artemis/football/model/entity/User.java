package com.artemis.football.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author zhengenshen
 * @date 2018-05-21 16:33
 */

@Data
@Entity
@Table(name = "footballusers")
public class User {

    @Id
    @Column(name = "footballuserid")
    private Integer id;
    @Column(name = "userphone")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "teamname")
    private String teamName;
    @Column(name = "headimg")
    private String headimg;

}
