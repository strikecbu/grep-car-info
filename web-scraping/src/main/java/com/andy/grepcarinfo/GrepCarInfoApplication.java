package com.andy.grepcarinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class GrepCarInfoApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrepCarInfoApplication.class, args);
    }

}
