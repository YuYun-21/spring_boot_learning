package com.yuyun.utils;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author hyh
 * @since 2023-11-14
 */
public class MapUtils {
    public static void main(String[] args) {
        MapUtils test = new MapUtils();
        Map<String, Map<String, String>> map = test.getMap();
        Map<String, Map<String, String>> reversalMap = test.getReversalMap();
        Map<String, Map<String, String>> swappedMap = test.getSwappedMap();

        System.out.println("map = " + JSON.toJSONString(map));
        System.out.println("reversalMap = " + JSON.toJSONString(reversalMap));
        System.out.println("swappedMap = " + JSON.toJSONString(swappedMap));
    }

    /**
     * 原来的map
     *
     * @return
     */
    public Map<String, Map<String, String>> getMap() {
        Map<String, Map<String, String>> map = new HashMap<>();
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("0", "校长");
        stringMap.put("1", "学生");
        stringMap.put("2", "老师");
        map.put("level", stringMap);

        Map<String, String> deptMap = new HashMap<>();
        deptMap.put("001", "小学");
        deptMap.put("002", "初中");
        deptMap.put("003", "高中");
        map.put("dept_dic", deptMap);

        return map;
    }

    /**
     * 反转后的map
     *
     * @return
     */
    public Map<String, Map<String, String>> getReversalMap() {
        return getMap().entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> entry.getValue().entrySet().stream().collect(Collectors.toMap(
                        Map.Entry::getValue,
                        Map.Entry::getKey
                ))
        ));
    }

    /**
     * 反转后的map
     *
     * @return
     */
    public Map<String, Map<String, String>> getSwappedMap() {
        Map<String, Map<String, String>> swappedMap = new HashMap<>();

        for (Map.Entry<String, Map<String, String>> entry : getMap().entrySet()) {
            Map<String, String> innerMap = entry.getValue();
            Map<String, String> swappedInnerMap = new HashMap<>();

            for (Map.Entry<String, String> innerEntry : innerMap.entrySet()) {
                swappedInnerMap.put(innerEntry.getValue(), innerEntry.getKey());
            }

            swappedMap.put(entry.getKey(), swappedInnerMap);
        }

        return swappedMap;
    }
}
