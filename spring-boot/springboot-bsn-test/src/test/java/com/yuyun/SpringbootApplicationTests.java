package com.yuyun;

import com.alibaba.fastjson.JSONObject;
import com.bsnbase.sdk.entity.config.Config;
import com.bsnbase.sdk.util.common.Common;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class SpringbootApplicationTests {

    @Test
    void contextLoads() {

    }

//    @Test
//    public void initConfig() throws IOException {
//        Config config = new Config();
//        config.setAppCode("app008809d6356825a4c249be16572723c6225");
//        config.setUserCode("USER0001202112061433104102226");
//        config.setApi("http://52.83.209.158:17502");
//        config.setPrk(Common.readFile("cert/private_key.pem"));
//        config.setPuk(Common.readFile("cert/public_key.pem"));
//        config.setMspDir("");
//        config.initConfig(config);
//    }

    @Test
    public void initConfig() {
        String filePath="config/config.json";
        Config config=Config.buildByConfigJson(filePath);
        config.initConfig(config);
    }

    @Test
    public void hhh(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("basekey" , "10004");
        jsonObject.put("basevalue" , "10004");
        System.out.println(jsonObject);
        System.out.println(JSONObject.parseObject("{\"basekey\":\"161\",\"basevalue\":\"12s3\"}"));
    }

}

