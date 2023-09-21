package com.yuyun.im.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 连接信息
 *
 * @author hyh
 * @since 2023-09-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Command {

    /**
     * 连接信息编码
     */
    private Integer code;

    /**
     * 昵称
     */
    private String nickname;
}
