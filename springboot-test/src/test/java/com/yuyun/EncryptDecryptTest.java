package com.yuyun;

import cn.hutool.core.codec.Base32;
import cn.hutool.core.codec.Base58;
import cn.hutool.core.codec.Base62;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;

/**
 * 加密解密工具
 *
 * @author hyh
 * @since 2023-09-14
 */
public class EncryptDecryptTest {

    /**
     * 加密
     */
    @Test
    void encrypt() {
        String data = "3245678";
        String encode = Base62.encode(data);
        System.out.println("Base62.encode(data) = " + encode);

        String encode1 = Base64.encode(data);
        System.out.println("Base64.encode(data) = " + encode1);

        String encode2 = Base32.encode(data);
        System.out.println("Base32.encode(data) = " + encode2);

        String encode3 = Base58.encode(data.getBytes(StandardCharsets.UTF_8));
        System.out.println("Base58.encode(data) = " + encode3);
    }

    /**
     * 解密
     */
    @Test
    void Decrypt() {
        // 将 Long 转换为 BigDecimal
        //Long longValue = 111L;
        //BigDecimal bigDecimalValue = new BigDecimal(longValue);
        //
        //// 打印结果
        //System.out.println("Long value: " + longValue);
        //System.out.println("BigDecimal value: " + bigDecimalValue);
        //
        //BigDecimal bigDecimal = BigDecimal.valueOf(longValue);
        //System.out.println("bigDecimal = " + bigDecimal);
        //String data = "13zzaWBGTA";
        //String decodeStr = Base62.decodeStr(data);
        //System.out.println("decodeStr = " + decodeStr);
        //
        //System.out.println("bigDecimal.add(new BigDecimal(\"112\")) = " + bigDecimal.add(new BigDecimal("112")));

        //DecimalFormat format = new DecimalFormat("0.00%");
        //BigDecimal decimal = new BigDecimal("0.006");
        //String percent = format.format(decimal);
        //System.out.println("percent = " + percent);
        //
        //// 去除百分号，并将字符串转换为 BigDecimal
        //String cleanedString = "0.3%".replace("%", "");
        //BigDecimal divide = new BigDecimal(cleanedString).divide(new BigDecimal("100"));
        //System.out.println("divide = " + divide);
        //
        //System.out.println("!StrUtil.endWith(\"0.03%\", \"%\") = " + StrUtil.endWith("0.03%", "%"));
        //
        //Assert.isTrue(false,"shibai");

        BigDecimal decimal1 = new BigDecimal("90");
        BigDecimal decimal2 = new BigDecimal("89");
        BigDecimal decimal3 = new BigDecimal("89");

        System.out.println("" + decimal1.compareTo(decimal2));
        System.out.println("" + decimal2.compareTo(decimal1));
        System.out.println("" + decimal2.compareTo(decimal3));

        BigDecimal decimal = new BigDecimal("90");
        System.out.println(decimal.add(null));
        System.out.println(decimal.subtract(null));
    }
}
