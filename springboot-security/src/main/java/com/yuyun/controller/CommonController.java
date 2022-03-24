package com.yuyun.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

/**
 * 通用请求处理
 * @Author HeYunHui
 * @create 2020/11/14 15:38
 */
@Controller
public class CommonController {

    protected static final Logger log= LoggerFactory.getLogger(CommonController.class);

    /**
     * 字符串为空
     * @param value
     * @return
     */
    public static boolean isNullOrSpace(String value){

        if(value==null){
            return true;
        }
        else {
            if(value.equals("")){
                return true;
            }
            else {
                return false;
            }
        }
    }
}
