package com.intesim.client.handler;

import com.alibaba.fastjson.JSON;
import com.intesim.entity.DataMsg;
import com.intesim.entity.SocketConst;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * host端json解码
 *
 */
public class HostDecoder extends ByteToMessageDecoder {
    /**
     * NettyHandler 日志输出器
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(HostDecoder.class);

    /**
     * 合包临时存储
     */
    private String tempPacket = "";

    /**
     * 多包缓存
     */
    private final List<String> tempList = new ArrayList<>();

    /**
     * 包解析方法
     *
     * @param channelHandlerContext 连接通道
     * @param byteBuf 接受包数据
     * @param list 发送至下一节点
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) {
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        String packet = new String(bytes, StandardCharsets.UTF_8);
        LOGGER.debug("get data is " + packet
                + "||host:" + channelHandlerContext.channel().remoteAddress().toString());
        this.unitePacket(packet);
        if (!tempList.isEmpty()) {
            tempList.forEach(t -> this.sendData(t, channelHandlerContext, list));
            tempList.clear();
        }
    }

    /**
     * 发送数据
     *
     * @param packet 接受包
     * @param channelHandlerContext 通讯管道
     * @param list 发送下一节点
     */
    private void sendData(String packet, ChannelHandlerContext channelHandlerContext, List<Object> list) {
        DataMsg dataMsg = this.getDataMsg(channelHandlerContext, packet);
        if (dataMsg != null) {
            list.add(dataMsg);
        }
    }

    /**
     * 获取json
     *
     * @param ctx 通讯管道
     * @param packet 接受包
     * @return 向下一节点发送的数据
     */
    private DataMsg getDataMsg(ChannelHandlerContext ctx, String packet) {
        try {
            DataMsg dataMsg = JSON.parseObject(packet, DataMsg.class);//直接转json对象 如果失败报错
            if (dataMsg == null) {
                this.getFaile(ctx, packet, "无数据！");
                return null;
            }
            LOGGER.debug("host dataMsg is :" +
                    packet +
                    "||host:" +
                    ctx.channel().remoteAddress().toString()
            );
            return dataMsg;
        } catch (Exception ex) {
            this.getFaile(ctx, packet, "数据错误！");
        }
        return null;
    }

    /**
     * 合包拆包
     *
     * @param packet 接受包
     */
    private void unitePacket(String packet) {
        packet = tempPacket + packet;
        int startIndex = packet.indexOf(SocketConst.PACKET_START);
        int endIndex = packet.indexOf(SocketConst.PACKET_END);
        while (endIndex != -1) {
            tempList.add(packet.substring(startIndex + 1, endIndex));
            packet = packet.substring(endIndex + 1);
            startIndex = packet.indexOf(SocketConst.PACKET_START);
            endIndex = packet.indexOf(SocketConst.PACKET_END);
        }
        tempPacket = packet;
    }

    /**
     * 编译信息
     *
     * @param ctx 通讯管道
     */
    private void getFaile(ChannelHandlerContext ctx, String json, String message) {
        LOGGER.error("Message:" + message +
                "\n|----------------------------------------------|pocket errot:data||" + json +
                "\n|----------------------------------------------|host||" + ctx.channel().remoteAddress());
    }
}
