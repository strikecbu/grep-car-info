package com.andysrv.cargateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CarGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarGatewayApplication.class, args);
    }

}
