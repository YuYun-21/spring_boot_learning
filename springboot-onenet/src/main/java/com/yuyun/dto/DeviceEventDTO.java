package com.yuyun.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备在线状态表
 *
 * @author hyh 
 * @since 1.0.0 2022-02-16
 */
@Data
@TableName("device_event")
public class DeviceEventDTO implements Serializable {
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
	 * 时间
	 */
	private Date dateTime;

	/**
	 * 生命周期事件
	 */
	private String event;

}