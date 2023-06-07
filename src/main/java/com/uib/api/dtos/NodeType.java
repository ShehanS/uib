package com.uib.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NodeType {
    private String color;
    private String id;
    private String position;
    private String terminal;
    private String toolTip;
    private String target;
    private int x;
    private int y;

}
