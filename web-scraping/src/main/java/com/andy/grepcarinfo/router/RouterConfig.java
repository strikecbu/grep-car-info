package com.andy.grepcarinfo.router;

import com.andy.grepcarinfo.handler.CarUpdateHandler;
import com.andy.grepcarinfo.handler.ConfigHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> carUpdateRoute(CarUpdateHandler handler) {
        return RouterFunctions.route()
                .GET("/carUpdate", handler::updateCar)
                .build();
    }
    @Bean
    public RouterFunction<ServerResponse> configRoute(ConfigHandler handler) {
        return RouterFunctions.route()
                .GET("/configs", handler::getConfigs)
                .build();
    }
}
