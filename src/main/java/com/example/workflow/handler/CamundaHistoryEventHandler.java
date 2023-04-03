package com.example.workflow.handler;

import com.example.workflow.dto.HistoryEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.impl.history.event.HistoryEvent;
import org.camunda.bpm.engine.impl.history.handler.HistoryEventHandler;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CamundaHistoryEventHandler<T> implements HistoryEventHandler {
    private final KafkaTemplate<String, T> kafkaTemplate;

    @Override
    public void handleEvent(HistoryEvent historyEvent) {
        log.info("Send history data!");
        kafkaTemplate
                .sendDefault(
                        historyEvent.getProcessInstanceId(),
                        (T) new HistoryEventDto(
                                historyEvent.getClass().getCanonicalName(),
                                historyEvent
                        )
                );
    }

    @Override
    public void handleEvents(List<HistoryEvent> list) {
        list.forEach(this::handleEvent);
    }
}
