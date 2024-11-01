package com.yuyun;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.yuyun.easyexcel.DemoData;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author hyh
 * @since 2023-11-17
 */
public class MapTest {

    @Test
    void test3() {
        String ratio = "";
        // 去除百分号，并将字符串转换为 BigDecimal
        String ratioCleaned = ratio.replace("%", "");
        // 扣款比例 已去除百分号
        BigDecimal ratioDecimal = StrUtil.isBlank(ratio) ? BigDecimal.ONE : new BigDecimal(ratio.replace("%", "")).divide(new BigDecimal("100"), 5, RoundingMode.HALF_DOWN);
        System.out.println("ratioDecimal = " + ratioDecimal);

        BigDecimal bigDecimal = BigDecimal.ONE.scaleByPowerOfTen(2);
        System.out.println("bigDecimal = " + bigDecimal);
        BigDecimal divide = new BigDecimal("12.254").divide(bigDecimal, 5, RoundingMode.HALF_DOWN);
        BigDecimal multiply = new BigDecimal("12.254").multiply(bigDecimal);
        System.out.println("divide = " + divide);
        System.out.println("multiply = " + multiply);

        BigDecimal bigDecimal1 = parsePercentage("");
        System.out.println("bigDecimal1 = " + bigDecimal1);

        BigDecimal bigDecimal2 = new BigDecimal("12.25400").movePointLeft(2);
        System.out.println("bigDecimal2 = " + bigDecimal2);
        System.out.println("bigDecimal2 = " + bigDecimal2 + "%");
        System.out.println("bigDecimal2 = " + bigDecimal2.stripTrailingZeros() + "%");
        System.out.println("bigDecimal2.toString() = " + new BigDecimal("12.254").divide(bigDecimal2, 5, RoundingMode.HALF_DOWN));

    }
    // 方法：将BigDecimal表示的百分比转换为不带尾随零的字符串形式
    public static String formatPercentage(BigDecimal percentage) {
        // 将百分比乘以100，以转换为正常的百分数
        BigDecimal scaledPercentage = percentage.multiply(BigDecimal.valueOf(100));
        // 去除尾随的零
        BigDecimal strippedPercentage = scaledPercentage.stripTrailingZeros();
        // 转换为字符串并添加百分号
        return strippedPercentage.toPlainString() + "%";
    }

    public static BigDecimal parsePercentage(String ratio, int scale, RoundingMode roundingMode) {

        return StrUtil.isBlank(ratio) ? BigDecimal.ONE : new BigDecimal(ratio.replace("%", ""))
                .divide(BigDecimal.ONE.scaleByPowerOfTen(2), scale, roundingMode);
    }

    /**
     * 解析百分比
     *
     * @param ratio 比率
     * @return {@link BigDecimal }
     */
    public static BigDecimal parsePercentage(String ratio) {

        return parsePercentage(ratio, 5, RoundingMode.HALF_DOWN);
    }

    @Test
    void test2() {
        Map<String, Integer> map = new HashMap<>();

        // 示例的数据
        String key = "exampleKey";
        int valueToAdd = 5;

        // 使用 computeIfAbsent 初始化 map
        map.computeIfAbsent(key, k -> 0);

        // 获取或计算值
        int currentValue = map.get(key);

        // 在值上执行操作
        map.put(key, currentValue + valueToAdd);

        // 打印结果
        System.out.println(map.get(key)); // 输出 5

        Long deptId = 11L;
        Map<Long, AtomicReference<BigDecimal>> subtotalDecimalAtomicMap = new HashMap<>();
        // 使用 computeIfAbsent 初始化 subtotalDecimalAtomicMap
        subtotalDecimalAtomicMap.computeIfAbsent(deptId, k -> new AtomicReference<>(BigDecimal.ZERO));
        AtomicReference<BigDecimal> bigDecimalAtomicReference = subtotalDecimalAtomicMap.get(deptId);
        BigDecimal bigDecimal = bigDecimalAtomicReference.get();
        System.out.println("bigDecimal = " + bigDecimal);
    }

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
