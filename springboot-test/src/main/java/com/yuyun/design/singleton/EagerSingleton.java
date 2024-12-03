package com.yuyun.design.singleton;

/**
 * 饿汉式单例模式
 * 不能实现延迟加载，不管将来用不用始终占据内存
 *
 * @author hyh
 * @since 2024-02-26
 */
public class EagerSingleton {
    private static final EagerSingleton instance = new EagerSingleton();

    private EagerSingleton() {
    }

    public static EagerSingleton getInstance() {
        return instance;
    }
}
