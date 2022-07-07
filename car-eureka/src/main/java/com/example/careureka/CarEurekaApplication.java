package com.example.careureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class CarEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarEurekaApplication.class, args);
    }

}
