package com.yuyun.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hyh
 */
@RestController
public class HelloController {

    @RequestMapping("hello")
    public String index(){

        return "hello";
    }
}
