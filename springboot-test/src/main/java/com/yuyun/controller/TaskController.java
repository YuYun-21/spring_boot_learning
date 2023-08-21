package com.yuyun.controller;

import com.yuyun.task.DynamicCronTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hyh
 * @since 2023-08-18
 */
@RestController
@RequestMapping("icc/meetingRoomAppoint")
public class TaskController {

    @Autowired
    private DynamicCronTask dynamicCronTask;


}
