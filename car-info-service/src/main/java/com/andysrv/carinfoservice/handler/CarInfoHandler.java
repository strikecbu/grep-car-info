package com.andysrv.carinfoservice.handler;

import com.andysrv.carinfoservice.dto.VendorType;
import com.andysrv.carinfoservice.producer.CarInfoScrapeProducer;
import com.andysrv.carinfoservice.service.CarInfoService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class CarInfoHandler {

    private final CarInfoService carInfoService;
    private final CarInfoScrapeProducer scrapeProducer;

    public CarInfoHandler(CarInfoService carInfoService, CarInfoScrapeProducer scrapeProducer) {
        this.carInfoService = carInfoService;
        this.scrapeProducer = scrapeProducer;
    }

    public Mono<ServerResponse> getAllCars(ServerRequest request) {
        return carInfoService.getAllCarInfo()
                .collectList()
                .flatMap(list -> ServerResponse.ok()
                        .bodyValue(list));
    }

    public Mono<ServerResponse> sendScrapeEvent(ServerRequest request) {
        String vendor = request.queryParam("vendor").orElse("");
        Optional<VendorType> vendorType = VendorType.values(vendor);
        if (vendorType
                .isEmpty()) {
            return ServerResponse.badRequest()
                    .bodyValue("Not available vendor");
        }
        scrapeProducer.sendScrapeEvent(vendorType.get());
        return ServerResponse.accepted()
                .build();
    }
}
