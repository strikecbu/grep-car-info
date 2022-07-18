package com.andysrv.carinfoservice.service;

import com.andysrv.carinfoservice.dto.CarView;
import com.andysrv.carinfoservice.dto.VendorType;
import com.andysrv.carinfoservice.entity.CarInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CarInfoServiceTest {

    @Autowired
    private CarInfoService carInfoService;

    @Test
    void saveOrUpdate() {
        CarInfo carInfo = CarInfo.builder()
                .title("2011 SuperCar sonic")
                .year(2015)
                .brand("BMW")
                .price(1000000.0)
                .queryTime(LocalDateTime.now())
                .vendorType(VendorType.SHOU_SHI)
                .build();
        Mono<CarInfo> mono = carInfoService.saveOrUpdate(carInfo);
        StepVerifier.create(mono)
                .consumeNextWith(carInfo1 -> {
                    assert carInfo1.getId() != null;
                    assert carInfo1.getPriceIds() != null;
                    assertEquals(carInfo.getTitle(), carInfo1.getTitle());
                })
                .verifyComplete();

    }

    @Test
    void getAllCarInfo() {
        Mono<List<CarView>> list$ = carInfoService.getAllCarInfo()
                .collectList();
        StepVerifier.create(list$)
                .consumeNextWith(carInfos -> {
                    String s = "";
                })
                .verifyComplete();

    }
}
