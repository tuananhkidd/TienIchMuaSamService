package com.kidd.shopping.base.converter;


import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonObjectMapper {
    public static ObjectMapper instance;

    static {
        try {
            instance = new ObjectMapper();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Jackson object mapper initial failed");
        }
    }

    public static ObjectMapper getInstance() {
        return instance;
    }
}
