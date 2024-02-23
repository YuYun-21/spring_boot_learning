package com.yuyun;

import com.alibaba.fastjson.JSON;
import com.yuyun.easyexcel.DemoData;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author hyh
 * @since 2023-11-17
 */
public class MapTest {
    /**
     * 将集合映射
     *
     * @param collection   集合            List<SysDept>
     * @param keyMapper    密钥映射器       SysDept::getDeptId
     * @param valueMapper  值映射器         SysDept::getDeptName
     * @param keyList      密钥列表         List<Long>
     * @param defaultValue 默认值           ""
     * @return {@link Map}<{@link K}, {@link V}>
     */
    public static <E, K, V> Map<K, V> toMap(Collection<E> collection,
                                            Function<E, K> keyMapper,
                                            Function<E, V> valueMapper,
                                            Collection<K> keyList,
                                            V defaultValue) {


        return collection.stream().collect(Collectors.toMap(
                keyMapper,
                valueMapper,
                (a, b) -> b,
                () -> {
                    Map<K, V> emptyMap = new HashMap<>(keyList.size());
                    keyList.forEach(id -> emptyMap.put(id, defaultValue));
                    return emptyMap;
                }
        ));
    }

    @Test
    void test1() {
        List<DemoData> list = new ArrayList<>();
        List<String> stringList = new ArrayList<>();
        stringList.add("333");
        stringList.add("fdaf");
        stringList.add("erew");
        stringList.add("543");
        Map<String, String> map = toMap(list, DemoData::getString, DemoData::getIgnore, stringList, "");
        System.out.println("map = " + map);

        for (int i = 1; i < 3; i++) {
            System.out.println("i = " + i);
        }
    }

    @Test
    void test() {

        long start = System.currentTimeMillis();
        Map<String, String> inputMap = new HashMap<>();
        inputMap.put("0", "校长");
        inputMap.put("1", "学生");
        inputMap.put("2", "老师");

        Map<String, String> resultMap = generateRandomCombinations(inputMap);

        // 打印结果映射
        resultMap.forEach((key, value) -> System.out.println("\"" + key + "\": \"" + value + "\""));

        Map<String, String> resultMaps = generateRandomCombinations(inputMap);
        String jsonString = JSON.toJSONString(resultMaps);
        System.out.println("jsonString = " + jsonString);

        long end = System.currentTimeMillis() - start;
        System.out.println("end = " + end);
    }

    private static Map<String, String> generateRandomCombinations(Map<String, String> inputMap) {
        List<String> keys = new ArrayList<>(inputMap.keySet());
        List<String> values = new ArrayList<>(inputMap.values());

        // 打乱键
        Collections.shuffle(keys);

        // 生成所有可能的键组合
        List<List<String>> keyCombinations = new ArrayList<>();
        for (int r = 1; r <= keys.size(); r++) {
            keyCombinations.addAll(combinations(keys, r));
        }

        // 打乱键组合
        Collections.shuffle(keyCombinations);

        // 创建结果映射
        Map<String, String> resultMap = new LinkedHashMap<>();
        for (List<String> keyCombination : keyCombinations) {
            String combinedKey = String.join(",", keyCombination);
            String combinedValue = keyCombination.stream()
                    .map(inputMap::get)
                    .collect(Collectors.joining(","));
            resultMap.put(combinedKey, combinedValue);
        }

        return resultMap;
    }

    private static <T> List<List<T>> combinations(List<T> list, int size) {
        List<List<T>> combinations = new ArrayList<>();
        combine(combinations, new ArrayList<>(), list, size, 0);
        return combinations;
    }

    private static <T> void combine(List<List<T>> combinations, List<T> temp, List<T> list, int size, int start) {
        if (size == 0) {
            combinations.add(new ArrayList<>(temp));
            return;
        }

        for (int i = start; i < list.size(); i++) {
            temp.add(list.get(i));
            combine(combinations, temp, list, size - 1, i + 1);
            temp.remove(temp.size() - 1);
        }
    }
}
