package com.uib.api.dtos.inputDTOs.MappingTable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MappingDTO {
    List<DefaultNode> defaultNodes;
}
