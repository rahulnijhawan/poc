package com.rahul.springsecurityv2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class Springsecurityv2Application {

    public static void main(String[] args) {
        SpringApplication.run(Springsecurityv2Application.class, args);
    }

}
