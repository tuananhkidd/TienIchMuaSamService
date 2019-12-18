package com.kidd.shopping.base.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListConverter implements AttributeConverter<List<String>, String> {
    public static final Logger logger = LoggerFactory.getLogger(JsonConverter.class);

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
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
    public List<String> convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return new ArrayList<>();
        }
        try {
            return JacksonObjectMapper.getInstance().readValue(dbData, new TypeReference<List<String>>(){});
        } catch (IOException e) {
            logger.error("Falied to convert json " + dbData + " to list", e);
            return new ArrayList<>();
        }
    }
}
