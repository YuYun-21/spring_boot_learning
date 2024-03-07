package com.yuyun;

import lombok.Data;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author hyh
 * @since 2024-01-05
 */
public class TestDemo {

    @Test
    void test2(){
        List<Entity> entities = Arrays.asList(new Entity(1), new Entity(3), new Entity(2),
                new Entity(1), new Entity(3), new Entity(2));

        // 定义一个状态到排序索引的映射
        Map<Integer, Integer> sortOrder = new HashMap<>();
        sortOrder.put(2, 1);
        sortOrder.put(3, 2);
        sortOrder.put(1, 3);

        Comparator<Entity> customComparator = (e1, e2) ->
                Integer.compare(sortOrder.get(e1.getStatus()), sortOrder.get(e2.getStatus()));

        List<Entity> sortedEntities = entities.stream()
                .sorted(customComparator)
                .collect(Collectors.toList());

        sortedEntities.forEach(System.out::println);
    }

    @Test
    void test1() throws UnsupportedEncodingException {

        System.out.println("LocalDate.now() = " + LocalDate.now());
        String s1 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        System.out.println("s1 = " + s1);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateStr = sdf.format(new Date());
        String fileName = URLEncoder.encode("ExcelExportConstants.CAR_BASIC_INFO" + dateStr, StandardCharsets.UTF_8.name());
        System.out.println("fileName = " + fileName);
    }

}

@Data
class Entity {
    private int status;

    public Entity(int status) {
        this.status = status;
    }
}
