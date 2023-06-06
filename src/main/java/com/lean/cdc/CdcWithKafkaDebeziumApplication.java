package com.lean.cdc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CdcWithKafkaDebeziumApplication {

	public static void main(String[] args) {
		SpringApplication.run(CdcWithKafkaDebeziumApplication.class, args);
	}

}
