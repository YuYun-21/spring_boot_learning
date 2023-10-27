package com.yuyun.sms;

import java.util.Map;

/**
 * @author hyh
 * @since 2023-10-26
 */
public abstract class AbstractSmsService {

    /**
     * 短信配置信息
     */
    MasMsgConfig config;

    /**
     * 发送短信-多对多
     *
     * @param contentMap mobile-content
     */
    public abstract void sendSms(Map<String, String> contentMap);

}
