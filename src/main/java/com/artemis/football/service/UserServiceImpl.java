package com.artemis.football.service;

import com.artemis.football.model.entity.User;
import com.artemis.football.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author zhengenshen
 * @date 2018-05-22 18:16
 */

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User login(String username, String password) {
        // String encode = MD5Tools.crypt(password);

        Optional<User> resp = userRepository.findByUsernameAndPassword(username, password);
        return resp.orElse(null);
    }
}
