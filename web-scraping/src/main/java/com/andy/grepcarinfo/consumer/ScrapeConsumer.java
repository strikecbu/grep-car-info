package com.andy.grepcarinfo.consumer;

import com.andy.grepcarinfo.exception.WrongScrapeCommandException;
import com.andy.grepcarinfo.model.VendorType;
import com.andy.grepcarinfo.service.CarInfoUpdateExecuteService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    public void consumer(ConsumerRecord<String, String> record) throws JsonProcessingException {
        log.info("consumer record: {}", record);
        ScrapeType scrapeType = ScrapeType.values(record.key())
                .orElseThrow(() -> new WrongScrapeCommandException("Invalid KEY"));
        VendorType vendorType = VendorType.values(record.value())
                .orElseThrow(() -> new WrongScrapeCommandException("Invalid VALUE"));
        switch (scrapeType) {
            case CAR:
                carInfoUpdateExecuteService.execute(vendorType);
                break;
        }
    }
}
