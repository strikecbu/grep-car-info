package com.andy.grepcarinfo.service;

import com.andy.grepcarinfo.model.CarView;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;


@SpringBootTest
class ConnectGrepDataServiceShowShi2ImplTest {

    @SpyBean
    ConnectGrepDataServiceShowShi2Impl service;

    @Test
    void scrapWebData() throws IOException {

        Flux<CarView> carViewFlux = service.scrapWebData("https://sscars.com.tw/car");
        StepVerifier.create(carViewFlux)
                .consumeNextWith(carView -> {
                    //驗證能取到資料即可
                    System.out.println(carView);
                    assert carView.getTitle() != null;
                    assert carView.getPrice() != null;
                    assert carView.getBrand() != null;
                    assert carView.getYear() != null;
                    assert carView.getDetailUrl() != null;
                    assert carView.getDescription() != null;
                })
                .thenCancel()
                .verify();
    }

    @Test
    void scrapWebData_error() throws IOException {

        Flux<String> flux = Flux.just(
                "https://sscars.com.tw/car/?pwb-brand=peugeot&pa_species=%e6%8e%80%e8%83%8c%e8%bb%8a",
                "https://notexist.com");
        Mockito.doReturn(flux)
                .when(service)
                .getAllQueryUrl(anyString());

        Flux<CarView> carViewFlux = service.scrapWebData("https://sscars.com.tw/car");
        StepVerifier.create(carViewFlux)
                .verifyComplete();

    }
}
