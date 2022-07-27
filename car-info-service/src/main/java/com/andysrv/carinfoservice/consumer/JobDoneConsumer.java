package com.andysrv.carinfoservice.consumer;

import com.andysrv.carinfoservice.factory.KafkaReceiverFactory;
import com.andysrv.carinfoservice.stream.AnnounceNewsStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class JobDoneConsumer {

    private final KafkaReceiverFactory factory;

    private final AnnounceNewsStream announceNewsStream;

    @Value("${carinfo.kafka.consumer.jobDone.topic}")
    private String topic;

    public JobDoneConsumer(KafkaReceiverFactory factory, AnnounceNewsStream announceNewsStream) {
        this.factory = factory;
        this.announceNewsStream = announceNewsStream;
    }

    @KafkaListener(topics = {"${carinfo.kafka.consumer.jobDone.topic}"})
    public void run(ConsumerRecord<String, String> record) {
        Flux.just(record)
                .flatMap(this::handleMessage)
                .onErrorContinue((throwable, o) -> {
                    log.warn("Consume {} fail: {}", topic, throwable.getMessage());
                })
                .log()
                .subscribe();
    }

    private Publisher<? extends String> handleMessage(ConsumerRecord<String, String> receiverRecord) {
        return Mono.just(receiverRecord)
                .filter(record -> "SCRAPE".equalsIgnoreCase(record.key()))
                .map(ConsumerRecord::value)
                .doOnNext(record -> {
                    String timeStr = LocalDateTime.now()
                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    announceNewsStream.next(String.format("資料最後更新時間: %s", timeStr));
                    log.info("資料最後更新時間: {}", timeStr);

                })
                .doOnError(throwable -> {
                    String s = "";
                });
    }

}
