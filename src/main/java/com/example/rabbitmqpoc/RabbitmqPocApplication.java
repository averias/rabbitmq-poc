package com.example.rabbitmqpoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RabbitmqPocApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqPocApplication.class, args);
    }

}
