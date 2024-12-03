package com.yuyun.easypoi.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author hyh
 * @since 2023-11-17
 */
public class MapMain {
    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        Map<String, String> originalMap = createOriginalMap();

        Map<String, String> resultMap = generateCombinations(originalMap);

        // 打印结果映射
        resultMap.forEach((key, value) -> System.out.println("\"" + key + "\": \"" + value + "\""));

        System.out.println("result.size() = " + resultMap.size());

        long end = System.currentTimeMillis() - start;
        System.out.println("end = " + end);
    }

    private static Map<String, String> generateCombinations(Map<String, String> originalMap) {
        List<String> keys = new ArrayList<>(originalMap.keySet());

        // 生成所有可能的键组合
        List<List<String>> keyCombinations = new ArrayList<>();

        for (int size = 1; size <= keys.size(); size++) {
            keyCombinations.addAll(combinations(keys, size));
        }

        return keyCombinations.stream().collect(Collectors.toMap(
                combination -> String.join(",", combination),
                combination -> combination.stream().map(originalMap::get).collect(Collectors.joining(","))
        ));
    }

    private static List<List<String>> combinations(List<String> keys, int size) {
        List<List<String>> result = new ArrayList<>();
        generateCombinationsHelper(keys, size, 0, new ArrayList<>(), result);
        return result;
    }

    private static void generateCombinationsHelper(List<String> keys, int size, int start, List<String> current,
                                                   List<List<String>> result) {
        if (size == 0) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int i = start; i < keys.size(); i++) {
            current.add(keys.get(i));
            generateCombinationsHelper(keys, size - 1, i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }

    // private static Map<String, String> generateCombinations(Map<String, String> inputMap) {
    //    List<String> keys = new ArrayList<>(inputMap.keySet());
    //
    //    List<String> keyCombinations = IntStream.rangeClosed(1, keys.size())
    //            .boxed()
    //            .flatMap(size -> combinations(keys, size).stream()
    //                    .map(combination -> String.join(",", combination)))
    //            .collect(Collectors.toList());
    //
    //    return keyCombinations.stream()
    //            .distinct()
    //            .filter(StringUtils::isNotBlank)
    //            .collect(Collectors.toMap(
    //                    key -> key,
    //                    key -> Arrays.stream(key.split(","))
    //                            .map(inputMap::get)
    //                            .collect(Collectors.joining(","))
    //            ));
    //}
    //
    // private static <T> List<List<T>> combinations(List<T> list, int size) {
    //    if (size == 0) {
    //        return Collections.singletonList(Collections.emptyList());
    //    }
    //    if (list.isEmpty()) {
    //        return Collections.emptyList();
    //    }
    //
    //    T head = list.get(0);
    //    List<List<T>> tailCombinations = combinations(list.subList(1, list.size()), size - 1);
    //    return Stream.concat(tailCombinations.stream(), tailCombinations.stream()
    //                    .map(sublist -> new ArrayList<T>(sublist) {{
    //                        add(0, head);
    //                    }}))
    //            .collect(Collectors.toList());
    //}

    private static Map<String, String> createOriginalMap() {
        Map<String, String> originalMap = new HashMap<>();
        originalMap.put("0", "校长");
        originalMap.put("1", "学生");
        originalMap.put("2", "老师");
        originalMap.put("3", "老师3");
        return originalMap;
    }

}

