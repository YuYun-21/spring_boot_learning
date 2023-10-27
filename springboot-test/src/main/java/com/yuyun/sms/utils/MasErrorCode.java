package com.yuyun.sms.utils;

import cn.hutool.core.util.StrUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * mas平台错误码
 *
 * @author hyh
 * @since 2023-05-09
 */
public class MasErrorCode {

    private static final Map<String, String> MAP = new HashMap<>();

    static {
        MAP.put("IllegalMac", "mac校验不通过。");
        MAP.put("IllegalSignId", "无效的签名编码。");
        MAP.put("InvalidMessage", "非法消息，请求数据解析失败。");
        MAP.put("InvalidUsrOrPwd", "非法用户名/密码。");
        MAP.put("NoSignId", "未匹配到对应的签名信息。");
        MAP.put("TooManyMobiles", "手机号数量超限（>5000），应≤5000");
    }

    public static String getMsg(String code) {
        String msg = MAP.get(code);

        return StrUtil.isBlank(msg) ? code : msg;
    }
}
