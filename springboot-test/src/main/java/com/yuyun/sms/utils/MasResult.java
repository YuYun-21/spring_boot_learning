package com.yuyun.sms.utils;

import lombok.Data;

/**
 * 调用云Mas平台的返回结果
 *
 * @author hyh
 * @since 2023-05-09
 */
@Data
public class MasResult {

    /**
     * 响应状态码
     */
    private String rspcod;
    /**
     * 消息批次号
     */
    private String msgGroup;
    /**
     * 数据校验结果
     */
    private boolean success;
}
