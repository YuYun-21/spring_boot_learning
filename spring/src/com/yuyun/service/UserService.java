package com.yuyun.service;

import com.yuyun.spring.Autowired;
import com.yuyun.spring.BeanNameAware;
import com.yuyun.spring.Component;

/**
 * @author hyh
 * @since 2024-12-03
 */
@Component
public class UserService implements BeanNameAware {

    @Autowired
    private OrderService orderService;

    private String beanName;

    /**
     * 设置bean名称 由spring来调用
     *
     * @param beanName bean名称
     */
    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public void test() {
        System.out.println("userService test：" + orderService);
    }
}
