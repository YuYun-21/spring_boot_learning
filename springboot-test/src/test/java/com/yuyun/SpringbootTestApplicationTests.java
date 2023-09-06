package com.yuyun;

import cn.hutool.http.HttpUtil;
import com.yuyun.task.DynamicCronTask;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class SpringbootTestApplicationTests {

    @Autowired
    private DynamicCronTask dynamicCronTask;

    @Test
    void contextLoads() {
        dynamicCronTask.list();

    }

    @Test
    void restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://10.0.0.24:8083/wzkj-api/questionAnswer/page";
        JSONObject forObject = restTemplate.getForObject(url, JSONObject.class);

        System.out.println("forObject = " + forObject);

        String response1 = HttpUtil.get(url);

        System.out.println("response1 = " + response1);

    }

}
