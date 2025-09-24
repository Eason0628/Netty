package com.intesim.client.handler;

import com.alibaba.fastjson.JSON;
import com.intesim.entity.DataMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
        LOGGER.info("Client connected:{}", ctx.channel().remoteAddress());
        DataMsg dataMsg = new DataMsg();
        dataMsg.setData("Client data from channelActive");
        ctx.writeAndFlush(dataMsg);
    }

    /**
     * 处理请求的业务逻辑
     *
     * @param ctx 连接通道
     * @param msg 请求信息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        DataMsg reciveMsg = (DataMsg) msg;
        String serverMsg = JSON.toJSONString((reciveMsg.getData()));
        System.out.println(serverMsg);

        DataMsg sendMsg = new DataMsg();
        sendMsg.setData("Client connected and sending data from channelRead");
        ctx.writeAndFlush(sendMsg);
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



