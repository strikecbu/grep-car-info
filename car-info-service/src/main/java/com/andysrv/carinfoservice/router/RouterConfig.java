package com.andysrv.carinfoservice.router;

import com.andysrv.carinfoservice.handler.CarInfoHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;

@Configuration
public class RouterConfig {
    @Bean
    public RouterFunction<ServerResponse> carRoute(CarInfoHandler handler) {
        return RouterFunctions.route()
                .nest(path("/cloudcar"), builder -> {
                    builder.GET("cars", handler::getAllCars);
                })
                .build();
    }
}
