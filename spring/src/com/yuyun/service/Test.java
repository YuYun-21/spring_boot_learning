package com.yuyun.service;

import com.yuyun.spring.YuyunApplicationContext;

/**
 * @author hyh
 * @since 2024-12-03
 */
public class Test {

    public static void main(String[] args) {
        YuyunApplicationContext yuyunApplicationContext = new YuyunApplicationContext(AppConfig.class);
        UserService userService = (UserService) yuyunApplicationContext.getBean("userService");
        userService.test();
    }
}
