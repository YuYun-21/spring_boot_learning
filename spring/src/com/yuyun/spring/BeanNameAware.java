package com.yuyun.spring;

/**
 * spring来管理bean的名字
 *
 * @author hyh
 * @since 2024-12-04
 */
public interface BeanNameAware {
    /**
     * 设置bean的名字
     *
     * @param beanName bean的名字
     */
    void setBeanName(String beanName);
}
