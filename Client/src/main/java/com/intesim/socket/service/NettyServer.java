package com.intesim.socket.service;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 开启netty服务
 *
 */
public class NettyServer implements Runnable {

    public NettyServer(Integer port, String host) {
        this.port = port;
        this.host = host;
    }

    /**
     * NettyServerListener Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServer.class);
    /**
     * Create bootstrap
     */
    private Bootstrap bootstrap = new Bootstrap();
    /**
     * Connection channel pool
     */
    private EventLoopGroup group = new NioEventLoopGroup();

    private Integer port;

    private String host;

    /**
     * Start Netty
     */
    @Override
    public void run() {
        try {
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)//Netty4使用对象池，重用缓冲区
                    .option(ChannelOption.SO_KEEPALIVE, true)//保持长连接
                    .handler(new ChildChannelInitializer());
            while (true) {
                try {
                    this.connect();
                    Thread.sleep(30000L);// HEAR_BEAT_INTERVAL
                    LOGGER.info("Host client closed");
                }catch (Exception e){
                    LOGGER.error("Netty error",e);
                    Thread.sleep(30000L);// HEAR_BEAT_INTERVAL
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Netty error.", ex);
        }
    }

    private void connect() throws InterruptedException {
        // 异步开启客户端连接
        ChannelFuture f = bootstrap.connect(this.host, this.port).sync();
        LOGGER.info("Netty connect " + this.host + ":" + this.port);
        // 异步等待关闭连接channel
        f.channel().closeFuture().sync();
    }

    /**
     * 获取child监听
     * Add child listener
     */
    private class ChildChannelInitializer extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
//            pipeline.addLast(new HostEncoder());
//            pipeline.addLast(new HostDecoder());
//            pipeline.addLast(new HostContHandler());
        }
    }
}
