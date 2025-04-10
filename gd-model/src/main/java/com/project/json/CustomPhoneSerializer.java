package com.project.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class CustomPhoneSerializer extends JsonSerializer<String> {
    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String newPhone = s.replaceAll("(\\d{3})\\d{6}(\\d{2})", "$1******$2");
        jsonGenerator.writeString(newPhone);
    }
}
