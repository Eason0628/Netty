package com.intesim.server.socket.handler;

import com.alibaba.fastjson.JSON;
import com.intesim.entity.SocketEntity;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * 服务器端信息响应
 *
 */
@Slf4j
public class ServerContHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("Host Online:{}", ctx.channel().remoteAddress().toString());
    }

    /**
     * 处理请求的业务逻辑
     *
     * @param ctx 管道
     * @param msg 信息实体
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        SocketEntity dataMsg = (SocketEntity) msg;
        String data = JSON.toJSONString((dataMsg.getDataMsg().getData()));
        System.out.println(data);
    }

    /**
     * @param ctx 管道
     * @throws Exception 异常信息
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    /**
     * 异常捕获处理
     *
     * @param ctx 管道
     * @param cause 错误信息
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}