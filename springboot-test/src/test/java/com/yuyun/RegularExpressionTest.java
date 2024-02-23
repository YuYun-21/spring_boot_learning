package com.yuyun;

import org.junit.jupiter.api.Test;

/**
 * 正则表达式
 *
 * @author hyh
 * @since 2023-11-15
 */
public class RegularExpressionTest {

    @Test
    void test() {
        String input = "帮扶工 作 。2023年7月27。  哈哈哈哈骗你的";

        // 使用正则表达式替换文字后面的空格
        String result = input.replaceAll("(?<=[\\u4e00-\\u9fa5])\\s+", "");

        System.out.println("替换后的结果：" + result);

        String result1 = input.replaceAll("\\s+(?=[\\u4e00-\\u9fa5])", "");

        System.out.println("替换后的结果：" + result1);
    }

    @Test
    void test1() {
        String input = "1234567890";

        // 使用正则表达式替换文字后面的空格
        String result = input.replaceAll("(?<=[\\u4e00-\\u9fa5])\\s+", "");

        System.out.println("替换后的结果：" + result);

        String result1 = input.replaceAll("\\s+(?=[\\u4e00-\\u9fa5])", "");

        System.out.println("替换后的结果：" + result1);
    }
}
