package com.andysrv.carinfoservice.consumer;

import com.andysrv.carinfoservice.dto.CarEvent;
import com.andysrv.carinfoservice.entity.CarInfo;
import com.andysrv.carinfoservice.factory.KafkaReceiverFactory;
import com.andysrv.carinfoservice.mapper.CarInfoMapper;
import com.andysrv.carinfoservice.service.CarInfoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.ReceiverRecord;

@Component
@Slf4j
public class CarInfoConsumer implements CommandLineRunner {

    private final KafkaReceiverFactory factory;
    private final CarInfoService carInfoService;
    private final ObjectMapper objectMapper;
    private final CarInfoMapper carInfoMapper;

    @Value("${carinfo.kafka.consumer.carInfo.topic}")
    private String consumeCarInfoTopic;

    public CarInfoConsumer(
            KafkaReceiverFactory factory, CarInfoService carInfoService,
            ObjectMapper objectMapper, CarInfoMapper carInfoMapper) {
        this.factory = factory;
        this.carInfoService = carInfoService;
        this.objectMapper = objectMapper;

        this.carInfoMapper = carInfoMapper;
    }

    @Override
    public void run(String... args) {
        factory.create(consumeCarInfoTopic)
                .flatMap(this::handleMessage)
                .onErrorContinue((throwable, o) -> {
                    log.warn("Consume {} fail: {}", consumeCarInfoTopic, throwable.getMessage());
                })
                .log()
                .subscribe();
    }

    @Timed(value = "CarInfoConsumer.handleMessage.time", description = "Time taken to consume carInfo then save")
    private Publisher<? extends CarInfo> handleMessage(ReceiverRecord<String, String> receiverRecord) {
        return Mono.just(receiverRecord)
                .map(record -> {
                    String json = record.value();
                    try {
                        return objectMapper.readValue(json, CarEvent.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(carInfoMapper::dtoToEntity)
                .flatMap(carInfoService::saveOrUpdate)
                .doOnNext(noop -> receiverRecord.receiverOffset()
                        .acknowledge())
                .doOnError(throwable -> {
                    String s = "";
                });
    }

}
