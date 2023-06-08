package com.uib.api.dtos.inputDTOs.MappingTable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DefaultNode {
    private String operator;
    private String from;
    private String to;
    private String value;
    private String type;
}
