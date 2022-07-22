package com.andysrv.carinfoservice.handler;

import com.andysrv.carinfoservice.dto.ReScrapeRequest;
import com.andysrv.carinfoservice.dto.VendorType;
import com.andysrv.carinfoservice.producer.CarInfoScrapeProducer;
import com.andysrv.carinfoservice.service.CarInfoService;
import com.andysrv.carinfoservice.stream.AnnounceNewsStream;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class CarInfoHandler {

    private final CarInfoService carInfoService;
    private final CarInfoScrapeProducer scrapeProducer;
    private final AnnounceNewsStream announceNewsStream;

    public CarInfoHandler(CarInfoService carInfoService, CarInfoScrapeProducer scrapeProducer,
                          AnnounceNewsStream announceNewsStream) {
        this.carInfoService = carInfoService;
        this.scrapeProducer = scrapeProducer;
        this.announceNewsStream = announceNewsStream;
    }

    public Mono<ServerResponse> getAllCars(ServerRequest request) {
        return carInfoService.getAllCarInfo()
                .collectList()
                .flatMap(list -> ServerResponse.ok()
                        .bodyValue(list));
    }

    public Mono<ServerResponse> sendScrapeEvent(ServerRequest request) {
        return request.bodyToMono(ReScrapeRequest.class)
                .map(ReScrapeRequest::getVendor)
                .map(VendorType::values)
                .flatMap(vendorType -> {
                    if (vendorType.isEmpty()) {
                        return Mono.error(new RuntimeException("Not available vendor"));
                    }
                    scrapeProducer.sendScrapeEvent(vendorType.get());
                    return ServerResponse.accepted()
                            .build();
                })
                .onErrorResume(throwable -> ServerResponse.badRequest()
                        .bodyValue(throwable.getMessage()));
    }

    public Mono<ServerResponse> announceNewsStream(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(announceNewsStream.getNewsFlux(), String.class);
    }


    public Mono<ServerResponse> testStream(ServerRequest request) {
        return request.bodyToMono(ReScrapeRequest.class)
                .doOnNext(reScrapeRequest -> {
                    String value = reScrapeRequest.getVendor();
                    announceNewsStream.next(value);
                })
                .flatMap(req -> ServerResponse.ok().build());
    }
}
