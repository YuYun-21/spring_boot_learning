package com.yuyun.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author hyh
 */
@ApiModel(value = "通用接口响应对象")
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编码：0表示成功，其他值表示失败
     */
    @ApiModelProperty(value = "编码：0表示成功，其他值表示失败", example = "0")
    private int code = 0;
    /**
     * 数据返回时间
     */
    @ApiModelProperty(value = "数据返回时间", example = "2020-06-29 09:07:34")
    private String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    /**
     * 消息内容
     */
    @ApiModelProperty(value = "消息内容")
    private String msg = "success";
    /**
     * 响应数据
     */
    @ApiModelProperty(value = "响应数据")
    private T data;

    public Result<T> success(T data) {
        this.setData(data);
        return this;
    }

    public Result<T> ok(Boolean b) {
        if (!b){
            return this.error();
        }
        return this;
    }

    /**
     * 判断是否成功
     *
     * @return true 成功
     */
    public boolean success() {
        return code == 0;
    }

    public Result<T> error() {
        this.code = 500;
        this.msg = "操作失败！";
        return this;
    }

    public Result<T> error(int code) {
        this.code = code;
        this.msg = "操作失败！";
        return this;
    }

    public Result<T> error(String msg) {
        this.code = 500;
        this.msg = msg;
        return this;
    }

    public Result<T> error(int code, String msg) {
        this.code = code;
        this.msg = msg;
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
