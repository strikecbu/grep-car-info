package com.andy.grepcarinfo.service;

import com.andy.grepcarinfo.model.Car;
import com.andy.grepcarinfo.model.CarView;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.List;


class ConnectGrepDataServiceShowShi2ImplTest {

    @Test
    void grepCarData() throws IOException {
        ConnectGrepDataServiceShowShi2Impl service = new ConnectGrepDataServiceShowShi2Impl();
        List<Car> cars = service.grepCarData("https://sscars.com.tw/car");
    }


    @Test
    void scrapWebData() throws IOException, InterruptedException {
        ConnectGrepDataServiceShowShi2Impl service = new ConnectGrepDataServiceShowShi2Impl();

        Flux<CarView> carViewFlux = service.scrapWebData("https://sscars.com.tw/car");
//        StepVerifier.create(carViewFlux)
//                .expectNextCount(55)
//                .verifyComplete();

        List<CarView> block = carViewFlux.collectList()
                .block();
        System.out.println(block);
    }
}
