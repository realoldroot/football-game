package com.artemis.football.repository;

import com.artemis.football.model.MatchRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author zhengenshen
 * @date 2018-05-23 16:18
 */

public interface MatchRoomRepository extends MongoRepository<MatchRoom, Long> {
}
