package com.andy.grepcarinfo.producer;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;
import reactor.kafka.sender.SenderResult;

@Service
public class ScrapeDoneProducer {

    private final KafkaSender<String, String> sender;
    @Value("${carinfo.kafka.producer.scrapeDoneEvent.topic}")
    private String topic;

    public ScrapeDoneProducer(KafkaSender<String, String> sender) {
        this.sender = sender;
    }

    public Mono<SenderResult<String>> sendEvent(Mono<String> jobIds$) {

        Mono<SenderRecord<String, String, String>> senderRecordFlux = jobIds$.map(id -> {
            ProducerRecord<String, String> record = new ProducerRecord<>(
                    topic,
                    "SCRAPE",
                    id);
            return SenderRecord.create(record, id);
        });
        return sender.send(senderRecordFlux).single();
    }
}
