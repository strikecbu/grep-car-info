package com.andy.grepcarinfo.handler;

import com.andy.grepcarinfo.model.CarView;
import com.andy.grepcarinfo.model.VendorType;
import com.andy.grepcarinfo.producer.CarInfoProducer;
import com.andy.grepcarinfo.service.ConnectGrepDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.SenderResult;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
@Slf4j
public class CarUpdateHandler {

    private final ConnectGrepDataService dataService;
    private final CarInfoProducer carInfoProducer;
    @Value("${scrap.shoushi.url}")
    private String scrapShouShiUrl;

    public CarUpdateHandler(@Qualifier("connectGrepDataServiceShowShi2Impl") ConnectGrepDataService dataService,
                            CarInfoProducer carInfoProducer) {
        this.dataService = dataService;
        this.carInfoProducer = carInfoProducer;
    }

    public Mono<ServerResponse> updateCar(ServerRequest request) {
        String correlationKey = request.headers()
                .firstHeader("carinfo-correlation-key");
        log.info("correlationKey: {}", correlationKey);
        Optional<String> type = request.queryParam("type");
        Optional<VendorType> vendorTypeOption = Arrays.stream(VendorType.values())
                .filter(vt -> vt.toString()
                        .equalsIgnoreCase(type.orElse("")))
                .findFirst();
        if (vendorTypeOption.isEmpty()) {
            return ServerResponse.badRequest()
                    .bodyValue("Not have update type");
        }
        Flux<CarView> carViewFlux = null;
        try {
            Flux<CarView> viewFlux = dataService.scrapWebData(scrapShouShiUrl);
            carViewFlux = carInfoProducer.sendEvent(viewFlux)
                    .map(SenderResult::correlationMetadata);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return carViewFlux.collectList()
                .flatMap(list ->
                        ServerResponse.ok()
                                .bodyValue(list)
                );
    }
}
