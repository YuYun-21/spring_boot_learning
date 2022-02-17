package com.yuyun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuyun.dto.DeviceDataDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 设备数据
 *
 * @author hyh 
 * @since 1.0.0 2022-02-16
 */
@Mapper
public interface DeviceDataMapper extends BaseMapper<DeviceDataDTO> {

}