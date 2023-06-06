package com.lean.cdc.source.repository;


import com.lean.cdc.source.entity.DebeziumTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DebeziumTopicRepository extends JpaRepository<DebeziumTopic, Long> {

}
