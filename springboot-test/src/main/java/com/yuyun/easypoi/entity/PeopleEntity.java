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
    @Excel(name = "名字", width = 20)
    private String name;
    @Excel(name = "年龄", width = 20)
    private Integer age;
    @Excel(name = "角色", width = 20, dict = "level", addressList = true)
    private Integer rule;
    @Excel(name = "状态", width = 20, replace = {"注销_0", "正常_1", "禁用_2"}, addressList = true)
    private String status;
    @Excel(name = "部门", width = 20, dict = "dept_dic", addressList = true)
    private String dept;
}
