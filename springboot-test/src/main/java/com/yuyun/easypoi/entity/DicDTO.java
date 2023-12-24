package com.yuyun.easypoi.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 字典
 *
 * @author hyh
 * @since 2023-11-16
 */
@Data
public class DicDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * name
     */
    private String name;
}
