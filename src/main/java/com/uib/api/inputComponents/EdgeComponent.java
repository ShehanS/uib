package com.uib.api.inputComponents;


import com.uib.api.dtos.Edge;
import com.uib.api.dtos.returnDTOs.Edge.EdgeDTO;

public class EdgeComponent {
    public EdgeDTO extractEdge(Edge edge) {
        EdgeDTO edgeDTO = new EdgeDTO();
        edgeDTO.setSourceTerminal(edge.getSourceHandle());
        edgeDTO.setTargetTerminal(edge.getTargetHandle());
        edgeDTO.setSourceNode(edge.getSource());
        edgeDTO.setTargetNode(edge.getTarget());
        return edgeDTO;
    }
}
