package com.yuyun;

import cn.hutool.core.util.NumberUtil;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author hyh
 * @since 2024-02-20
 */
public class BigDecimalTest {

    @Test
    void test() {
        BigDecimal decimal = BigDecimal.valueOf(8118);
        BigDecimal multiply = decimal.divide(new BigDecimal("3600"), 2, RoundingMode.DOWN);
        System.out.println("multiply = " + multiply);

        BigDecimal subtract = BigDecimal.ZERO.subtract(BigDecimal.ONE);
        System.out.println("subtract = " + subtract);
        BigDecimal divide1 = subtract.divide(BigDecimal.ONE, 2, RoundingMode.HALF_UP);
        BigDecimal multiply1 = divide1.multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP);
        System.out.println("divide = " + multiply1);
        System.out.println("divide = " + multiply1 + "%");

        String decimalFormat = NumberUtil.decimalFormat("#0.00%", divide1);
        System.out.println("decimalFormat = " + decimalFormat);

        String decimalFormat2 = NumberUtil.decimalFormat("###,###.00", new BigDecimal("123456789"));
        System.out.println("decimalFormat2 = " + decimalFormat2);

        System.out.println("7/3 = " + 7 / 3);
    }
}
