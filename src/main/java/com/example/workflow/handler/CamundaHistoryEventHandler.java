package com.example.workflow.handler;

import com.example.workflow.dto.HistoryEventDto;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.impl.history.event.HistoryEvent;
import org.camunda.bpm.engine.impl.history.handler.HistoryEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class CamundaHistoryEventHandler implements HistoryEventHandler {
    private final KafkaTemplate<String, HistoryEventDto> kafkaTemplate;

    @Autowired
    public CamundaHistoryEventHandler(KafkaTemplate<String, HistoryEventDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void handleEvent(HistoryEvent historyEvent) {
        HistoryEventDto historyEventDto = new HistoryEventDto(
                historyEvent.getClass().getName(),
                historyEvent
        );

        kafkaTemplate
                .send(
                        "camunda",
                        historyEventDto
                );
    }

    @Override
    public void handleEvents(List<HistoryEvent> list) {
        list.forEach(this::handleEvent);
    }
}
