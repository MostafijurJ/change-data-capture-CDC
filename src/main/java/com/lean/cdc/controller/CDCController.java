package com.lean.cdc.controller;

import com.lean.cdc.sink.entity.SinkDebeziumTopic;
import com.lean.cdc.sink.service.SinkDebeziumTopicService;
import com.lean.cdc.source.entity.DebeziumTopic;
import com.lean.cdc.source.service.SourceDebeziumTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/debezium")
public class CDCController {

    @Autowired
    private  SourceDebeziumTopicService sourceDebeziumTopicService;
    @Autowired
    private  SinkDebeziumTopicService sinkDebeziumTopicService;

    @PostMapping("/saveTopic")
    public ResponseEntity<DebeziumTopic> saveData(@RequestBody DebeziumTopic topic){
        return sourceDebeziumTopicService.saveTopic(topic);
    }

    @GetMapping("/getAllSinkTopics")
    public ResponseEntity<List<SinkDebeziumTopic>> getAllTopics(){
        return sinkDebeziumTopicService.getAllSinks();
    }



}
