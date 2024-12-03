package com.yuyun.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 是单列还是多例Bean的注解
 *
 * @author hyh
 * @since 2024-12-03
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Scope {

    /**
     * prototype 多例
     *
     * @return {@code String }
     */
    String value() default "";
}
