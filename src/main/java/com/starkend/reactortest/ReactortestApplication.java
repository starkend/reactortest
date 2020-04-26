package com.starkend.reactortest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class ReactortestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactortestApplication.class, args);
    }

}
