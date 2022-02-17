package com.yuyun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuyun.dto.DeviceEventDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 设备在线状态表
 *
 * @author hyh 
 * @since 1.0.0 2022-02-16
 */
@Mapper
public interface DeviceEventMapper extends BaseMapper<DeviceEventDTO> {

}