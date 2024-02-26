package com.yuyun.design.singleton;

/**
 * 懒汉式单例模式 与线程锁定
 * 线程安全控制烦琐，而且性能受影响
 *
 * @author hyh
 * @since 2024-02-26
 */
public class LazySingleton {
    private volatile static LazySingleton instance = null;
    private LazySingleton() { }
    public static LazySingleton getInstance() {
        //第一重判断
        if (instance == null) {
            //锁定代码块
            synchronized (LazySingleton.class) {
                //第二重判断
                if (instance == null) {
                    //创建单例实例
                    instance = new LazySingleton();
                }
            }
        }
        return instance;
    }
}
