package com.lean.cdc.sink.service;

import com.lean.cdc.sink.entity.SinkDebeziumTopic;
import com.lean.cdc.sink.repository.SinkDebeziumTopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SinkDebeziumTopicService {
    private final SinkDebeziumTopicRepository repository;

    public ResponseEntity<List<SinkDebeziumTopic>> getAllSinks(){
        List<SinkDebeziumTopic> topics = repository.findAll();
        return ResponseEntity.ok(topics);
    }


}
