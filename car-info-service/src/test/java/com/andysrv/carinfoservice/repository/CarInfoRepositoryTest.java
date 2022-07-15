package com.andysrv.carinfoservice.repository;

import com.andysrv.carinfoservice.entity.CarInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CarInfoRepositoryTest {

    @Autowired
    private CarInfoRepository carInfoRepository;

    @Test
    void testSave() {
        CarInfo car = CarInfo.builder()
                .title("My first car")
                .price(100.0)
                .updateTime(LocalDateTime.now())
                .build();
        Mono<CarInfo> save = carInfoRepository.save(car).log();
        StepVerifier.create(save)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void findByPrice() {
        Flux<CarInfo> byPrice = carInfoRepository.findByPrice(100.0).log();
        StepVerifier.create(byPrice)
                .expectNextCount(1)
                .verifyComplete();
    }
}
