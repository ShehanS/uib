package com.uib.api.dtos.returnDTOs.MappingTable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MapAttribute {
    private String operator;
    private String from;
    private String to;
    private String type;
    private String value;
}
