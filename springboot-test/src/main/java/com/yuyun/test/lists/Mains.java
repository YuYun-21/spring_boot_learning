package com.yuyun.test.lists;

import lombok.Data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author hyh
 * @since 2023-12-26
 */
public class Mains {
    public static void main(String[] args) {
        // 假设有一个 YourObject 对象数组
        YourObject[] objects = {
                new YourObject("A", 10, "02"),
                new YourObject("A", 20, "05"),
                new YourObject("B", 30, "06"),
                // 添加更多对象
        };

        // 创建一个包含所有月份的列表
        List<String> allMonths = Arrays.asList(
                "01", "02", "03", "04", "05", "06",
                "07", "08", "09", "10", "11", "12"
        );

        // 使用 Stream 转换为 Map<String, Map<String, Integer>>
        Map<String, Map<String, Integer>> resultMap = Arrays.stream(objects)
                .collect(Collectors.groupingBy(
                        YourObject::getType,
                        Collectors.toMap(
                                YourObject::getTime,
                                YourObject::getSum,
                                // 如果存在重复的键，保留第一个值
                                Integer::sum,
                                () -> {
                                    Map<String, Integer> zeroMap = new HashMap<>(allMonths.size());
                                    allMonths.forEach(month -> zeroMap.put(month, 1));
                                    return zeroMap;
                                }
                        )
                ));

        // 打印结果
        resultMap.forEach((type, innerMap) -> System.out.println(type + ": " + innerMap));
    }
}

@Data
class YourObject {
    private String type;
    private int sum;
    private String time;
    private Long id;
    private Long pid;

    public YourObject(String type, int sum, String time) {
        this.type = type;
        this.sum = sum;
        this.time = time;
    }
}