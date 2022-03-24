package com.yuyun.controller;

import com.yuyun.entity.Users;
import com.yuyun.service.UserInfoService;
import com.yuyun.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author hyh
 */
@CrossOrigin
@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/login")
    @ResponseBody
    public HashMap<String, String> login(
            @RequestBody Account account) throws IOException {
//        Users u=manageService.getUserByUserNameAndPass(account.username,account.password);
        if (account.username.equals("admin") && account.password.equals("123456")) {
//        if(u!=null){
            String jwt = JwtUtils.generateToken("admin", "123456789abc");
//            String jwt= JwtUtils.generateToken(u.getRoleid(),u.getUsersid());


            return new HashMap<String, String>() {{
                put("msg", "ok");
                put("token", jwt);
//                put("role",u.getRoleid());
                put("role", "admin");
            }};
        } else {
            //return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            return new HashMap<String, String>() {{
                put("msg", "error");
                put("token", "error");
            }};
        }
    }

    public static class Account {
        public String username;
        public String password;
    }

    @PostMapping("/login2")
    @ResponseBody
    public HashMap<String, String> login2(
            @RequestBody Account account) throws IOException {
        Users u = userInfoService.getUsersByUsrNameAndPwd(account.username, account.password);
//        if(account.username.equals("admin")&&account.password.equals("123456")){
        if (u != null) {
//            String jwt= JwtUtils.generateToken("admin","123456789abc");
            String jwt = JwtUtils.generateToken(u.getUsrType(), u.getUsrId());


            return new HashMap<String, String>() {{
                put("msg", "ok");
                put("token", jwt);
                put("role", u.getUsrType());
//                put("role","admin");
            }};
        } else {
            //return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            return new HashMap<String, String>() {{
                put("msg", "error");
                put("token", "error");
            }};
        }
    }

}
