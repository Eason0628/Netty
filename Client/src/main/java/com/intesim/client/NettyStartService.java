package com.intesim.client;


import com.intesim.client.service.NettyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Client Start Netty
 *
 */
@Component
public class NettyStartService implements InitializingBean {

    /**
     * NettyServerListener Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyStartService.class);

    public static String SERVER_HOST;

    public static Integer SERVER_PORT = 2025;

    @Autowired
    public NettyStartService(Environment env) {
        SERVER_HOST = env.getProperty("netty.server.host");
    }

    @Override
    public void afterPropertiesSet() {
        try {
            new Thread(new NettyServer(SERVER_PORT, SERVER_HOST)).start();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
    }
}
