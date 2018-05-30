package com.artemis.football.repository;

import com.artemis.football.model.DataPack;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author zhengenshen
 * @date 2018-05-30 15:25
 */

public interface DataPackRepository extends MongoRepository<DataPack, Integer> {
}
