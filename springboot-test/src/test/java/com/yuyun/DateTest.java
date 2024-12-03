package com.yuyun;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/**
 * @author hyh
 * @since 2024-02-20
 */
public class DateTest {

    @Test
    void dateTest() {
        String dateStr1 = "2017-04-01 22:33:23";
        Date date1 = DateUtil.parse(dateStr1);

        String dateStr2 = "2017-05-01 23:33:23";
        Date date2 = DateUtil.parse(dateStr2);

        // 相差一个月，31天
        long betweenDay = DateUtil.between(date1, date2, DateUnit.DAY);
        System.out.println("betweenDay = " + betweenDay);

        // 相差一个月，31天
        long betweenDays = DateUtil.between(date1, date2, DateUnit.SECOND);
        System.out.println("betweenDay = " + betweenDays);

        String startTime = "2024-01-31 12:00:05";
        String endTime = "2024-01-31 14:15:23";

        DateTime startDate = DateUtil.parse(startTime);
        DateTime endDate = DateUtil.parse(endTime);

        long betweenSecond = DateUtil.between(startDate, endDate, DateUnit.SECOND);
        BigDecimal decimal = BigDecimal.valueOf(betweenSecond);
        BigDecimal multiply = decimal.divide(new BigDecimal("3600"), 2, RoundingMode.DOWN);
        System.out.println("multiply = " + multiply);

        DateTime yesterday = DateUtil.yesterday();
        String yesterdayFormat = DateUtil.formatDate(yesterday);
        System.out.println("yesterdayFormat = " + yesterdayFormat);

        DateTime dateTime = DateUtil.offsetDay(yesterday, -1);
        String yeFormat = DateUtil.formatDate(dateTime);
        System.out.println("yeFormat = " + yeFormat);

        BigDecimal subtract = BigDecimal.ZERO.subtract(BigDecimal.ONE);
        System.out.println("subtract = " + subtract);
        BigDecimal divide1 = subtract.divide(BigDecimal.ONE, 2, RoundingMode.HALF_UP);
        BigDecimal multiply1 = divide1.multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP);
        System.out.println("divide = " + multiply1);
        System.out.println("divide = " + multiply1 + "%");

        String decimalFormat = NumberUtil.decimalFormat("#0.00%", divide1);
        System.out.println("decimalFormat = " + decimalFormat);

    }
}
