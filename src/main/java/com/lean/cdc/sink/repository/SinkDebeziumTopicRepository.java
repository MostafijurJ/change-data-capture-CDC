package com.lean.cdc.sink.repository;


import com.lean.cdc.sink.entity.SinkDebeziumTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SinkDebeziumTopicRepository extends JpaRepository<SinkDebeziumTopic, Long> {


}
