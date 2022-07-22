package com.andy.grepcarinfo.service;

import com.andy.grepcarinfo.model.CarView;
import com.andy.grepcarinfo.model.VendorType;
import com.andy.grepcarinfo.producer.CarInfoProducer;
import com.andy.grepcarinfo.producer.ScrapeDoneProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.SenderResult;

import java.io.IOException;

@Service
@Slf4j
public class CarInfoUpdateExecuteServiceImpl implements CarInfoUpdateExecuteService {

    private final ConnectGrepDataService shouShiDataService;
    private final CarInfoProducer carInfoProducer;
    private final ScrapeDoneProducer scrapeDoneProducer;
    @Value("${scrap.shoushi.url}")
    private String scrapShouShiUrl;

    public CarInfoUpdateExecuteServiceImpl(@Qualifier("connectGrepDataServiceShowShi2Impl") ConnectGrepDataService shouShiDataService,
                                           CarInfoProducer carInfoProducer, ScrapeDoneProducer scrapeDoneProducer) {
        this.shouShiDataService = shouShiDataService;
        this.carInfoProducer = carInfoProducer;
        this.scrapeDoneProducer = scrapeDoneProducer;
    }

    @Override
    public void execute(VendorType vendor, String jobId) {
        switch (vendor) {
            case SHOU_SHI -> {
                try {
                    Flux<CarView> carViewFlux = shouShiDataService.scrapWebData(scrapShouShiUrl);
                    carInfoProducer.sendEvent(carViewFlux)
                            .map(SenderResult::correlationMetadata)
                            .collectList()
                            .doOnNext(list -> {
                                log.info("Send to kafka all data: {}", list);
                            })
                            .flatMap(noop -> scrapeDoneProducer.sendEvent(Mono.just(jobId)))
                            .subscribe(produceResult -> {
                                String topic = produceResult.recordMetadata()
                                        .topic();
                                log.info("Send {} event success.", topic);
                            });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
