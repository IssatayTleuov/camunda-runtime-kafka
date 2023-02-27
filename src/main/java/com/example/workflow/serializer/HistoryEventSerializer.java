package com.example.workflow.serializer;

import com.example.workflow.dto.HistoryEventDto;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class HistoryEventSerializer implements Serializer<HistoryEventDto> {

    private final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private final static Logger log = LoggerFactory.getLogger(HistoryEventSerializer.class);

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
