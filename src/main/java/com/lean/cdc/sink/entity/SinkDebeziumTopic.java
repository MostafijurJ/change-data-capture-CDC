package com.lean.cdc.sink.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "debezium_topic")
public class SinkDebeziumTopic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    int id;
    String name;
    int age;
    String email;
}
