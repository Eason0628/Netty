package com.intesim.entity.runtime;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 进程数据实体
 *
 */
@Data
public class ProcessDataEntity {

    /**
     * pid
     */
    private Long pid;

    /**
     * 父级pid
     */
    private Long ppid;

    /**
     * 进程名称
     */
    private String processName;

    /**
     * 进程版本
     */
    private String processVersion;

    /**
     * cpu使用率
     */
    private Double cpuHold;

    /**
     * 内存使用率
     */
    private Integer memoryHold;

    /**
     * 进程状态 0运行 1阻塞 2停止
     */
    private Integer status = 0;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 进程树
     */
    private List<ProcessDataEntity> tree;

    public ProcessDataEntity(){

    }

    public ProcessDataEntity(long pid, Date startTime){
        this();
        this.pid = pid;
        this.startTime = startTime;
    }
}
