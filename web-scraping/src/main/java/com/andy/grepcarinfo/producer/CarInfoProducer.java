package com.andy.grepcarinfo.producer;

import com.andy.grepcarinfo.model.CarView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;
import reactor.kafka.sender.SenderResult;

@Service
public class CarInfoProducer {

    private final KafkaSender<String, String> sender;
    private final ObjectMapper objectMapper;
    @Value("${carinfo.kafka.producer.topic}")
    private String topic;

    public CarInfoProducer(KafkaSender<String, String> sender, ObjectMapper objectMapper) {
        this.sender = sender;
        this.objectMapper = objectMapper;
    }


    public Flux<SenderResult<CarView>> sendEvent(Flux<CarView> carView$) {

        Flux<SenderRecord<String, String, CarView>> senderRecordFlux = carView$.map(view -> {
            try {
                String infoStr = objectMapper.writeValueAsString(view);
                String vendorType = view.getVendorType()
                        .toString();
                ProducerRecord<String, String> record = new ProducerRecord<>(
                        topic,
                        vendorType,
                        infoStr);
                return SenderRecord.create(record, view);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
        return sender.send(senderRecordFlux);
    }
}
