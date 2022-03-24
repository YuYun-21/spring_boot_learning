package com.yuyun.service.impl;

import com.yuyun.controller.CommonController;
import com.yuyun.entity.Users;
import com.yuyun.mapper.UserMapper;
import com.yuyun.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public String getCurrentUserId() {
        String userid= (String) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
        if(CommonController.isNullOrSpace(userid)){
            return null;
        }
        else {
            return userid;
        }
    }

    @Override
    public String getCurrentRole() {
        String role=null;
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)    SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for (GrantedAuthority authority : authorities) {
            role = authority.getAuthority();

        }

        if(CommonController.isNullOrSpace(role)){
            return null;
        }
        else{
            return role;
        }
    }

    @Override
    public Users getUsersByUsrNameAndPwd(String usrName, String usrPwd) {
        List<Users> ul=userMapper.getUsersByUsrNameAndPwd(usrName,usrPwd);
        if(ul.size()>0){
            return ul.get(0);
        }
        return null;
    }
}
