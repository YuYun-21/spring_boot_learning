package com.yuyun.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 企业信息表
 *
 * @author  yuyun
 * @since 1.0.0 2021-12-17
 */
@Data
@TableName("company")
public class CompanyDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 企业名称
	 */
	private String companyName;

	/**
	 * 简介
	 */
	private String description;

	/**
	 * 营业执照
	 */
	private String businessLicense;

	/**
	 * 地址
	 */
	private String address;

	/**
	 * 状态 1：正常  0：停用
	 */
	private Integer status;

	/**
	 * 备注
	 */
	private String remarks;

	/**
	 * 创建时间
	 */
	private Date createDate;

	/**
	 * 更新时间
	 */
	private Date updateDate;


}