package com.yuyun.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备数据
 *
 * @author hyh 
 * @since 1.0.0 2022-02-16
 */
@Data
@TableName("device_data")
public class DeviceDataDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	private Long id;

	/**
	 * 产品ID
	 */
	private String productId;

	/**
	 * 设备ID
	 */
	private String deviceId;

	/**
	 * 数据流名称
	 */
	private String dataStream;

	/**
	 * 数据
	 */
	private Double dataValue;

	/**
	 * 设备数据点产生时间
	 */
	private Date dateTime;

}