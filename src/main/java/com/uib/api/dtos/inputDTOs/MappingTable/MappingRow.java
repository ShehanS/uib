package com.uib.api.dtos.inputDTOs.MappingTable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MappingRow {
    private int id;
    private String nodeId;
    private DefaultNode defaultNode;
}
