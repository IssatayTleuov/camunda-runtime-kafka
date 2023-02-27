package com.example.workflow;

import com.example.workflow.dto.HistoryEventDto;
import com.example.workflow.serializer.HistoryEventSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.camunda.bpm.engine.impl.history.event.HistoricActivityInstanceEventEntity;
import org.camunda.bpm.engine.impl.history.event.HistoricVariableUpdateEventEntity;
import org.camunda.bpm.engine.impl.history.event.HistoryEvent;
import org.camunda.bpm.engine.impl.history.handler.HistoryEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;

@Component
public class CamundaHistoryEventHandler implements HistoryEventHandler {
    @Autowired
    private KafkaTemplate<String, HistoryEventDto> kafkaTemplate;
    private static final Logger log = LoggerFactory.getLogger(CamundaHistoryEventHandler.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handleEvent(HistoryEvent historyEvent) {
        log.info("Start - history handle event");
        HistoryEventDto historyEventDto = new HistoryEventDto(
                historyEvent.getClass().getName(),
                historyEvent
        );
        try {
            log.info("Json - " + objectMapper.writeValueAsString(historyEventDto));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        kafkaTemplate
                .send(
                        "camunda",
                        historyEventDto
                );
        log.info("End - history handle event");
    }

    @Override
    public void handleEvents(List<HistoryEvent> list) {
        list.forEach(this::handleEvent);
    }
}
