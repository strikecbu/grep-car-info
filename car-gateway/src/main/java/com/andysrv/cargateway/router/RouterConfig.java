package com.andysrv.cargateway.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class RouterConfig {

    @Autowired
    @Qualifier("verifyFirebaseTokenFilter")
    GatewayFilter verifyFirebaseTokenFilter;

    @Bean
    public RouteLocator route(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(spec -> spec
                        .path("/carinfo/web-scraping/**")
                        .filters(filterSpec -> filterSpec
                                .rewritePath("/carinfo/web-scraping/(?<path>.*)", "/${path}")
                                .addResponseHeader("X-Response-Timestamp", new Date().toString())
                        )
                        .uri("lb://WEB-SCRAPING")
                )
                .route(spec -> spec
                        .path("/car-info-service/cloudcar/**")
                        .filters(filterSpec -> filterSpec
                                .rewritePath("/car-info-service/(?<path>.*)", "/${path}")
                                .addResponseHeader("X-Response-Timestamp", new Date().toString())
                                .filter(verifyFirebaseTokenFilter)
                        )
                        .uri("lb://CAR-INFO-SERVICE")
                )
                .route(spec -> spec
                        .path("/car-info-service/public/**")
                        .filters(filterSpec -> filterSpec
                                .rewritePath("/car-info-service/(?<path>.*)", "/${path}")
                                .addResponseHeader("X-Response-Timestamp", new Date().toString())
                        )
                        .uri("lb://CAR-INFO-SERVICE")
                )
                .build();
    }
}
