package com.yuyun.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuyun.dto.DeviceEventDTO;
import com.yuyun.mapper.DeviceEventMapper;
import com.yuyun.service.DeviceEventService;
import org.springframework.stereotype.Service;

/**
 * 设备在线状态表
 *
 * @author hyh 
 * @since 1.0.0 2022-02-16
 */
@Service
public class DeviceEventServiceImpl extends ServiceImpl<DeviceEventMapper, DeviceEventDTO> implements DeviceEventService {

}