package com.yuyun.spring;

/**
 * 初始化bean
 *
 * @author hyh
 * @date 2024-12-04
 */
public interface InitializingBean {

    /**
     * 设置属性 也可以是其他代码 由spring调用
     */
    void afterPropertiesSet();
}
