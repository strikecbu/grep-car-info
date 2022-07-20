package com.andysrv.cargateway.filter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Configuration
@Slf4j
public class CorrelationFilterConfig {

    private String KEY = "carinfo-correlation-key";
    ;


    @Order(1)
    @Bean
    public GlobalFilter responseFilter() {
        return (exchange, chain) -> {
            HttpHeaders headers = exchange.getRequest()
                    .getHeaders();
            String correlationId = headers.getFirst(KEY);
            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> {
                        exchange.getResponse()
                                .getHeaders()
                                .set(KEY, correlationId);
                    }));
        };
    }

    @Order(0)
    @Bean
    public GlobalFilter requestFilter() {
        return (exchange, chain) -> {
            HttpHeaders headers = exchange.getRequest()
                    .getHeaders();
            if (headers.containsKey(KEY)) {
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

    @Order(1)
    @Bean
    public GatewayFilter verifyFirebaseTokenFilter(FirebaseAuth firebaseAuth) {
        return (exchange, chain) -> {
            HttpHeaders headers = exchange.getRequest()
                    .getHeaders();
            if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                return responseUnauthorized(exchange);
            }


            String authorization = headers.getFirst(HttpHeaders.AUTHORIZATION);
            String token = authorization.replaceAll("Bearer ", "");

            try {
                //另一種寫法
//                FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token, true);

                FirebaseToken verifyIdToken = firebaseAuth.verifyIdToken(token, true);
                if (!verifyIdToken.isEmailVerified()) {
                    return responseUnauthorized(exchange);
                }
            } catch (FirebaseAuthException e) {
                log.warn("Authorization fail: [{}] {}", e.getAuthErrorCode(), e.getMessage());
                return responseUnauthorized(exchange);
            }


            return chain.filter(exchange);
        };
    }

    private Mono<Void> responseUnauthorized(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }
}
