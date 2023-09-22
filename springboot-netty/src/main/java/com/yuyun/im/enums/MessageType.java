package com.yuyun.im.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 消息类型
 *
 * @author hyh
 * @since 2023-09-21
 */
@Getter
@AllArgsConstructor
public enum MessageType {

    /**
     * 私聊
     */
    PRIVATE(1),
    /**
     * 群聊
     */
    GROUP(2),
    /**
     * 不支持类型
     */
    ERROR(-1),

    ;

    private Integer type;

    public static MessageType match(Integer code) {

        for (MessageType value : MessageType.values()) {
            if (value.getType().equals(code)) {
                return value;
            }
        }
        return ERROR;
    }
}
