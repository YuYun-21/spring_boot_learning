package com.yuyun.sms;

import lombok.Data;

import java.io.Serializable;

/**
 * 云mas配置
 *
 * @author hyh
 * @since 2023-05-08
 */
@Data
public class MasMsgConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ip
     */
    private String ip;
    /**
     * 公司名
     */
    private String ecName;
    /**
     * 秘钥
     */
    private String secretKey;
    /**
     * 用户名
     */
    private String apId;
    /**
     * 签名编码
     */
    private String sign;
    /**
     * 扩展码
     */
    private String addSerial;

}
