package com.uib.api.dtos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class Node implements Serializable {
    private int width;
    private int height;
    private String id;
    private String _id;
    private String type;
    private String name;
    private Position position;
    private List<Property> properties;
    private Data data;
    private boolean selected;
    private PositionAbsolute positionAbsolute;
    private boolean dragging;
}
