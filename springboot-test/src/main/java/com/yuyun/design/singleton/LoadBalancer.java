package com.yuyun.design.singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 懒汉式单例模式
 *
 * @author hyh
 * @since 2024-02-26
 */
public class LoadBalancer {
    /**
     * 私有静态成员变量，存储唯一实例
     */
    private static LoadBalancer instance = null;
    /**
     * 服务器集合
     */
    private List serverList = null;

    /**
     * 私有构造函数
     */
    private LoadBalancer() {
        serverList = new ArrayList();
    }

    /**
     * 公有静态成员方法，返回唯一实例
     */
    public static LoadBalancer getLoadBalancer() {
        if (instance == null) {
            instance = new LoadBalancer();
        }
        return instance;
    }

    /**
     * 增加服务器
     *
     * @param server
     */
    public void addServer(String server) {
        serverList.add(server);
    }

    /**
     * 删除服务器
     *
     * @param server
     */
    public void removeServer(String server) {
        serverList.remove(server);
    }

    /**
     * 使用Random类随机获取服务器
     */
    public String getServer() {
        Random random = new Random();
        int i = random.nextInt(serverList.size());
        return (String) serverList.get(i);
    }

}

class Client {
    public static void main(String[] args) {
        // 创建四个LoadBalancer对象
        LoadBalancer balancer1, balancer2, balancer3, balancer4;
        balancer1 = LoadBalancer.getLoadBalancer();
        balancer2 = LoadBalancer.getLoadBalancer();
        balancer3 = LoadBalancer.getLoadBalancer();
        balancer4 = LoadBalancer.getLoadBalancer();
        // 判断服务器负载均衡器是否相同
        if (balancer1 == balancer2 && balancer2 == balancer3 && balancer3 == balancer4) {
            System.out.println("服务器负载均衡器具有唯一性！");
        }
        // 增加服务器
        balancer1.addServer("Server 1");
        balancer1.addServer("Server 2");
        balancer1.addServer("Server 3");
        balancer1.addServer("Server 4");
        // 模拟客户端请求的分发
        for (int i = 0; i < 10; i++) {
            String server = balancer1.getServer();
            System.out.println("分发请求至服务器： " + server);
        }
    }
}
