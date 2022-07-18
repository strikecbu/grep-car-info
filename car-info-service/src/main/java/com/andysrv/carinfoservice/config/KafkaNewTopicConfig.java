package com.andysrv.carinfoservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
//@Profile("local")
public class KafkaNewTopicConfig {

    @Value("${carinfo.kafka.producer.topic}")
    private String topic;
    @Value("${carinfo.kafka.consumer.topic}")
    private String scrapeTopic;

    @Bean
    public NewTopic carInfoEvents() {
        return TopicBuilder
                .name(topic)
                .partitions(3)
                .replicas(3)
                .build();
    }

    @Bean
    public NewTopic scrapeEvents() {
        return TopicBuilder
                .name(scrapeTopic)
                .partitions(3)
                .replicas(3)
                .build();
    }


}
