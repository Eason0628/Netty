package com.intesim.socket;


import com.intesim.socket.service.NettyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * netty启动类
 *
 * @author wangxu [83922113@qq.com]
 * @version 0.1 9:44 2018-06-05
 */
@Component
public class NettyStartService implements InitializingBean {

    /**
     * NettyServerListener Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyStartService.class);

    public static String SERVER_HOST;//服务器地址

    public static Integer SERVER_PORT = 2025;//服务器端口

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
