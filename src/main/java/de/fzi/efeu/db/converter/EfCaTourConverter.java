package de.fzi.efeu.db.converter;

import java.io.IOException;

import javax.persistence.AttributeConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.fzi.efeu.efeuportal.model.EfCaTour;

public class EfCaTourConverter implements AttributeConverter<EfCaTour, String> {

    private ObjectMapper objectMapper;

    public EfCaTourConverter() {
        objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
                .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    }

    @Override
    public String convertToDatabaseColumn(final EfCaTour efCaTour) {
        String efCaTourJson = null;
        try {
            efCaTourJson = objectMapper.writeValueAsString(efCaTour);
        } catch (final JsonProcessingException e) {
            e.printStackTrace();
        }

        return efCaTourJson;
    }

    @Override
    public EfCaTour convertToEntityAttribute(final String efCaTourJson) {
        EfCaTour efCaTour = null;
        try {
            efCaTour = objectMapper.readValue(efCaTourJson, EfCaTour.class);
        } catch (final IOException e) {
            e.printStackTrace();
        }

        return efCaTour;
    }
}
