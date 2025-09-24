package com.intesim.entity;

import io.netty.channel.ChannelHandlerContext;

/**
 * 通讯用实体
 *
 */
public class SocketEntity {

    /**
     * 业务处理对象
     */
    private DataMsg dataMsg;

    /**
     * 连接管道
     */
    private ChannelHandlerContext context;

    public ChannelHandlerContext getContext() {
        return context;
    }

    public void setContext(ChannelHandlerContext context) {
        this.context = context;
    }

    public DataMsg getDataMsg() {
        return dataMsg;
    }

    public void setDataMsg(DataMsg dataMsg) {
        this.dataMsg = dataMsg;
    }
}
