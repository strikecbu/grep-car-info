package com.andysrv.cargateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Configuration
@Slf4j
public class CorrelationFilterConfig {

    private String KEY = "carinfo-correlation-key";;


    @Order(1)
    @Bean
    public GlobalFilter responseFilter() {
        return (exchange, chain) -> {
            HttpHeaders headers = exchange.getRequest()
                    .getHeaders();
            String correlationId = headers.getFirst(KEY);
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                exchange.getResponse().getHeaders().set(KEY, correlationId);
            }));
        };
    }

    @Order(0)
    @Bean
    public GlobalFilter requestFilter() {
        return (exchange, chain) -> {
            HttpHeaders headers = exchange.getRequest()
                    .getHeaders();
            if(headers.containsKey(KEY)) {
                log.info("There have correlation key already: {}", headers.getFirst(KEY));
            } else {
                String correlationId = UUID.randomUUID()
                        .toString();
                ServerWebExchange webExchange = exchange.mutate()
                        .request(builder -> {
                            builder.header(KEY, correlationId);
                        })
                        .build();
                return chain.filter(webExchange);
            }
            return chain.filter(exchange);
        };
    }
}
