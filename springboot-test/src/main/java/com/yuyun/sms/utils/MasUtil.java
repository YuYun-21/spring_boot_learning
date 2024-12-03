package com.yuyun.sms.utils;

import cn.hutool.core.codec.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * Mas工具
 *
 * @author hyh
 * @since 2023-05-09
 */
public class MasUtil {
    static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 生成MD5码
     *
     * @param plainText 要加密的字符串
     * @return md5值
     */
    public final static String MD5(String plainText) {
        try {
            byte[] strTemp = plainText.getBytes(StandardCharsets.UTF_8);
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = HEX_DIGITS[byte0 >>> 4 & 0xf];
                str[k++] = HEX_DIGITS[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 先进行HmacSHA1转码再进行Base64编码
     *
     * @param data 要SHA1的串
     * @param key  秘钥
     * @return
     * @throws Exception
     */
    public static String hmacSHA1ToBase64(String data, String key) throws Exception {
        SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(data.getBytes());
        return Base64.encode(rawHmac);
    }

    /**
     * 校验MD5码
     *
     * @param text 要校验的字符串
     * @param md5  md5值
     * @return 校验结果
     */
    public static boolean valid(String text, String md5) {
        return md5.equals(MD5(text)) || md5.equals(MD5(text).toUpperCase());
    }

    /**
     * @param params
     * @return
     */
    public static String MD5(String... params) {
        StringBuilder sb = new StringBuilder();
        for (String param : params) {
            sb.append(param);
        }
        return MD5(sb.toString());
    }
}
