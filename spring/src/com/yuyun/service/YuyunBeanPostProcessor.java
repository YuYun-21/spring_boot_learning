package com.yuyun.service;

import com.yuyun.spring.BeanPostProcessor;
import com.yuyun.spring.Component;

import java.lang.reflect.Proxy;

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
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(String beanName, Object bean) {
        if ("userService".equals(beanName)) {
            Object instance = Proxy.newProxyInstance(YuyunBeanPostProcessor.class.getClassLoader(),
                    // 这个类的接口
                    bean.getClass().getInterfaces(),
                    (proxy, method, args) -> {
                        System.out.println("切面逻辑");
                        // 执行bean的method方法
                        return method.invoke(bean, args);
                    });
            return instance;
        }
        return bean;
    }
}
