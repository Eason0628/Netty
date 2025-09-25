package com.intesim.server.socket.handler;


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
 * Netty 编码器
 */
public class ServerEncoder extends MessageToByteEncoder<DataMsg> {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, DataMsg dataMsg, ByteBuf out) {
    	String result = SocketConst.PACKET_START + JSON.toJSONString(dataMsg) + SocketConst.PACKET_END;
        LOGGER.debug("Server Push Packet---------->>>>>>\r\n  {}\r\nTo host======>>{}",
                result, channelHandlerContext.channel().remoteAddress().toString());
        out.writeBytes(result.getBytes(StandardCharsets.UTF_8));
    }
}
