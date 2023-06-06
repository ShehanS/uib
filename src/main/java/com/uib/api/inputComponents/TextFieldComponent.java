package com.uib.api.inputComponents;

import com.uib.api.dtos.Property;
import com.uib.api.dtos.returnDTOs.TextFiled.TextFieldDTO;
import factory.IInputType;

public class TextFieldComponent implements IInputType {
    @Override
    public TextFieldDTO extract(Property property) {
        TextFieldDTO textFieldDTO = new TextFieldDTO();
        textFieldDTO.setName(property.getIdentity());
        textFieldDTO.setValue(property.getDefaultValue().toString());
        return textFieldDTO;
    }
}
