package com.intesim.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * HostEntity
 *
 */
@Data
public class HostEntity extends RegisterEntity {

    /**
     * 在运行任务
     */
    private Map<String, NodeProcessDataEntity> nodeProcess;

    /**
     * 空闲内存
     */
    private String freeMem;

    /**
     * cpu使用率
     */
    private String cpuUsageRate;
    /**
     * 当前host已下发的任务
     */
    private List<HostDispatcherNode> sendNodes;
    /**
     * 当前Host Ip及端口号
     */
    private String ip;
    /**
     * 是否被占用
     */
    private Boolean beOccupied = false;

    private Lock occupiedLock = new ReentrantLock();


    public Boolean getBeOccupied() {
        try {
            occupiedLock.lock();
            return beOccupied;
        } finally {
            occupiedLock.unlock();
        }
    }

    public void setBeOccupied(Boolean beOccupied) {
        try {
            occupiedLock.lock();
            this.beOccupied = beOccupied;
        } finally {
            occupiedLock.unlock();
        }
    }

}
