package com.intesim.client.handler;

import com.alibaba.fastjson.JSON;
import com.intesim.entity.DataMsg;
import com.intesim.entity.SocketConst;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

/**
 * 编码
 *
 */
public class HostEncoder extends MessageToByteEncoder<DataMsg> {
    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(HostEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, DataMsg dataMsg, ByteBuf out) {
        String json = SocketConst.PACKET_START + JSON.toJSONString(dataMsg) + SocketConst.PACKET_END;
        LOGGER.debug("字符请求返回：" +
                json +
                "||host:" +
                channelHandlerContext.channel().remoteAddress().toString()
        );
        out.writeBytes(json.getBytes(StandardCharsets.UTF_8));
    }
}
