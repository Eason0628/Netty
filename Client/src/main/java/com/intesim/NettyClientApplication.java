package com.intesim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NettyClientApplication {
    public NettyClientApplication() {
    }

    public static void main(String[] args) {
        SpringApplication.run(NettyClientApplication.class, args);
    }
}
