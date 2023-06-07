package com.uib.api.dtos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class Data {
    private String displayName;
    private String label;
    private String icon;
    private String description;
    private String id;
    private ArrayList<Object> properties;
    private List<NodeType> nodeTypes;
    private String nodeType;
    private String uuid;
    private String implementer;
    private String color;
}
