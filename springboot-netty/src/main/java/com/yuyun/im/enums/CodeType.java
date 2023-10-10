package com.yuyun.im.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 返回值code
 *
 * @author hyh
 * @since 2023-10-10
 */
@Getter
@AllArgsConstructor
public enum CodeType {

    /**
     * 建立连接成功
     */
    SUCCESS_CONNECTION(20001),
    /**
     * 用户列表
     */
    USER_LIST(20002),
    /**
     * 加入群聊成功
     */
    SUCCESS_GROUP(20003),

    /**
     * 错误
     */
    ERROR(-1),
    ;
    private final Integer code;

    public static CodeType match(Integer code) {
        for (CodeType value : CodeType.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return ERROR;
    }
}
