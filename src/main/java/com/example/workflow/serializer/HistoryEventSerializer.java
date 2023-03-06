package com.example.workflow.serializer;

import com.example.workflow.dto.HistoryEventDto;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

@Slf4j
public class HistoryEventSerializer implements Serializer<HistoryEventDto> {

    private final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String s, HistoryEventDto historyEventDto) {
        try {
            if (historyEventDto == null){
                log.info("Null received at serializing");
                return null;
            }

            log.info("Serializing...");
            return objectMapper.writeValueAsBytes(historyEventDto);
        } catch (Exception e) {
            throw new SerializationException("Error when serializing HistoryEvent to byte[]");
        }
    }

    @Override
    public void close() {
        Serializer.super.close();
    }
}
