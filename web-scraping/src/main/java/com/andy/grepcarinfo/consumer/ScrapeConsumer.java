package com.andy.grepcarinfo.consumer;

import com.andy.grepcarinfo.exception.WrongScrapeCommandException;
import com.andy.grepcarinfo.model.VendorType;
import com.andy.grepcarinfo.service.CarInfoUpdateExecuteService;
 import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ScrapeConsumer {

    private final CarInfoUpdateExecuteService carInfoUpdateExecuteService;

    public ScrapeConsumer(CarInfoUpdateExecuteService carInfoUpdateExecuteService) {
        this.carInfoUpdateExecuteService = carInfoUpdateExecuteService;
    }


    @KafkaListener(topics = "scrape-events")
    @Timed(value = "ScrapeConsumer.consumer.time", description = "Time taken to receive command event")
    public void consumer(ConsumerRecord<String, String> record) {
        log.info("consumer record: {}", record);
        ScrapeType scrapeType = ScrapeType.values(record.key())
                .orElseThrow(() -> new WrongScrapeCommandException("Invalid KEY"));
        String value = record.value();
        String[] split = value.split("-");
        if (split.length != 2) {
            throw new WrongScrapeCommandException("Invalid VALUE");
        }
        VendorType vendorType = VendorType.values(split[0])
                .orElseThrow(() -> new WrongScrapeCommandException("Invalid VALUE"));
        switch (scrapeType) {
            case CAR:
                carInfoUpdateExecuteService.execute(vendorType, split[1]);
                break;
        }
    }
}
