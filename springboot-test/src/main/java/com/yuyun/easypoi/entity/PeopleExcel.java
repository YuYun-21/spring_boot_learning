package com.yuyun.easypoi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * 员工信息表
 *
 * @author hyh
 */
@Data
public class PeopleExcel implements IExcelModel, IExcelDataModel, Serializable {
    private static final long serialVersionUID = 1L;

    @Excel(name = "姓名", width = 8)
    @NotNull(message = "不能为空")
    private String name;

    /**
     * 性别
     */
    @Excel(name = "性别", width = 6, replace = {"男_1", "女_2"}, addressList = true)
    @NotNull(message = "不能为空")
    private String sex;

    @Excel(name = "手机号码", width = 12)
    @Pattern(regexp = "^1[3456789]\\d{9}$", message = "手机号码格式错误！")
    @NotNull(message = "不能为空")
    private String mobile;

    @Excel(name = "身份证号码", width = 18)
    @NotNull(message = "不能为空")
    private String idCard;

    /**
     * 政治面貌，只能填：党员、群众、其他
     */
    @Excel(name = "政治面貌", width = 10, replace = {"党员_党员", "群众_群众", "其他_其他"}, addressList = true)
    @NotNull(message = "不能为空")
    private String politicsStatus;

    @Excel(name = "专业", width = 14)
    private String profession;

    @Excel(name = "入职时间", width = 12)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    @NotNull(message = "不能为空")
    private Date entryTime;

    @Excel(name = "工作期限（年）", width = 12)
    @NotNull(message = "不能为空")
    private Integer deadline;

    @Excel(name = "来源分类", width = 22, replace = {"公益型_001", "乡镇街道村居人员兼任_002", "劳务派遣_003", "直接选用_004", "其他_005"}, addressList = true)
    @NotNull(message = "不能为空")
    private String sourceType;

    /**
     * 是否在职，1 在职  0 离职
     */
    @Excel(name = "是否在职", width = 10, replace = {"在职_1", "离职_0"}, addressList = true)
    @NotNull(message = "不能为空")
    private Integer state;

    @Excel(name = "所属的部门", width = 12, dict = "dept_dic", addressList = true)
    @NotNull(message = "不能为空")
    private Long deptId;

    @Excel(name = "专业标签", width = 10, dict = "tag_dic", addressList = true)
    @NotNull(message = "不能为空")
    private Long tagId;

    /**
     * 行号
     */
    private Integer rowNum;

    /**
     * 错误信息
     */
    private String errorMsg;
}