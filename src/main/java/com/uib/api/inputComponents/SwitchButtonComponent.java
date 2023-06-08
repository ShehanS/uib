package com.uib.api.inputComponents;

import com.uib.api.dtos.Property;
import com.uib.api.dtos.returnDTOs.SwitchButton.SwitchButtonDTO;
import com.uib.api.interfaces.IInputType;

public class SwitchButtonComponent implements IInputType {
    @Override
    public SwitchButtonDTO extract(Property property) {
        SwitchButtonDTO switchButtonDTO = new SwitchButtonDTO();
        switchButtonDTO.setName(property.getIdentity());
        if (property.getDefaultValue() != null) {
            switchButtonDTO.setValue(property.getDefaultValue().toString());
        }

        return switchButtonDTO;
    }
}
