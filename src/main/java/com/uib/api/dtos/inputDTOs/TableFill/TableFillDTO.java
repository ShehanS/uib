package com.uib.api.dtos.inputDTOs.TableFill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableFillDTO {
    private int id;
    private String nodeId;
    private ValueDTO value;
}
