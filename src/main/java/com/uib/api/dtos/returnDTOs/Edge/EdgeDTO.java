package com.uib.api.dtos.returnDTOs.Edge;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EdgeDTO {
    private String sourceNode;
    private String sourceTerminal;
    private String targetNode;
    private String targetTerminal;
}
