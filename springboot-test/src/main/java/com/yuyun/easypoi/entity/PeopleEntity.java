package com.yuyun.easypoi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * 人员信息
 *
 * @author hyh
 */
@Data
public class PeopleEntity {
    @Excel(name = "名字")
    private String name;
    @Excel(name = "年龄")
    private Integer age;
    @Excel(name = "角色", dict = "level", addressList = true)
    private Integer rule;
    @Excel(name = "状态", replace = {"注销_0", "正常_1", "禁用_2"}, addressList = true)
    private String status;
}
