package com.yuyun.utils;

import sun.misc.BASE64Decoder;

import java.io.IOException;

/**
 * Base64工具类
 *
 * @author hyh
 */
public class Base64Utils {

    /**
     * Base64解码
     *
     * @param bytes Base64加密的字节码
     * @return String
     * @throws IOException IOException
     */
    public static String Base64Decode(byte[] bytes)
            throws IOException {
        BASE64Decoder base64decoder = new BASE64Decoder();
        byte[] bs = base64decoder.decodeBuffer(new String(bytes));
        return new String(bs, "UTF-8");
    }

}
