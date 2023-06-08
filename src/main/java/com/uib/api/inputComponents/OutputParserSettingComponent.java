package com.uib.api.inputComponents;

import com.uib.api.dtos.Property;
import com.uib.api.dtos.returnDTOs.OutputParserSetting.OutputParserSettingsDTO;
import com.uib.api.interfaces.IInputType;

public class OutputParserSettingComponent implements IInputType {
    @Override
    public OutputParserSettingsDTO extract(Property property) {
        OutputParserSettingsDTO outputParserSettingsDTO = new OutputParserSettingsDTO();
        if (property.getDefaultValue() != null) {
            outputParserSettingsDTO.setParserName(property.getDefaultValue().toString());
        } else {
            outputParserSettingsDTO.setParserName("");
        }
        return outputParserSettingsDTO;
    }
}
