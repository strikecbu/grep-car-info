package com.andy.grepcarinfo.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
//@Profile("local")
public class KafkaNewTopicConfig {

    @Value("${carinfo.kafka.producer.carInfoEvent.topic}")
    private String topic;
    @Value("${carinfo.kafka.consumer.scrapeCar.topic}")
    private String scrapeTopic;
    @Value("${carinfo.kafka.producer.scrapeDoneEvent.topic}")
    private String jobDoneTopic;

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
    @Bean
    public NewTopic jobDoneEvents() {
        return TopicBuilder
                .name(jobDoneTopic)
                .partitions(3)
                .replicas(3)
                .build();
    }


}
