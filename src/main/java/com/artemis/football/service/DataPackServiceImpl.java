package com.artemis.football.service;

import com.artemis.football.model.DataPack;
import com.artemis.football.repository.DataPackRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author zhengenshen
 * @date 2018-05-30 15:26
 */

@Slf4j
@Service
public class DataPackServiceImpl implements DataPackService {

    @Autowired
    private DataPackRepository repository;

    @Async
    @Override
    public void asyncSave(DataPack dataPack) {
        String yyyyMMddHHmmss = dataPack.getFromUid() + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        dataPack.setId(Long.parseLong(yyyyMMddHHmmss));
        repository.save(dataPack);
    }
}
