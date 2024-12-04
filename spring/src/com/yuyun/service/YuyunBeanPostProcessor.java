package com.yuyun.service;

import com.yuyun.spring.BeanPostProcessor;
import com.yuyun.spring.Component;

/**
 * 对Bean的初始化过程进行干预
 *
 * @author hyh
 * @since 2024-12-04
 */
@Component
public class YuyunBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(String beanName, Object bean) {
        // 对bean进行操作
        if ("userService".equals(beanName)) {
            System.out.println("YuyunBeanPostProcessor.postProcessBeforeInitialization");
        }
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(String beanName, Object bean) {
        if ("userService".equals(beanName)) {
            System.out.println("YuyunBeanPostProcessor.postProcessAfterInitialization");
        }
        return null;
    }
}
