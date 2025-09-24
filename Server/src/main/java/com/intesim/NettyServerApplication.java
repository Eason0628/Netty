package com.intesim;

import com.intesim.server.NettyStart;
import com.intesim.util.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NettyServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyServerApplication.class, args);
        NettyStart start = (NettyStart) SpringUtil.getBean("nettyStart");
        start.start();
    }
}
