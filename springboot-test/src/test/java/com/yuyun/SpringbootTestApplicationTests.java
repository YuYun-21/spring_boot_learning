package com.yuyun;

import com.yuyun.task.DynamicCronTask;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootTestApplicationTests {

    @Autowired
    private DynamicCronTask dynamicCronTask;

    @Test
    void contextLoads() {
        dynamicCronTask.list();

        }

}
