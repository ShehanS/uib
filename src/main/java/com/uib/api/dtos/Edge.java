package com.uib.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Edge implements Serializable {
    private String source;
    private String sourceHandle;
    private String target;
    private String targetHandle;
    private String id;
}
