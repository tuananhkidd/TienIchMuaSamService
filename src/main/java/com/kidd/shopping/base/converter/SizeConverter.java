package com.kidd.shopping.base.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.kidd.shopping.product.entity.ColorSize;
import com.kidd.shopping.product.entity.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SizeConverter implements AttributeConverter<List<ColorSize>, String> {
    public static final Logger logger = LoggerFactory.getLogger(JsonConverter.class);

    @Override
    public String convertToDatabaseColumn(List<ColorSize> attribute) {
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
    public List<ColorSize> convertToEntityAttribute(String dbData) {
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
