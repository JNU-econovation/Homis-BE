package com.Homis.ddeugae.common.util.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.List;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {
    private static final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);

    // DB에 저장할 때
    @Override
    public String convertToDatabaseColumn(List<String> attribute){
        try{
            return mapper.writeValueAsString(attribute);
        } catch (JsonProcessingException jpe){
            // custom
        }
    }

    // DB에서 조회할 때
    @Override
    public List<String> convertToEntityAttribute(String dbData){
        TypeReference<List<String>> typeReference = new TypeReference<List<String>>() {};
        try {
            return mapper.readValue(dbData, typeReference);
        } catch (JsonProcessingException jpe){
            // custom
        }
    }
}
