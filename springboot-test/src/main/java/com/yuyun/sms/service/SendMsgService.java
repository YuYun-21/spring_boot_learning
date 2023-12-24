package com.yuyun.sms.service;

import com.yuyun.sms.AbstractSmsService;
import com.yuyun.sms.SmsFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hyh
 * @since 2023-10-27
 */
public class SendMsgService {
    /**
     * 发送短信-多对多
     */
    public void sendMsg() {

        Map<String, String> contentMap = new HashMap<>(3);
        contentMap.put("17856235689", "发送短信内容");
        contentMap.put("17812565689", "发送短信内容1");
        contentMap.put("17812567789", "发送短信内容2");

        AbstractSmsService smsService = SmsFactory.build();
        smsService.sendSms(contentMap);
    }
}
