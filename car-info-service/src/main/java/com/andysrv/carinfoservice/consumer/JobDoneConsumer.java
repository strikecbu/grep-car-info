package com.andysrv.carinfoservice.consumer;

import com.andysrv.carinfoservice.factory.KafkaReceiverFactory;
import com.andysrv.carinfoservice.stream.AnnounceNewsStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.ReceiverRecord;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class JobDoneConsumer implements CommandLineRunner {

    private final KafkaReceiverFactory factory;

    private final AnnounceNewsStream announceNewsStream;

    @Value("${carinfo.kafka.consumer.jobDone.topic}")
    private String topic;

    public JobDoneConsumer(KafkaReceiverFactory factory, AnnounceNewsStream announceNewsStream) {
        this.factory = factory;
        this.announceNewsStream = announceNewsStream;
    }

    @Override
    public void run(String... args) {
        factory.create(topic)
                .flatMap(this::handleMessage)
                .onErrorContinue((throwable, o) -> {
                    log.warn("Consume {} fail: {}", topic, throwable.getMessage());
                })
                .log()
                .subscribe();
    }

    private Publisher<? extends String> handleMessage(ReceiverRecord<String, String> receiverRecord) {
        return Mono.just(receiverRecord)
                .filter(record -> "SCRAPE".equalsIgnoreCase(record.key()))
                .map(ConsumerRecord::value)
                .doOnNext(record -> {
                    String timeStr = LocalDateTime.now()
                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    announceNewsStream.next(String.format("資料最後更新時間: %s", timeStr));
                    log.info("資料最後更新時間: {}", timeStr);

                })
                .doOnNext(noop -> receiverRecord.receiverOffset()
                        .acknowledge())
                .doOnError(throwable -> {
                    String s = "";
                });
    }

}
