package com.yuyun.service;

import com.yuyun.entity.Users;

/**
 * @author hyh
 */
public interface UserInfoService {

    String getCurrentUserId();//从令牌环中获取userid
    String getCurrentRole();//从令牌环中获取角色id

    Users getUsersByUsrNameAndPwd(String usrName, String usrPwd);
}
