package com.yuyun;

import com.yuyun.dto.People;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author hyh
 * @since 2023-08-29
 */
public class ListTest {

    @Test
    void test() {
        List<Foot> footList = new ArrayList<>();
        footList.add(new Foot("西红柿", 30));
        footList.add(new Foot("白菜", 1056));
        footList.add(new Foot("青菜", 5));
        footList.add(new Foot("豆腐", 1589));

        IntSummaryStatistics collect = footList.stream().collect(Collectors.summarizingInt(Foot::getNumber));
        System.out.println("collect = " + collect);

        List<Foot> footList1 = new ArrayList<>();
        IntSummaryStatistics collect1 = footList1.stream().collect(Collectors.summarizingInt(Foot::getNumber));
        System.out.println("collect1 = " + collect1);

        double i = (double) 3 / 2;
        double g = (double) 7 / 3;
        System.out.println("i = " + String.valueOf(i));
        BigDecimal decimal = new BigDecimal(i);
        System.out.println("decimal = " + decimal);

        BigDecimal bigDouble1 = new BigDecimal(g);
        BigDecimal bigDouble2 = new BigDecimal(i);
        BigDecimal douDifference = new BigDecimal(0);
        // BigDecimal求差
        douDifference = bigDouble1.subtract(bigDouble2);
        System.out.println("差 :" + douDifference);
        System.out.println("差 :" + douDifference.doubleValue());
        System.out.println("差 :" + BigDecimal.valueOf(douDifference.doubleValue()));
        // 打印输出

    }

    /**
     * 将特定的元素移到第一位
     */
    @Test
    void removeToFirst() {
        List<People> peopleList = new ArrayList<>();
        peopleList.add(new People("李四", "30"));
        peopleList.add(new People("张三", "30"));
        peopleList.add(new People("王五", "25"));
        peopleList.add(new People("赵六", "40"));

        // 查找指定的实体
        String name = "王五";
        int targetIndex = -1;
        for (int i = 0; i < peopleList.size(); i++) {
            if (name.equals(peopleList.get(i).getName())) {
                targetIndex = i;
                break;
            }
        }

        if (targetIndex != -1 && targetIndex != 0) {
            People temp = peopleList.remove(targetIndex);
            peopleList.add(0, temp);
        }

        // 打印移动后的结果
        System.out.println("peopleList = " + peopleList);

    }

    /**
     * 将特定的对象和第一个元素互换位置
     */
    @Test
    void replaceAndFirst() {
        List<People> peopleList = new ArrayList<>();
        peopleList.add(new People("李四", "30"));
        peopleList.add(new People("张三", "30"));
        peopleList.add(new People("王五", "25"));
        peopleList.add(new People("赵六", "40"));

        // 查找指定的实体
        String name = "王五";

        int targetIndex = -1;
        for (int i = 0; i < peopleList.size(); i++) {
            if (name.equals(peopleList.get(i).getName())) {
                targetIndex = i;
                break;
            }
        }

        if (targetIndex != -1 && targetIndex != 0) {
            People temp = peopleList.get(0);
            peopleList.set(0, peopleList.get(targetIndex));
            peopleList.set(targetIndex, temp);
        }

        // 打印替换后的结果
        System.out.println("peopleList = " + peopleList);
    }

    @Test
    void RemoveDuplicateValues1() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 9, 3, 2, 1, 3, 7, 2, 1));
        System.out.println("原集合:" + list);
        // 新集合
        List<Integer> newList = new ArrayList<>(list.size());
        list.forEach(i -> {
            // 如果新集合中不存在则插入
            if (!newList.contains(i)) {
                newList.add(i);
            }
        });
        System.out.println("去重后:" + newList);
    }

    @Test
    void RemoveDuplicateValues() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 9, 3, 2, 1, 3, 7, 2, 1));
        System.out.println("原集合:" + list);

        // Iterator<Integer> iterator = list.iterator();
        // while (iterator.hasNext()) {
        //    // 获取循环的值
        //    Integer item = iterator.next();
        //    // 如果存在两个相同的值
        //    if (list.indexOf(item) != list.lastIndexOf(item)) {
        //        // 移除最后那个相同的值
        //        iterator.remove();
        //    }
        //}
        // System.out.println("去重后:" + list);

        // HashSet<Integer> set = new HashSet<>(list);
        // System.out.println("去重后:" + set);

        // LinkedHashSet<Integer> set = new LinkedHashSet<>(list);
        // System.out.println("去重后:" + set);

        TreeSet<Integer> set = new TreeSet<>(list);
        System.out.println("去重集合:" + set);
    }

}

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class Foot {
    private String name;
    private Integer number;
}