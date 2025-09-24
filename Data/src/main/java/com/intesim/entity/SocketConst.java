package com.intesim.entity;

/**
 * socket连接常量
 *
 */
public class SocketConst {

    //通讯设置
    public static final int RE_TIMES_OUT = 5;//解析包错误重试次数
    public static final int SO_BACKLOG = 1024 * 1024;//最大等待队列
    //循环等待10秒
    public static final long WHILE_WAIT = 10000L;
    //包头
    public static final char PACKET_START = 2;
    //包尾
    public static final char PACKET_END = 3;
}
