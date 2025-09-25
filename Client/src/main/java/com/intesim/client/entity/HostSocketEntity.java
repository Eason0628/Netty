package com.intesim.client.entity;

import com.intesim.entity.DataMsg;
import io.netty.channel.ChannelHandlerContext;

/**
 * socket实体
 *
 */
public class HostSocketEntity {

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
