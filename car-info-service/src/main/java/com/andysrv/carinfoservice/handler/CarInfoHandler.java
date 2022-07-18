package com.andysrv.carinfoservice.handler;

import com.andysrv.carinfoservice.service.CarInfoService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class CarInfoHandler {

    private final CarInfoService carInfoService;

    public CarInfoHandler(CarInfoService carInfoService) {
        this.carInfoService = carInfoService;
    }

    public Mono<ServerResponse> getAllCars(ServerRequest request) {
        return carInfoService.getAllCarInfo()
                .collectList()
                .flatMap(list -> ServerResponse.ok()
                        .bodyValue(list));
    }
}
