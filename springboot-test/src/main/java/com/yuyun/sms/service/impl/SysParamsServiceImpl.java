package com.yuyun.sms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.yuyun.sms.service.SysParamsService;
import org.springframework.stereotype.Service;

/**
 * @author hyh
 * @since 2023-10-27
 */
@Service
public class SysParamsServiceImpl implements SysParamsService {

    @Override
    public <T> T getValueObject(String paramCode, Class<T> clazz) {

        String paramValue = getValue(paramCode);

        if (StrUtil.isNotBlank(paramValue)) {
            return JSON.parseObject(paramValue, clazz);
        }

        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("params get error");
        }
    }

    /**
     * 从数据库里面查询
     *
     * @return {"ip":"112.35.1.155:1992","ecName":"xxx有限公司","secretKey":"f6b0b43re4wb72ae","apId":"yuyun","sign":"c1trBeDe2","addSerial":""}
     */
    private String getValue(String paramCode) {
        System.out.println("paramCode = " + paramCode);

        return "{\"ip\":\"112.35.1.155:1992\",\"ecName\":\"xxx有限公司\",\"secretKey\":\"f6b0b43re4wb72ae\",\"apId\":\"yuyun\",\"sign\":\"c1trBeDe2\",\"addSerial\":\"\"}";
    }
}
