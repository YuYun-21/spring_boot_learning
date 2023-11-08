package com.yuyun.easypoi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 人员信息
 *
 * @author hyh
 */
@Data
public class PeopleImportEntity implements IExcelModel, IExcelDataModel {
    @Excel(name = "名字", isImportField = "name", width = 20)
    @Size(max = 3, message = "长度不能超过3", groups = {ViliGroupOne.class})
    @NotNull(message = "不能为空")
    private String name;

    @Excel(name = "年龄", isImportField = "age", width = 20)
    @Max(value = 30, message = "最高不能超过30")
    private Integer age;

    @Excel(name = "角色", isImportField = "rule", width = 20, dict = "level", addressList = true)
    private Integer rule;

    @Excel(name = "状态", isImportField = "status", width = 20, replace = {"注销_0", "正常_1", "禁用_2"}, addressList = true)
    private String status;

    /**
     * 行号
     */
    private Integer rowNum;

    /**
     * 错误信息
     */
    private String errorMsg;

    @Override
    public Integer getRowNum() {
        return rowNum;
    }

    @Override
    public void setRowNum(Integer integer) {
        this.rowNum = integer;
    }

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }

    @Override
    public void setErrorMsg(String s) {
        this.errorMsg = s;
    }
}
