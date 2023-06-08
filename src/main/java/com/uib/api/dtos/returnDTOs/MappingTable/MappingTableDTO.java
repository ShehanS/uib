package com.uib.api.dtos.returnDTOs.MappingTable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MappingTableDTO {
    private List<MapAttribute> mapAttributes;
}
