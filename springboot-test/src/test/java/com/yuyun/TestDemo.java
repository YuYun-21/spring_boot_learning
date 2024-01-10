package com.yuyun;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author hyh
 * @since 2024-01-05
 */
public class TestDemo {

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
