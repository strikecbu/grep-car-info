package com.andysrv.carinfoservice.producer;

import com.andysrv.carinfoservice.dto.VendorType;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

@Component
@Slf4j
public class CarInfoScrapeProducer {

    private final KafkaSender<String, String> sender;
    @Value("${carinfo.kafka.producer.topic}")
    private String topic;

    public CarInfoScrapeProducer(KafkaSender<String, String> sender) {
        this.sender = sender;
    }

    public void sendScrapeEvent(VendorType vendor) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, "CAR", vendor.toString());
        SenderRecord<String, String, String> senderRecord = SenderRecord.create(record, "SHOU_SHI");
        sender.send(Mono.just(senderRecord))
                .single()
                .subscribe(result -> {
                    log.info("Send event '{}' completed.", result.correlationMetadata());
                });
    }
}
