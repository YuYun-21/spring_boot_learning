package com.yuyun.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;

/**
 * HTTP工具
 *
 * @author hyh
 */
public class HttpUtils {
    /**
     * 发送数据到POST请求路径
     *
     * @param url     请求路径
     * @param headers 请求头
     * @param params  请求参数
     * @return String
     */
    @SuppressWarnings("unchecked")
    public static String SendPost(String url, JSONObject headers, JSONObject params) {
        String ret = "";
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(url);

        for (Map.Entry<String, Object> entry : headers.entrySet()) {
            String key = entry.getKey();
            method.addRequestHeader(key, headers.getString(key));
        }

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            method.addParameter(key, params.getString(key));
        }

        try {
            client.executeMethod(method);
            BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String str = "";
            while ((str = reader.readLine()) != null) {
                stringBuffer.append(str);
            }
            ret = stringBuffer.toString();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            method.releaseConnection();
        }

        return ret;
    }

    /**
     * 发送数据到POST请求
     *
     * @param url     发送设备命令的http地址
     * @param headers 请求头
     * @param params  请求参数
     * @return String 返回结果
     */
    @SuppressWarnings({"unchecked", "deprecation"})
    public static String SendPost(String url, net.sf.json.JSONObject headers, String params) {
        String ret = "";
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(url);
        Iterator<String> header = headers.keys();
        while (header.hasNext()) {
            String key = header.next();
            method.addRequestHeader(key, headers.getString(key));
        }

        method.setRequestBody(params);

        try {
            client.executeMethod(method);
            BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), "UTF-8"));
            StringBuffer stringBuffer = new StringBuffer();
            String str = "";
            while ((str = reader.readLine()) != null) {
                stringBuffer.append(str);
            }
            ret = stringBuffer.toString();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            method.releaseConnection();
        }

        return ret;
    }
}
