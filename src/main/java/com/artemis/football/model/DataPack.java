package com.artemis.football.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

/**
 * @author zhengenshen
 * @date 2018-05-30 15:08
 */

@Data
@Document
public class DataPack {

    @Id
    private Long id;
    private Integer roomId;
    private Integer fromUid;
    private Integer toUid;
    private Integer count;
    private Units units;
    private long timestamp;
}
