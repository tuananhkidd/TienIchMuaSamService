package com.kidd.shopping.base.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsonConverter implements AttributeConverter<Map<String, String>, String> {
    public static final Logger logger = LoggerFactory.getLogger(JsonConverter.class);

    @Override
    public String convertToDatabaseColumn(Map<String, String> attribute) {
        if (attribute == null) {
            return "{}";
        }
        try {
            return JacksonObjectMapper.getInstance().writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            logger.error("Falied to convert attribute " + attribute + " to json", e);
            return "{}";
        }
    }

    @Override
    public Map<String, String> convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return new HashMap<>();
        }
        try {
            return JacksonObjectMapper.getInstance().readValue(dbData, new TypeReference<Map<String, String>>(){});
        } catch (IOException e) {
            logger.error("Falied to convert json " + dbData + " to map", e);
            return new HashMap<>();
        }
    }
}
