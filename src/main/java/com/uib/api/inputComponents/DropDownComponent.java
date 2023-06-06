package com.uib.api.inputComponents;

import com.uib.api.dtos.Property;
import com.uib.api.dtos.returnDTOs.DropDown.DropDownDTO;
import factory.IInputType;

public class DropDownComponent implements IInputType {
    @Override
    public DropDownDTO extract(Property property) {
        DropDownDTO dropDownDTO = new DropDownDTO();
        if (property.getIdentity() != null) {
            dropDownDTO.setName(property.getIdentity());
        }
        if (property.getDefaultValue() != null) {
            dropDownDTO.setValue(property.getDefaultValue().toString());
        }
        return dropDownDTO;
    }
}
