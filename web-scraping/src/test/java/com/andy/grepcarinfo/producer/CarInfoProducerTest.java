package com.andy.grepcarinfo.producer;

import com.andy.grepcarinfo.model.CarView;
import com.andy.grepcarinfo.model.VendorType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("local")
@EmbeddedKafka(topics = {"${carinfo.kafka.producer.carInfoEvent.topic}"}, partitions = 3)
@TestPropertySource(properties = {
        "spring.kafka.producer.bootstrap-servers=${spring.embedded.kafka.brokers}",
        "spring.kafka.admin.properties.bootstrap.servers=${spring.embedded.kafka.brokers}"
})
class CarInfoProducerTest {

    private Consumer<String, String> consumer;

    @Autowired
    private EmbeddedKafkaBroker broker;

    @Value("${carinfo.kafka.producer.carInfoEvent.topic}")
    private String topic;

    @Autowired
    private CarInfoProducer producer;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        Map<String, Object> map = new HashMap<>(KafkaTestUtils.consumerProps("group1", "true", broker));
        consumer = new DefaultKafkaConsumerFactory<>(map,
                new StringDeserializer(),
                new StringDeserializer()).createConsumer();
        broker.consumeFromAllEmbeddedTopics(consumer);
    }

    @AfterEach
    void tearDown() {
        consumer.close();
    }

    @Test
    void sendEvent() throws JsonProcessingException {

        CarView carView = CarView.builder()
                .vendorType(VendorType.SHOU_SHI)
                .brand("Toyota")
                .year(2015)
                .queryTime(LocalDateTime.now())
                .title("2015 Happy sale")
                .price(250000.0)
                .detailUrl("http://yahoo.com")
                .build();
        producer.sendEvent(Flux.just(carView))
                .log()
                .single()
                .subscribe(carViewSenderResult -> {
                    System.out.println("Producer send: " + carViewSenderResult.correlationMetadata());
                });

        ConsumerRecord<String, String> record = KafkaTestUtils.getSingleRecord(consumer, topic, 5000);
        CarView carView1 = objectMapper.readValue(record.value(), CarView.class);
        assertEquals(carView.getBrand(), carView1.getBrand());
        assertEquals(carView.getTitle(), carView1.getTitle());
        System.out.println("Received info: " + record.value());

    }


}
