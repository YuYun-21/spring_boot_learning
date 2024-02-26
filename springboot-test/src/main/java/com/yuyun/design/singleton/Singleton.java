package com.yuyun.design.singleton;

/**
 * 单例模式
 * Initialization Demand Holder (IoDH)技术 两种单例的缺点都克服
 *
 * @author hyh
 * @since 2024-02-26
 */
public class Singleton {
    private Singleton() {
    }
    private static class HolderClass {
        private final static Singleton instance = new Singleton();
    }
    public static Singleton getInstance() {
        return HolderClass.instance;
    }

    public static void main(String[] args) {
        Singleton s1, s2;
        s1 = Singleton.getInstance();
        s2 = Singleton.getInstance();
        System.out.println(s1==s2);
    }
}
