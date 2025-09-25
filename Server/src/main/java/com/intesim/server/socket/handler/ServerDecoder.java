package com.intesim.server.socket.handler;

import com.alibaba.fastjson.JSON;
import com.intesim.entity.DataMsg;
import com.intesim.entity.SocketConst;
import com.intesim.entity.SocketEntity;
import com.intesim.server.cache.HostCache;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.context.ContextLoader;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * host通讯包解码
 *
 */
public class ServerDecoder extends ByteToMessageDecoder {

    /**
     * NettyHandler 日志输出器
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerDecoder.class);

    /**
     * 合包临时存储
     */
    private String tempPacket = "";

    /**
     * 多包缓存
     */
    private List<String> tempList = new ArrayList<>();

    /**
     * 包解析方法
     *
     * @param channelHandlerContext 通讯管道
     * @param byteBuf               缓冲
     * @param list                  发送请求
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) {
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        String packet = new String(bytes, StandardCharsets.UTF_8);
        LOGGER.debug("Host Push Packet----->>>>>\r\n {}\r\nhost===========>>{}", packet, channelHandlerContext.channel().remoteAddress().toString());
        this.unitePacket(packet);
        if (!tempList.isEmpty()) {
            for (String message : tempList) {
                this.sendData(message, channelHandlerContext, list);
            }
            tempList.clear();
        }
    }

    /**
     * 发送数据
     *
     * @param packet json包
     * @param ctx    管道
     * @param list   发送集合
     */
    private void sendData(String packet, ChannelHandlerContext ctx, List<Object> list) {
        DataMsg dataMsg = this.getDataMsg(ctx, packet);
        if (dataMsg != null) {
            SocketEntity newEntity = new SocketEntity();
            newEntity.setDataMsg(dataMsg);
            newEntity.setContext(ctx);
            list.add(newEntity);
        }
    }

    /**
     * 获取json
     *
     * @param ctx    通讯管道
     * @param packet 接受包
     * @return 向下一节点发送的数据
     */
    private DataMsg getDataMsg(ChannelHandlerContext ctx, String packet) {
        HostCache bean = new HostCache();
        try {
            DataMsg dataMsg = JSON.parseObject(packet, DataMsg.class);//直接转json对象 如果失败报错
            if (dataMsg == null) {
                this.getFail(ctx, packet, "No Socket Data!");
                return null;
            }
            LOGGER.debug("host dataMsg=========>>\r\n{}\r\nhost========>>{}", packet, ctx.channel().remoteAddress().toString());
            String ip = ctx.channel().remoteAddress().toString().substring(1, ctx.channel().remoteAddress().toString().indexOf(
                    ":"));
            bean.addHostInfoCache(ip, dataMsg.getData());
            return dataMsg;
        } catch (Exception ex) {
            bean.removeHostInfoCache(ctx.channel().remoteAddress().toString().substring(1, ctx.channel().remoteAddress().toString().indexOf(
                    ":")));
            this.getFail(ctx, packet, "Socket Data Error!");
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
     * @param ctx 管道
     */
    private void getFail(ChannelHandlerContext ctx, String json, String message) {
        LOGGER.error("Message======>>{}\r\n|pocket error==========>>\r\n{}host==========>>{}"
                , message, json, ctx.channel().remoteAddress());
    }
}
