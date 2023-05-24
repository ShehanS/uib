package com.uib.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Flow implements Serializable {
    private String savePath;
    private String fileName;
    private String displayName;
    private Object values;
    private String image;
    private Diagram diagram;
}
