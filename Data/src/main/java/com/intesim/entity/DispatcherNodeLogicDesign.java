package com.intesim.entity;

import lombok.Data;

@Data
public class DispatcherNodeLogicDesign {
    private String nodeWorkflowId;
    private Integer type;
    private String regular;
    private String regularType;
    private String realExpression;
    private String fileName;
    private String nextNodeWorkflowId;

}