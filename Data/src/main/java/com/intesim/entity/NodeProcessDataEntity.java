package com.intesim.entity;

import com.intesim.entity.runtime.ProcessDataEntity;

/**
 * 节点进程实体
 *
 */
public class NodeProcessDataEntity extends ProcessDataEntity {

    private String nodeId;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }
}
