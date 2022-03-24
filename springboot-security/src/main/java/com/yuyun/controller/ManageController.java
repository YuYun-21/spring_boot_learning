package com.yuyun.controller;

import com.yuyun.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author hyh
 */
@CrossOrigin
@RestController
@PreAuthorize("hasAuthority('admin')") //配置角色，拥有该角色的用户方可访问
@RequestMapping("/manage")
public class ManageController {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("testSecurityResource")
    @ResponseBody
    public String testSecurityResource() throws Exception {

        return "受保护的资源";
    }

    @GetMapping("testSecurityResource2")
    @ResponseBody
    public String testSecurityResource2() throws Exception {

        String userid = userInfoService.getCurrentUserId();
        String role = userInfoService.getCurrentRole();

        return "受保护的资源,当前用户的id是" + userid + "当前用户的角色是" + role;
    }
}
