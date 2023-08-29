package com.yuyun;

import com.yuyun.dto.People;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hyh
 * @since 2023-08-29
 */
public class ListTest {

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
}
