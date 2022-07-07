package com.andysrv.carconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class CarConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarConfigApplication.class, args);
    }

}
