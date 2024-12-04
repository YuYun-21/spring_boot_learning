package com.yuyun.service;

import com.yuyun.spring.Autowired;
import com.yuyun.spring.Component;
import com.yuyun.spring.Scope;

/**
 * @author hyh
 * @since 2024-12-03
 */
@Component
@Scope("prototype")
public class UserService {

    @Autowired
    private OrderService orderService;

    public void test() {
        System.out.println("userService test：" + orderService);
    }
}
