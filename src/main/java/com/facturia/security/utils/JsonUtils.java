// src/main/java/com/facturia/security/utils/JsonUtils.java
package com.facturia.security.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private JsonUtils() {
        
    }

    public static String convertToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("Error al convertir el objeto a JSON", e);
        }
    }
}