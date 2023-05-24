package com.uib.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Diagram implements Serializable {
    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;
    private Viewport viewport;
}
