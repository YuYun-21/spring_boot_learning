package com.yuyun;

import com.yuyun.im.IMServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NettyImApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(NettyImApplication.class, args);
        IMServer.start();
    }

}
