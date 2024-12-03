package com.yuyun.spring;

/**
 * Bean定义
 *
 * @author hyh
 * @date 2024-12-03
 */
public class BeanDefinition {

    /**
     * 类型
     */
    private Class type;
    /**
     * 范围 单例 多例
     */
    private String scope;

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
