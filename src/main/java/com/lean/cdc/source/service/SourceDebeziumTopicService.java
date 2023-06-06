package com.lean.cdc.source.service;

import com.lean.cdc.source.entity.DebeziumTopic;
import com.lean.cdc.source.repository.DebeziumTopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class SourceDebeziumTopicService {
    private final DebeziumTopicRepository repository;


    public ResponseEntity<DebeziumTopic> saveTopic (DebeziumTopic topic){
        topic.setDate(new Date());
         repository.save(topic);
         return  ResponseEntity.ok(topic);
    }




}
