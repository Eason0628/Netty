package com.intesim.cont;

/**
 * 运行节点状态
 *
 */
public class NodeStatusConst {

    /**
     * 前序未完成
     */
    public static final int PRE_NOT_COMPLETED = -1;
    /**
     * 等待下发
     */
    public static final int WAIT = 0;
    /**
     * 已下发
     */
    public static final int START = 1;
    /**
     * 运行中
     */
    public static final int RUNING = 2;
    /**
     * 运行结束
     */
    public static final int FINISH = 3;
    /**
     * 结果出错
     */
    public static final int ERROR = 4;
    /**
     * 运行出错
     */
    public static final int RUN_ERROR = 5;
    /**
     * 节点取消
     */
    public static final int CANCEL = 6;
    /**
     * 已完成跳过
     */
    public static final int PASS = 7;
    /**
     * 暂停
     */
    public static final int SUSPEND = 8;

    /**
     *未执行跳过
     */
    public static final int NONEXECUTION = 9;
    /**
     * 最大优先级
     */
    public static final int MAX_LEVEL = 0;
    /**
     * 最小优先级
     */
    public static final int MIN_LEVEL = 100;
}
