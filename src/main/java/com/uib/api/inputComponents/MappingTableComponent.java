package com.uib.api.inputComponents;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.uib.api.dtos.Property;
import com.uib.api.dtos.inputDTOs.MappingTable.MappingRow;
import com.uib.api.dtos.returnDTOs.MappingTable.MapAttribute;
import com.uib.api.dtos.returnDTOs.MappingTable.MappingTableDTO;
import com.uib.api.interfaces.IInputType;

import java.util.ArrayList;
import java.util.List;

public class MappingTableComponent implements IInputType {
    @Override
    public MappingTableDTO extract(Property property) {
        ObjectWriter owNodeTypes = new ObjectMapper().writer().withDefaultPrettyPrinter();
        ObjectMapper objectMapper = new ObjectMapper();
        MappingTableDTO mappingTableDTO = new MappingTableDTO();
        List<MapAttribute> mapAttributes = new ArrayList<>();
        try {
            String jsonStringMappingTable = owNodeTypes.writeValueAsString(property.getDefaultValue());
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            MappingRow[] mappingRows = objectMapper.readValue(jsonStringMappingTable, MappingRow[].class);
            for (MappingRow mappingRow : mappingRows) {
                MapAttribute mapAttribute = new MapAttribute();
                mapAttribute.setOperator(mappingRow.getDefaultNode().getOperator());
                mapAttribute.setFrom(mappingRow.getDefaultNode().getFrom());
                mapAttribute.setTo(mappingRow.getDefaultNode().getTo());
                mapAttribute.setValue(mappingRow.getDefaultNode().getValue());
                mapAttribute.setType(mappingRow.getDefaultNode().getType());
                mapAttributes.add(mapAttribute);
            }
            mappingTableDTO.setMapAttributes(mapAttributes);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return mappingTableDTO;
    }
}
