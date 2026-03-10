package com.shopnow.orderapp;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.shopnow")
@EntityScan(basePackages = "com.shopnow.orderapi.model")
@EnableJpaRepositories(basePackages = "com.shopnow.orderservice.repository")
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
