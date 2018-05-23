package com.artemis.football.repository;

import com.artemis.football.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author zhengenshen
 * @date 2018-05-22 18:11
 */

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsernameAndPassword(String username, String password);
}
