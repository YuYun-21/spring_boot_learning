package com.yuyun.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yuyun.dto.DeviceDataDTO;
import com.yuyun.dto.DeviceEventDTO;
import com.yuyun.service.DeviceDataService;
import com.yuyun.service.DeviceEventService;
import com.yuyun.service.MessageProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author hyh
 */
@Slf4j
@Service
public class MessageProcessServiceImpl implements MessageProcessService {
    
    @Autowired
    private DeviceEventService deviceEventService;
    @Autowired
    private DeviceDataService deviceDataService;

    @Override
    public void messageProcess(String body) {
        JSONObject obj = JSONObject.parseObject(body);
        //产品信息
        JSONObject product = obj.getJSONObject("sysProperty");
        //产品id
        String productId = product.getString("productId");
        // 消息类型
        String messageType = product.getString("messageType");
        //数据体
        JSONObject appProperty = obj.getJSONObject("appProperty");

        //数据上传时间
        Date time = new Date(appProperty.getLong("dataTimestamp"));
        //设备id
        String deviceId = appProperty.getString("deviceId");

        // 生命周期事件
        if ("deviceLifeCycle".equals(messageType)){
            JSONObject bodyJson = obj.getJSONObject("body");
            String event = bodyJson.getString("event");

            DeviceEventDTO deviceEvent = new DeviceEventDTO();
            deviceEvent.setProductId(productId);
            deviceEvent.setDeviceId(deviceId);
            deviceEvent.setDateTime(time);
            deviceEvent.setEvent(event);
            // 存储到数据库
            deviceEventService.save(deviceEvent);

        }
        // 数据点消息
        else if ("deviceDatapoint".equals(messageType)){
            // 数据流名称
            String dataStream = appProperty.getString("datastream");
            Double dataValue = obj.getDouble("body");

            DeviceDataDTO deviceDataDTO = new DeviceDataDTO();
            deviceDataDTO.setProductId(productId);
            deviceDataDTO.setDeviceId(deviceId);
            deviceDataDTO.setDataStream(dataStream);
            deviceDataDTO.setDataValue(dataValue);
            deviceDataDTO.setDateTime(time);

            // 存储到数据库
            deviceDataService.save(deviceDataDTO);

        } else {
            log.error("未知消息类型的数据：" + body);
        }
    }
}
