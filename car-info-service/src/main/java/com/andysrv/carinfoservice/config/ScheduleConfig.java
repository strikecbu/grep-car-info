package com.andysrv.carinfoservice.config;

import com.andysrv.carinfoservice.producer.CarInfoScrapeProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@Configuration
@Slf4j
public class ScheduleConfig {


    private final CarInfoScrapeProducer producer;

    public ScheduleConfig(CarInfoScrapeProducer producer) {
        this.producer = producer;
    }

    @Scheduled(fixedDelayString = "${carinfo.schedule.scrape-car.fixedDelay}", initialDelay = 10000)
    public void sendScrapEventJob() {
        log.info("Schedule is on, send scrape-events");
        producer.sendScrapeEvent();
    }

}
