package com.yuyun;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hyh
 * @since 2023-11-17
 */
public class JsonTest {

    @Test
    void test1() {
    }

    @Test
    void test() {
        // {"humidity":[61.7,60.6,60.5,61],"wins":[1.5,1.6,1.1,1],"uv":[12,32,45,85]}
        String jsonData = "{\"humidity\":[61.7,60.6,60.5,61],\"wins\":[1.5,1.6,1.1,1],\"uv\":[12,32,45,85]}";
        // 解析JSON数据为JSONObject
        JSONObject jsonObject = JSONObject.parseObject(jsonData);
        // 创建一个List用于存储结果
        List<Map<String, Object>> resultList = new ArrayList<>();
        // 计算新数组的长度（以原始数据中最长的键对应的值的列表长度为准）
        // 获取原始数据中的所有键对应的值的数组，并确定数组的最大长度
        int maxLength = 0;
        for (String key : jsonObject.keySet()) {
            JSONArray values = jsonObject.getJSONArray(key);
            maxLength = Math.max(maxLength, values.size());
        }
        for (int i = 0; i < maxLength; i++) {
            Map<String, Object> keyValueMap = new HashMap<>(jsonObject.size());
            for (String key : jsonObject.keySet()) {
                JSONArray values = jsonObject.getJSONArray(key);
                Object value = values.get(i);
                keyValueMap.put(key, value);
            }
            // 将每个新的 Map 对象添加到新的 List 中
            resultList.add(keyValueMap);
        }
        System.out.println("resultList = " + JSONObject.toJSONString(resultList));
        // resultList = [{"wins":1.5,"uv":12,"humidity":61.7},{"wins":1.6,"uv":32,"humidity":60.6},{"wins":1.1,"uv":45,"humidity":60.5},{"wins":1,"uv":85,"humidity":61}]
    }
}
