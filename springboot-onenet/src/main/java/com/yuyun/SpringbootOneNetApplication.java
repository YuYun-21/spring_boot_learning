package com.yuyun;

import com.yuyun.mq.MqClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hyh
 */
@MapperScan("com.yuyun.mapper")
@SpringBootApplication
public class SpringbootOneNetApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootOneNetApplication.class, args);

        MqClient mqClient = new MqClient();
        mqClient.connect();
    }

}
