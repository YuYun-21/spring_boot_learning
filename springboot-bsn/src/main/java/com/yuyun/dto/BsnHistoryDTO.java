package com.yuyun.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 区块链历史返回结果数据格式
 *
 * @author hyh
 */
@Data
@ApiModel(value = "区块链历史返回结果数据格式")
public class BsnHistoryDTO {

    /**
     * key
     */
    @ApiModelProperty(value = "key")
    private String key;

    /**
     * 交易id
     */
    @ApiModelProperty(value = "交易id")
    private String txId;

    /**
     * 是否删除
     */
    @ApiModelProperty(value = "是否删除")
    private String isDelete;

    /**
     * 时间
     */
    @ApiModelProperty(value = "时间")
    private Date time;

    /**
     * 区块链中存储的具体数据
     */
    @ApiModelProperty(value = "区块链中存储的数据")
    private String value;

    /**
     * 区块链中存储的数据对应的实体
     */
    @ApiModelProperty(value = "区块链中存储的数据对应的实体")
    private CompanyDTO companyDTO;
}
