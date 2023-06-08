package com.uib.api.inputComponents;

import com.uib.api.dtos.Property;
import com.uib.api.dtos.returnDTOs.CheckBox.CheckBoxDTO;
import com.uib.api.interfaces.IInputType;

public class CheckBoxComponent implements IInputType {
    @Override
    public CheckBoxDTO extract(Property property) {
        CheckBoxDTO checkBoxDTO = new CheckBoxDTO();
        if (property.getIdentity() != null) {
            checkBoxDTO.setName(property.getIdentity());
        }
        if (property.getDefaultValue() != null) {
            checkBoxDTO.setValue(property.getDefaultValue().toString());
        }
        return checkBoxDTO;
    }
}
