package com.example.workflow.serializer;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.kafka.common.serialization.Serializer;

import java.io.Serializable;
import java.util.Map;

public class CustomSerializer<T extends Serializable> implements Serializer<T> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String s, T t) {
        return SerializationUtils.serialize(t);
    }

    @Override
    public void close() {
        Serializer.super.close();
    }
}
