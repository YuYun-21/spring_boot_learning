package com.yuyun.im.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 聊天信息
 *
 * @author hyh
 * @since 2023-09-21
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage extends Command {
    /**
     * 消息类型
     */
    private Integer type;

    /**
     * 信息接收对象
     */
    private String target;

    /**
     * 内容
     */
    private String content;
}
