package com.yuyun.service;

/**
 * @author hyh
 */
public interface MessageProcessService {

    /**
     * 消息处理，存储到数据库
     *
     * @param body 消息体
     */
    void messageProcess(String body);

}
