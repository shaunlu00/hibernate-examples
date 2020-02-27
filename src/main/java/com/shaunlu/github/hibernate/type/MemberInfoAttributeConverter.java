package com.shaunlu.github.hibernate.type;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shaunlu.github.hibernate.model.MemberInfo;

import javax.persistence.AttributeConverter;
import java.io.IOException;

public class MemberInfoAttributeConverter implements AttributeConverter<MemberInfo, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(MemberInfo attribute) {
        String str = null;
        try {
            str = objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return str;
    }

    @Override
    public MemberInfo convertToEntityAttribute(String dbData) {
        MemberInfo memberInfo = null;
        try {
            memberInfo = objectMapper.readValue(dbData, MemberInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return memberInfo;
    }
}
