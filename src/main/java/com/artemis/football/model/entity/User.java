package com.artemis.football.model.entity;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zhengenshen
 * @date 2018-05-21 16:33
 */

@Data
public class User {

    private Integer id;
    private String username;
    private String password;

    public boolean check() {
        if (StringUtils.equals(this.username, "admin") && StringUtils.equals(this.password, "password")) {
            return true;
        } else {
            return false;
        }
    }
}
