package com.charlie.toggleservice.config;

import com.charlie.toggleservice.model.FeatureToggle;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class FeatureToggleSerializer extends JsonSerializer<FeatureToggle> {

    @Override
    public void serialize(FeatureToggle featureToggle, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name", featureToggle.getName());
        jsonGenerator.writeBooleanField("isActive", featureToggle.isActive());
        jsonGenerator.writeEndObject();
    }
}
