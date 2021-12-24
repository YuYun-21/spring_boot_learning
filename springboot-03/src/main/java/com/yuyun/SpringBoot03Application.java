package com.yuyun;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hyh
 */
@MapperScan("com.yuyun.mapper")
@SpringBootApplication
public class SpringBoot03Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot03Application.class, args);
    }

}
