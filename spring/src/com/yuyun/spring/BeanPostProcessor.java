package com.yuyun.spring;

/**
 * 对Bean创建的过程进行处理
 *
 * @author hyh
 * @since 2024-12-04
 */
public interface BeanPostProcessor {
    /**
     * 初始化前后处理
     *
     * @param beanName bean名称
     * @param bean     bean
     * @return {@code Object }
     */
    Object postProcessBeforeInitialization(String beanName, Object bean);

    /**
     * 初始化后后处理
     *
     * @param beanName bean名称
     * @param bean     bean
     * @return {@code Object }
     */
    Object postProcessAfterInitialization(String beanName, Object bean);
}
