package com.yuyun;

import com.alibaba.fastjson.JSONObject;
import com.yuyun.utils.Base64Utils;
import com.yuyun.utils.HttpUtils;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootOneNetApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void dispatchOrders() {
        String deviceId = "866760442";
        String token = "version=2018-10-31&res=products%2F480701&et=1712112818&method=sha1&sign=89dqMv4ygwfq5dUZXEHsv88m%2BGI%3D";

        JSONObject headers = new JSONObject();
        headers.put("Authorization", token);
        JSONObject jsonObject = new JSONObject();

        // 发送给设备的命令参数
        jsonObject.put("color", "-101");

        String url = "http://api.heclouds.com/v1/synccmds?device_id=" + deviceId + "&timeout=30";
        System.out.println(jsonObject);
        String sendPost = HttpUtils.SendPost(url, headers, jsonObject);

        System.out.println(sendPost);

        JSONObject sp = JSONObject.parseObject(sendPost);
        int errno = sp.containsKey("errno") ? sp.getInteger("errno") : -1;
        System.out.println("errno:" + errno);

        String error = sp.containsKey("error") ? sp.getString("error") : "";
        System.out.println("error:" + error);

        if (errno != 0 && StringUtils.isNotBlank(error)) {
            System.out.println(error);
        }
        JSONObject data = sp.containsKey("data") ? sp.getJSONObject("data") : new JSONObject();
        System.out.println(data);

        if (data.containsKey("cmd_resp")) {
            String cmdResp = data.getString("cmd_resp");
            try {
                String str = Base64Utils.Base64Decode(cmdResp.getBytes());

                System.out.println("解码后的cmd_resp：" + str);
            } catch (Exception e) {
                System.out.println("解码失败！");
            }
        }
    }

    @Test
    public void dispatchOrders2() {
        net.sf.json.JSONObject headers = new net.sf.json.JSONObject();

        String token = "version=2018-10-31&res=products%2F480701&et=1712112818&method=sha1&sign=89dqMv4ygwfq5dUZXEHsv88m%2BGI%3D";
        headers.put("Authorization", token);

        String deviceId = "866760442";
        // 发送给设备的命令参数
        String param = "100";

        String params = String.format("\"color:%s\"", param);
        String url = "http://api.heclouds.com/v1/synccmds?device_id=" + deviceId + "&timeout=30";
        String sendPost = HttpUtils.SendPost(url, headers, params);

        System.out.println(params);
        System.out.println(sendPost);
    }

}
