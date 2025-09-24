package com.intesim.server;

import com.intesim.server.socket.NettyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;



@Component("nettyStart")
public class NettyStart {
    private static final Logger log = LoggerFactory.getLogger(NettyStart.class);

    @Value("${netty.port}")
    private Integer port;

    public static boolean IS_ALIVE;

    public void start() {
        log.info("Port------------------->>>>>>>>>>>>>>>>>>>>>   {}", port);
        Thread thread = new Thread(new NettyServer(port));
        thread.start();
        log.info(">>>>>>>>>>>>>>>>>>>>>-------------------Initialization netty");
    }
}
