package com.uib.api.inputComponents;

import com.uib.api.dtos.Property;
import com.uib.api.dtos.returnDTOs.GroovyEditor.GroovyEditorDTO;
import com.uib.api.interfaces.IInputType;

public class GroovyEditorComponent implements IInputType {


    @Override
    public GroovyEditorDTO extract(Property property) {
        GroovyEditorDTO groovyEditorDTO = new GroovyEditorDTO();
        if (property.getDefaultValue() != null) {
            groovyEditorDTO.setScriptBlock(property.getDefaultValue().toString());
        }
        return groovyEditorDTO;
    }
}
