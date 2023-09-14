package com.yuyun;

import cn.hutool.core.codec.Base32;
import cn.hutool.core.codec.Base58;
import cn.hutool.core.codec.Base62;
import cn.hutool.core.codec.Base64;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

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
    void encrypt(){
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
    void Decrypt(){
        String data = "13zzaWBGTA";
        String decodeStr = Base62.decodeStr(data);
        System.out.println("decodeStr = " + decodeStr);
    }
}
