package com.yuyun.dto;

import lombok.Data;

/**
 * @author hyh
 * @since 2023-08-29
 */
@Data
public class People {
    private String name;
    private String age;

    public People(String name, String age) {
        this.name = name;
        this.age = age;
    }
}
