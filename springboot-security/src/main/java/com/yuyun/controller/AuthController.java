package com.yuyun.controller;

import com.yuyun.utils.JwtUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashMap;

/**
 * 令牌环自动更新
 * @Author hyh
 * @PreAuthorize("hasAuthority('admin')")//只允许有admin角色的用户访问 hasAnyAuthority([auth1,auth2])
 */
@CrossOrigin
@RestController
@PreAuthorize("hasAnyAuthority('admin','member')")
@RequestMapping("/auth")
public class AuthController {

    /**
     * 更新令牌环信息
     * @param request
     * @return
     */
    @GetMapping("refreshToken")
    @ResponseBody
    public HashMap<String,String> refreshToken(HttpServletRequest request){


        String role=null;
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)    SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for (GrantedAuthority authority : authorities) {
            role = authority.getAuthority();

        }
        // UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
        String userid= (String)SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
        if(CommonController.isNullOrSpace(role)){
            return new HashMap<String,String>(){{
                put("token","error");
            }};
        }
        else{

            String jwt="";
            //一小时
            jwt= JwtUtils.generateToken(role,userid,60*60*1000);
            HashMap<String,String> m=new HashMap<>();
            m.put("token",jwt);
            return m;


        }

    }

    /**
     * 获取当前登录用户的角色
     * @return
     */
    @GetMapping("getRole")
    @ResponseBody
    public HashMap<String,String> getRoleByToken(){

        String role="";
        String userid="";
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)    SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for (GrantedAuthority authority : authorities) {
            role = authority.getAuthority();

        }
        if(CommonController.isNullOrSpace(role)){
            return new HashMap<String,String>(){{
                put("role","error");
            }};
        }
        else{

            HashMap<String,String> m=new HashMap<>();
            m.put("role",role);
            return m;
        }
    }
}
