package com.example.workflow.dto;

import org.camunda.bpm.engine.impl.history.event.HistoryEvent;

public class HistoryEventDto {
    private String className;
    private HistoryEvent historyEvent;

    public HistoryEventDto() {}

    public HistoryEventDto(String className, HistoryEvent historyEvent) {
        this.className = className;
        this.historyEvent = historyEvent;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public HistoryEvent getHistoryEvent() {
        return historyEvent;
    }

    public void setHistoryEvent(HistoryEvent historyEvent) {
        this.historyEvent = historyEvent;
    }
}
