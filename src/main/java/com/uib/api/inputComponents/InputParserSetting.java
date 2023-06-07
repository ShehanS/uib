package com.uib.api.inputComponents;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.cfg.CoercionAction;
import com.fasterxml.jackson.databind.cfg.CoercionInputShape;
import com.uib.api.dtos.Property;
import com.uib.api.dtos.returnDTOs.InputParserSetting.InputParserSettingDTO;
import com.uib.api.interfaces.IInputType;

import java.io.IOException;

public class InputParserSetting implements IInputType {
    @Override
    public InputParserSettingDTO extract(Property property) {
        InputParserSettingDTO inputParserSettingDTO = null;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            ObjectWriter owProperty = new ObjectMapper().writer().withDefaultPrettyPrinter();
            if(property.getDefaultValue() != null) {
                String jsonStringDefaultValue = owProperty.writeValueAsString(property.getDefaultValue());
                inputParserSettingDTO = objectMapper.readValue(jsonStringDefaultValue, InputParserSettingDTO.class);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return inputParserSettingDTO;
    }
}
