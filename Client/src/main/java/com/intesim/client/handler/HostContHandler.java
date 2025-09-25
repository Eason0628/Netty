package com.intesim.client.handler;

import com.alibaba.fastjson.JSON;
import com.intesim.entity.DataMsg;
import com.intesim.entity.SocketEntity;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


public class HostContHandler extends ChannelInboundHandlerAdapter {

    /**
     * 日志输出器
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(HostContHandler.class);


    /**
     * 连接事件
     *
     * @param ctx 连接通道
     * @throws Exception 抛出异常
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketEntity socketEntity = new SocketEntity();
        DataMsg dataMsg = new DataMsg();
        String msgs = "Hello Server,This msg from Client!";
        dataMsg.setData(msgs);
        socketEntity.setDataMsg(dataMsg);
        ctx.writeAndFlush(socketEntity);
    }

    /**
     * 处理请求的业务逻辑
     *
     * @param ctx 连接通道
     * @param msg 请求信息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
    }

    /**
     * 服务器断线处理
     *
     * @param ctx 连接通道
     * @throws Exception 抛出异常
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    /**
     * 异常捕获处理
     *
     * @param ctx   连接通道
     * @param cause 异常信息
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    }
}



