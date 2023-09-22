package com.yuyun.im.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 连接类型
 *
 * @author hyh
 * @since 2023-09-21
 */
@Getter
@AllArgsConstructor
public enum CommandType {

    /**
     * 建立连接
     */
    CONNECTION(10001),
    /**
     * 聊天
     */
    CHAT(10002),
    /**
     * 加入群聊
     */
    JOIN_GROUP(10003),
    /**
     * 错误
     */
    ERROR(-1),

    ;

    private final Integer code;

    public static CommandType match(Integer code) {
        for (CommandType value : CommandType.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return ERROR;
    }
}
