package com.yuyun.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@TableName("users")
@Data
public class Users {
    
    @TableId(type = IdType.AUTO)
    private String usrId;
    private String usrName;

    private String usrTel;

    private String usrPwd;

    private String usrType;

}
