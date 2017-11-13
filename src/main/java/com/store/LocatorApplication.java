package com.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class LocatorApplication {

    public static void main(String[] args) {

        SpringApplication.run(LocatorApplication.class, args);

    }
}
