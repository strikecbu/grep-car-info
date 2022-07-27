package com.andysrv.carinfoservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@Profile("dev")
public class KafkaNewTopicConfig {

    @Value("${carinfo.kafka.producer.scrapeCar.topic}")
    private String topic;
    @Value("${carinfo.kafka.consumer.carInfo.topic}")
    private String carInfoTopic;
    @Value("${carinfo.kafka.consumer.jobDone.topic}")
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
                .name(carInfoTopic)
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
