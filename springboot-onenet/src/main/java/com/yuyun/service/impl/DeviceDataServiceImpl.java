package com.yuyun.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuyun.dto.DeviceDataDTO;
import com.yuyun.mapper.DeviceDataMapper;
import com.yuyun.service.DeviceDataService;
import org.springframework.stereotype.Service;

/**
 * 设备数据
 *
 * @author hyh 
 * @since 1.0.0 2022-02-16
 */
@Service
public class DeviceDataServiceImpl extends ServiceImpl<DeviceDataMapper, DeviceDataDTO> implements DeviceDataService {

}