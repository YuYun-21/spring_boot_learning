package com.yuyun;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HtmlUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import com.yuyun.utils.MD5Utils;
import org.junit.jupiter.api.Test;

import java.util.Objects;

/**
 * @author hyh
 * @since 2023-08-29
 */
public class HttpUtilTest {


    @Test
    void removeToFirst() {


    }

    @Test
    void httpRequestTest() {
        String clientId = "7d5a3739586e371";
        String clientSecret = "cef441eb8cf2aac3a28";

        String basic = "Basic ";

        String authorization = basic + Base64.encode(clientId + ":" + clientSecret);
        String url = "http://www.baidu.com";

        String httpResponse = HttpRequest.post(url)
                .form("code", "code1234")
                .header("Authorization", authorization)
                .execute()
                .body();

        System.out.println("httpResponse = " + httpResponse);

        JSONObject result = new JSONObject();
        try {
            result = JSONObject.parseObject(httpResponse);
        } catch (Exception e) {
            System.out.println("------------------- = " + HtmlUtil.unescape(httpResponse));
            String s = StrUtil.subBetween(httpResponse, "Exception: ", ", location");
            System.out.println("请求失败：" + s);
            System.out.println("HtmlUtil.unescape(s) = " + HtmlUtil.unescape(s));
        }

        System.out.println("result = " + result);
        System.out.println("Objects.isNull(result) = " + Objects.isNull(result));

        String message = result.getString("message");
        if (!Objects.equals("success", message)) {
            System.out.println("请求失败：" + message);
        }
    }

    @Test
    void md5Test() {
        String plainText = "123456789";
        String saltValue = "Abc";

        System.out.println("MD5Lower(plainText) = " + MD5Utils.MD5Lower(plainText));
        System.out.println("MD5Upper(plainText) = " + MD5Utils.MD5Upper(plainText));
        System.out.println("MD5Lower(plainText, saltValue) = " + MD5Utils.MD5Lower(plainText, saltValue));
        System.out.println("MD5Upper(plainText, saltValue) = " + MD5Utils.MD5Upper(plainText, saltValue));
        System.out.println("校验结果 = " + MD5Utils.valid(plainText, MD5Utils.MD5(plainText)));

        System.out.println("MD5Utils.MD5(plainText) = " + MD5Utils.MD5(plainText));
    }
}
