package com.uib.api.inputComponents;

import com.uib.api.dtos.Property;
import com.uib.api.dtos.PropertyExtractDTO;
import com.uib.api.interfaces.IInputType;

import java.util.ArrayList;
import java.util.List;

public class DefaultComponent implements IInputType {
    @Override
    public List<PropertyExtractDTO> extract(Property property) {
        List<PropertyExtractDTO> propertyExtractDTOS = new ArrayList<>();
        return propertyExtractDTOS;
    }
}
