package com.uib.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Property implements Serializable {
    private String id;
    private String nodeId;
    private String name;
    private String type;
    private String identity;
    private String displayName;
    private Object value;
    private Object defaultValue;
    private boolean validator;
    private boolean subValue;
    private boolean nameAsValue;
    private Object secondaryValue;
    private boolean setToHeader;

}
