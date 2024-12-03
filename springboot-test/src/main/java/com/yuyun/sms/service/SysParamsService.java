package com.yuyun.sms.service;

/**
 * @author hyh
 * @since 2023-10-27
 */
public interface SysParamsService {

    /**
     * 根据参数编码，获取value的Object对象
     *
     * @param paramCode 参数编码
     * @param clazz     Object对象
     * @param <T>       返回数据类型
     * @return
     */
    <T> T getValueObject(String paramCode, Class<T> clazz);
}
