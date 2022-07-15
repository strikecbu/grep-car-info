package com.andysrv.carinfoservice.consumer;

import com.andysrv.carinfoservice.dto.CarView;
import com.andysrv.carinfoservice.entity.CarInfo;
import com.andysrv.carinfoservice.mapper.CarInfoMapper;
import com.andysrv.carinfoservice.service.CarInfoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

@Component
@Slf4j
public class CarInfoConsumer implements CommandLineRunner {

    private final ReceiverOptions<String, String> options;

    private final CarInfoService carInfoService;
    private final ObjectMapper objectMapper;
    private final CarInfoMapper carInfoMapper;

    public CarInfoConsumer(ReceiverOptions<String, String> options,
                           CarInfoService carInfoService,
                           ObjectMapper objectMapper, CarInfoMapper carInfoMapper) {
        this.options = options;
        this.carInfoService = carInfoService;
        this.objectMapper = objectMapper;

        this.carInfoMapper = carInfoMapper;
    }

    @Override
    public void run(String... args) {
        KafkaReceiver.create(options)
                .receive()
                .flatMap(this::handleMessage)
                .onErrorContinue((throwable, o) -> {
                    log.warn("Saving carInfo fail: {}", throwable.getMessage());
                })
                .log()
                .subscribe();
    }

    public Publisher<? extends CarInfo> handleMessage(ReceiverRecord<String, String> receiverRecord) {
        return Mono.just(receiverRecord)
                .map(record -> {
                    String json = record.value();
                    try {
                        return objectMapper.readValue(json, CarView.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(carInfoMapper::dtoToEntity)
                .flatMap(carInfoService::saveOrUpdate)
                .doOnNext(noop -> receiverRecord.receiverOffset().acknowledge());
    }
}
