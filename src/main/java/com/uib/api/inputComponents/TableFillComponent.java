package com.uib.api.inputComponents;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.uib.api.dtos.Property;
import com.uib.api.dtos.inputDTOs.TableFill.TableFillDTO;
import com.uib.api.dtos.inputDTOs.TableFill.ValueDTO;
import com.uib.api.dtos.returnDTOs.TableFill.TableAttribute;
import com.uib.api.dtos.returnDTOs.TableFill.TableData;
import com.uib.api.interfaces.IInputType;

import java.util.ArrayList;
import java.util.List;

public class TableFillComponent implements IInputType {
    @Override
    public com.uib.api.dtos.returnDTOs.TableFill.TableFillDTO extract(Property property) {
        TableData tableData = new TableData();
        com.uib.api.dtos.returnDTOs.TableFill.TableFillDTO tableFillDTO = new com.uib.api.dtos.returnDTOs.TableFill.TableFillDTO();
        try {
            List<TableAttribute> tableAttributeList = new ArrayList<>();
            ObjectWriter owNodeTypes = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String jsonStringNodeTypes = owNodeTypes.writeValueAsString(property.getDefaultValue());
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            TableFillDTO[] tableFillDTOS = objectMapper.readValue(jsonStringNodeTypes, TableFillDTO[].class);
            for (TableFillDTO dto : tableFillDTOS) {
                ValueDTO valueObject = dto.getValue();
                TableAttribute tableAttribute = new TableAttribute();
                tableAttribute.setName(valueObject.getName());
                tableAttribute.setValue(valueObject.getValue());
                tableAttributeList.add(tableAttribute);
            }
            tableData.setAttributeList(tableAttributeList);
            tableFillDTO.setTableData(tableData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tableFillDTO;
    }
}
