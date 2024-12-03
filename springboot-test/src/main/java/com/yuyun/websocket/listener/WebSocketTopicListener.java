package com.yuyun.websocket.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;

/**
 * WebSocket 主题订阅监听器
 *
 * @author hyh
 */
@Slf4j
public class WebSocketTopicListener implements ApplicationRunner, Ordered {

    /**
     * 在Spring Boot应用程序启动时初始化WebSocket主题订阅监听器
     *
     * @param args 应用程序参数
     * @throws Exception 初始化过程中可能抛出的异常
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 订阅WebSocket消息

        log.info("初始化WebSocket主题订阅监听器成功");
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
