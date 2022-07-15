package com.andy.grepcarinfo.service;

import com.andy.grepcarinfo.model.CarView;
import com.andy.grepcarinfo.model.VendorType;
import com.andy.grepcarinfo.producer.CarInfoProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.kafka.sender.SenderResult;

import java.io.IOException;

@Service
@Slf4j
public class CarInfoUpdateExecuteServiceImpl implements CarInfoUpdateExecuteService {

    private final ConnectGrepDataService shouShiDataService;
    private final CarInfoProducer carInfoProducer;
    @Value("${scrap.shoushi.url}")
    private String scrapShouShiUrl;

    public CarInfoUpdateExecuteServiceImpl(@Qualifier("connectGrepDataServiceShowShi2Impl") ConnectGrepDataService shouShiDataService,
                                           CarInfoProducer carInfoProducer) {
        this.shouShiDataService = shouShiDataService;
        this.carInfoProducer = carInfoProducer;
    }

    @Override
    public void execute(VendorType vendor) {
        switch (vendor) {
            case SHOU_SHI -> {
                try {
                    Flux<CarView> carViewFlux = shouShiDataService.scrapWebData(scrapShouShiUrl);
                    carInfoProducer.sendEvent(carViewFlux)
                            .map(SenderResult::correlationMetadata)
                            .collectList()
                            .subscribe(list -> {
                                log.info("Send to kafka all data: {}", list);
                            });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
