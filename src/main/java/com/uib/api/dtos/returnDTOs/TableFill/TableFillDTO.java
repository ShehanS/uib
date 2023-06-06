package com.uib.api.dtos.returnDTOs.TableFill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableFillDTO {
    private List<TableAttribute> attributeList;
}
