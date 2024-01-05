package com.yuyun;

import cn.hutool.core.codec.Base32;
import cn.hutool.core.codec.Base58;
import cn.hutool.core.codec.Base62;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.AsymmetricCrypto;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * 加密解密工具
 *
 * @author hyh
 * @since 2023-09-14
 */
public class EncryptDecryptTest {

    @Test
    void rsa4() {
        String psw = "M9iyVTjRrHnrXCJB/p/ZsM+OaBvBHDjZE+kP7agtNhaMuenyKzffpMSvDJKPAp096eH26ScEs1HAVvoOmTDtCtCbz1" +
                "pJz0n+Bd+FIMesfwvh9HjWM7xod/jJKYi1ioQFG7Dnkzk8dPjw05Tvw/Z6wTnNsPYD730eHwiJJGanCj0";

        String public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCoRl/9+QidTl1LLXvNFy3AqIIYB98e23q49Hj5Vjz/0JOXf/" +
                "P7RIVE8Nt6p7CNY061S9Vt2x57WZssbSk0XbSLRgiB5DFYOLn8nu4HKhTuqo+fnH0K+nidOQgeFFVKFj/N8UTti1FAlsUd3ep" +
                "M6BsO5mVuE/snwEh1MZlUT16mKQIDAQAB";
        RSA rsa = new RSA(null, public_key);
        // 公钥加密，私钥解密
        byte[] encrypt = rsa.encrypt(StrUtil.bytes("a80331", CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
        System.out.println("encrypt = " + Arrays.toString(encrypt));
        System.out.println("encrypt = " + Base64.encode(encrypt));
    }

    @Test
    void rsa3() {
        String PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAI0e1e9sysjRhiSO7uS1rBiijfsWe/0chfMOaVduUR50u" +
                "3XuPr9xWQX0tRfX0r+pygR1uEJwazgurut5G7t+uNPJ7Ns1k7w2eVY2CbZ9I2W17FWB9XtJzzXbrnurlDTWZ6oyPGSPn65Yed+dapKS29s" +
                "TPwksMBKMnM/tcScqp3xNAgMBAAECgYByF6ENHl7gdw1F101rGToW/K1fd/EsVPgy5s7iAe2RevzoPoL6knBP8WTL7xCTJnmOm4IqLOtyk" +
                "8OV2udB8hUI53XKs9tz/aGxxinIQlYGhf7fU0PjISAG7lNvRRU3UIizW7RmhEJpcCGEoPjak1xqUEkDS0JY34UwQDuuUuywfQJBANi5fS+" +
                "Qf1OH+7ocFAfiCPyDVordM4sD+cYIx4HDjHJL7o03x8PyOHpVELn9OyW7Uz+DenO0Y3c2A9g0E2JHMc8CQQCmsdeWTmrm3haMW5HdqzA+ro" +
                "8knKYDgymALS67isgqISyAwiOMosKOcVEniQTTMFw+OTCNYyw8bPzOoqh2FcMjAkBryQS7rS/GCXFmhCRqLz6s510ZPx4tW1LT7PXcUbb9+" +
                "UmxLjbJ+yOfo8Ln3UuXXmzuv6Mc/HAjj9elpXCmhiX9AkA1T+uDRtEpxR2xBhhArN9hLLLbIr51oR1EwS6RDHG4B6QUCgMUl3lh1Rv/aPMCGB" +
                "VoUSIWFgGv0auI6ry2B3/BAkBYTz1xHggEWogBti8WwzHC+Tozo4Vx/NmXhtMvMIfdeBuh0ANEOnC37KGE7g9EYf+Giblvh8pcRoKihV0gwB8/";
        String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCNHtXvbMrI0YYkju7ktawYoo37Fnv9HIXzDmlXblEedLt17j6/cVkF9LUX1" +
                "9K/qcoEdbhCcGs4Lq7reRu7frjTyezbNZO8NnlWNgm2fSNltexVgfV7Sc812657q5Q01meqMjxkj5+uWHnfnWqSktvbEz8JLDASjJzP7XEnKqd8TQIDAQAB";

        RSA rsa = new RSA(PRIVATE_KEY, PUBLIC_KEY);
        // 公钥加密，私钥解密
        byte[] encrypt = rsa.encrypt(StrUtil.bytes("我是一段测试aaaa", CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
        System.out.println("encrypt = " + Arrays.toString(encrypt));

        byte[] decrypt = rsa.decrypt(encrypt, KeyType.PrivateKey);
        System.out.println("公钥加密，私钥解密 = " + StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8));

        // rsa加密后的秘文 用base64再加密，可直接解密
        String encode = Base64.encode(encrypt);
        byte[] decrypt1 = rsa.decrypt(encode, KeyType.PrivateKey);
        System.out.println("公钥加密，私钥解密 = " + StrUtil.str(decrypt1, CharsetUtil.CHARSET_UTF_8));

    }

    @Test
    void rsa2() {

        String PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAI0e1e9sysjRhiSO7uS1rBiijfsWe/0chfMOaVduUR50u3XuPr9xWQX0tRfX0r+pygR1uEJwazgurut5G7t+uNPJ7Ns1k7w2eVY2CbZ9I2W17FWB9XtJzzXbrnurlDTWZ6oyPGSPn65Yed+dapKS29sTPwksMBKMnM/tcScqp3xNAgMBAAECgYByF6ENHl7gdw1F101rGToW/K1fd/EsVPgy5s7iAe2RevzoPoL6knBP8WTL7xCTJnmOm4IqLOtyk8OV2udB8hUI53XKs9tz/aGxxinIQlYGhf7fU0PjISAG7lNvRRU3UIizW7RmhEJpcCGEoPjak1xqUEkDS0JY34UwQDuuUuywfQJBANi5fS+Qf1OH+7ocFAfiCPyDVordM4sD+cYIx4HDjHJL7o03x8PyOHpVELn9OyW7Uz+DenO0Y3c2A9g0E2JHMc8CQQCmsdeWTmrm3haMW5HdqzA+ro8knKYDgymALS67isgqISyAwiOMosKOcVEniQTTMFw+OTCNYyw8bPzOoqh2FcMjAkBryQS7rS/GCXFmhCRqLz6s510ZPx4tW1LT7PXcUbb9+UmxLjbJ+yOfo8Ln3UuXXmzuv6Mc/HAjj9elpXCmhiX9AkA1T+uDRtEpxR2xBhhArN9hLLLbIr51oR1EwS6RDHG4B6QUCgMUl3lh1Rv/aPMCGBVoUSIWFgGv0auI6ry2B3/BAkBYTz1xHggEWogBti8WwzHC+Tozo4Vx/NmXhtMvMIfdeBuh0ANEOnC37KGE7g9EYf+Giblvh8pcRoKihV0gwB8/";
        String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCNHtXvbMrI0YYkju7ktawYoo37Fnv9HIXzDmlXblEedLt17j6/cVkF9LUX19K/qcoEdbhCcGs4Lq7reRu7frjTyezbNZO8NnlWNgm2fSNltexVgfV7Sc812657q5Q01meqMjxkj5+uWHnfnWqSktvbEz8JLDASjJzP7XEnKqd8TQIDAQAB";
        // 私钥和公钥都是动生成的，是互相关联的
        RSA rsa = new RSA(PRIVATE_KEY, null);
        // 一般来说 私钥和公钥都是动生成的，但是可以根据私钥生成对应的公钥
        rsa.initKeys();
        // 获得私钥
        System.out.println("rsa.getPrivateKey() = " + rsa.getPrivateKey());
        System.out.println("rsa.getPrivateKeyBase64() = " + rsa.getPrivateKeyBase64());
        // 获得公钥
        System.out.println("rsa.getPublicKey() = " + rsa.getPublicKey());
        System.out.println("rsa.getPublicKeyBase64() = " + rsa.getPublicKeyBase64());

        // 公钥加密，私钥解密
        byte[] encrypt = rsa.encrypt(StrUtil.bytes("我是一段测试aaaa", CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
        byte[] decrypt = rsa.decrypt(encrypt, KeyType.PrivateKey);
        System.out.println("公钥加密，私钥解密 = " + StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8));

        // 私钥加密，公钥解密
        byte[] encrypt2 = rsa.encrypt(StrUtil.bytes("我是一段测试aaaa", CharsetUtil.CHARSET_UTF_8), KeyType.PrivateKey);
        byte[] decrypt2 = rsa.decrypt(encrypt2, KeyType.PublicKey);
        System.out.println("私钥加密，公钥解密：" + StrUtil.str(decrypt2, CharsetUtil.CHARSET_UTF_8));

        // 私钥加密，私钥解密 不可行
        byte[] encrypt3 = rsa.encrypt(StrUtil.bytes("我是一段测试aaaa", CharsetUtil.CHARSET_UTF_8), KeyType.PrivateKey);
        byte[] decrypt3 = rsa.decrypt(encrypt3, KeyType.PrivateKey);
        System.out.println("私钥加密，私钥解密：" + StrUtil.str(decrypt3, CharsetUtil.CHARSET_UTF_8));

        // 公钥加密，公钥解密 不可行
        byte[] encrypt4 = rsa.encrypt(StrUtil.bytes("我是一段测试aaaa", CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
        byte[] decrypt4 = rsa.decrypt(encrypt4, KeyType.PublicKey);
        System.out.println("公钥加密，公钥解密 = " + StrUtil.str(decrypt4, CharsetUtil.CHARSET_UTF_8));
    }

    @Test
    void rsa() {

        String PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIL7pbQ+5KKGYRhw7jE31hmA"
                + "f8Q60ybd+xZuRmuO5kOFBRqXGxKTQ9TfQI+aMW+0lw/kibKzaD/EKV91107xE384qOy6IcuBfaR5lv39OcoqNZ"
                + "5l+Dah5ABGnVkBP9fKOFhPgghBknTRo0/rZFGI6Q1UHXb+4atP++LNFlDymJcPAgMBAAECgYBammGb1alndta"
                + "xBmTtLLdveoBmp14p04D8mhkiC33iFKBcLUvvxGg2Vpuc+cbagyu/NZG+R/WDrlgEDUp6861M5BeFN0L9O4hz"
                + "GAEn8xyTE96f8sh4VlRmBOvVdwZqRO+ilkOM96+KL88A9RKdp8V2tna7TM6oI3LHDyf/JBoXaQJBAMcVN7fKlYP"
                + "Skzfh/yZzW2fmC0ZNg/qaW8Oa/wfDxlWjgnS0p/EKWZ8BxjR/d199L3i/KMaGdfpaWbYZLvYENqUCQQCobjsuCW"
                + "nlZhcWajjzpsSuy8/bICVEpUax1fUZ58Mq69CQXfaZemD9Ar4omzuEAAs2/uee3kt3AvCBaeq05NyjAkBme8SwB0iK"
                + "kLcaeGuJlq7CQIkjSrobIqUEf+CzVZPe+AorG+isS+Cw2w/2bHu+G0p5xSYvdH59P0+ZT0N+f9LFAkA6v3Ae56OrI"
                + "wfMhrJksfeKbIaMjNLS9b8JynIaXg9iCiyOHmgkMl5gAbPoH/ULXqSKwzBw5mJ2GW1gBlyaSfV3AkA/RJC+adIjsRGg"
                + "JOkiRjSmPpGv3FOhl9fsBPjupZBEIuoMWOC8GXK/73DHxwmfNmN7C9+sIi4RBcjEeQ5F5FHZ";

        RSA rsa = new RSA(PRIVATE_KEY, null);

        String a = "2707F9FD4288CEF302C972058712F24A5F3EC62C5A14AD2FC59DAB93503AA0FA17113A020EE4EA35EB53F"
                + "75F36564BA1DABAA20F3B90FD39315C30E68FE8A1803B36C29029B23EB612C06ACF3A34BE815074F5EB5AA3A"
                + "C0C8832EC42DA725B4E1C38EF4EA1B85904F8B10B2D62EA782B813229F9090E6F7394E42E6F44494BB8";

        byte[] aByte = HexUtil.decodeHex(a);
        byte[] decrypt1 = rsa.decrypt(aByte, KeyType.PrivateKey);
        System.out.println("decrypt1 = " + StrUtil.str(decrypt1, CharsetUtil.CHARSET_UTF_8));
    }

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
