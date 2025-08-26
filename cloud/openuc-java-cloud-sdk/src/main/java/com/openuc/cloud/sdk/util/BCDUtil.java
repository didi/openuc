package com.openuc.cloud.sdk.util;

import io.netty.buffer.ByteBuf;

/**
 * BCD压缩工具类
 * @author fangshun
 */
public class BCDUtil {

    /**
     * BCD压缩把bytes转成String
     * @param bytes byte数组
     * @return
     */
    public static String bytes2String(byte[] bytes) {
        return bytes2String(bytes, true);
    }


    /**
     * BCD压缩把bytes转成String，移除前置的0
     * @param bytes byte数组
     * @return
     */
    public static String bytes2String(byte[] bytes, boolean removeZeros) {
        String str = BCD.bcdToStr(bytes);
        if (removeZeros) {
            str = str.replaceFirst("^0*","");
        }
        return str;
    }

    /**
     * 字符串转bcd，高位补0
     * @param inputString
     * @return
     */
    public static void str2Bytes(String inputString, byte[] dest) {
        int len = dest.length;
        byte[] bcd = BCD.strToBcd(inputString);

        int bcdLen = bcd.length;

        // 解析出来的长度大于了给定长度，返回全0
        if (bcdLen > len) {
            return;
        }
        System.arraycopy(bcd, 0, dest, len-bcdLen, bcdLen);
    }

    public static byte[] strToBCDAndFillLeft(String str, int length, String left) {
        String targetStr = str;
        if (str.length() < length) {
            int size = length - str.length();
            for (int i=0; i<size; i++) {
                targetStr = left + targetStr;
            }
        }

        return BCD.strToBcd(targetStr);
    }

    public static String readBytes2BcdStr(ByteBuf buf, int bytesLength) {
        byte[] bytes = new byte[bytesLength];
        buf.readBytes(bytes);
        return BCD.bcdToStr(bytes);
    }

    public static byte[] str2bcd(String str) {
        return BCD.strToBcd(str);
    }

    public static String bcd2Str(byte[] bcdBytes) {
        return BCD.bcdToStr(bcdBytes);
    }


}
