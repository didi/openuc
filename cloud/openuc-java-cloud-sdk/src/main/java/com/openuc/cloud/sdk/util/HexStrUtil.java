package com.openuc.cloud.sdk.util;


public class HexStrUtil {

    public static long littleEndian5BytesToLong(byte[] bytes) {
        if (bytes.length != 5) {
            throw new IllegalArgumentException("字节数组长度必须为 5");
        }
        // 按小端顺序组合字节
        return ((long) (bytes[0] & 0xFF)) |
                ((long) (bytes[1] & 0xFF) << 8) |
                ((long) (bytes[2] & 0xFF) << 16) |
                ((long) (bytes[3] & 0xFF) << 24) |
                ((long) (bytes[4] & 0xFF) << 32);
    }

}
