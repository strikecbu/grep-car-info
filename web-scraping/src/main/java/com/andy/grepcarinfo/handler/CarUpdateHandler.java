package com.andy.grepcarinfo.handler;

import com.andy.grepcarinfo.model.CarView;
import com.andy.grepcarinfo.model.VendorType;
import com.andy.grepcarinfo.service.ConnectGrepDataService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
public class CarUpdateHandler {

    private final ConnectGrepDataService dataService;
    @Value("${scrap.shoushi.url}")
    private String scrapShouShiUrl;

    public CarUpdateHandler(@Qualifier("connectGrepDataServiceShowShi2Impl") ConnectGrepDataService dataService) {
        this.dataService = dataService;
    }

    public Mono<ServerResponse> updateCar(ServerRequest request) {
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
            carViewFlux = dataService.scrapWebData(scrapShouShiUrl);
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
