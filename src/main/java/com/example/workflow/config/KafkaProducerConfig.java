package com.example.workflow.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig<T> {
    @Value("${spring.kafka.template.default-topic}")
    private String topic;
    private final KafkaProperties kafkaProperties;

    @Bean
    public ProducerFactory<String, T> producerFactory() {
        return new DefaultKafkaProducerFactory<>(kafkaProperties.buildProducerProperties());
    }

    @Bean
    public KafkaTemplate<String, T> kafkaTemplate(ProducerFactory<String, T> producerFactory) {
        KafkaTemplate<String, T> template = new KafkaTemplate<>(producerFactory);
         template.setDefaultTopic(topic);
        return template;
    }
}
