package com.intesim.server.socket;

import com.intesim.server.NettyStart;
import com.intesim.server.socket.handler.ServerContHandler;
import com.intesim.server.socket.handler.ServerDecoder;
import com.intesim.server.socket.handler.ServerEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyServer implements Runnable {

	public NettyServer(Integer port){
		this.port=port;
	}
   
    /**
     * NettyServerListener Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServer.class);
    /**
     * Create bootstrap
     */
    private ServerBootstrap serverBootstrap = new ServerBootstrap();
    /**
     * BOSS
     */
    private EventLoopGroup boss = new NioEventLoopGroup();
    /**
     * Worker
     */
    private EventLoopGroup work = new NioEventLoopGroup();

    private Integer port;

    /**
     * Start Netty
     */
    @Override
    public void run() {
        try {
            serverBootstrap.group(boss, work)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .option(ChannelOption.SO_BACKLOG, 1024 * 1024)
                    .childOption(ChannelOption.TCP_NODELAY,true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .childOption(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                    .childHandler(new ChildChannelInitializer());
            LOGGER.info("Netty started at {}", port);
            ChannelFuture f = serverBootstrap.bind(port).sync();
            NettyStart.IS_ALIVE = true;
            f.channel().closeFuture().sync();
            NettyStart.IS_ALIVE = false;
        } catch (Exception ex){
            LOGGER.error("Netty error.",ex);
        } finally {
            work.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }

    /**
     * Get child listener
     *
     */
    private class ChildChannelInitializer extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new ServerEncoder());
                pipeline.addLast(new ServerDecoder());
                pipeline.addLast(new ServerContHandler());
            }
        }
    }