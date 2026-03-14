package com.shopnow.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableFeignClients(basePackages = "com.shopnow.order.integration")
@EnableJpaAuditing(auditorAwareRef = "springAuditorAware")
@SpringBootApplication(scanBasePackages = "com.shopnow.order")
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
