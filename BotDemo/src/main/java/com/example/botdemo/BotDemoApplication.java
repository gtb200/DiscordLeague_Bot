package com.example.botdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.example.botdemo.biz.repositories")
@EntityScan("com.example.botdemo.biz")
@SpringBootApplication
public class BotDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BotDemoApplication.class, args);
    }

}
