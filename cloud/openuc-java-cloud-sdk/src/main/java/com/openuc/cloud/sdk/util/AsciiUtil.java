package com.openuc.cloud.sdk.util;

import io.netty.buffer.ByteBuf;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author fangshun
 */
public class AsciiUtil {
    public static String bytesToAscii(byte[] bytes) {
        return new String(bytes, StandardCharsets.US_ASCII).trim();
    }

    public static byte[] asciiToBytes(String ascii, int len) {
        byte[] b = new byte[len];
        byte[] bytes = ascii.getBytes(StandardCharsets.US_ASCII);
        if (bytes.length <= len) {
            System.arraycopy(bytes, 0, b, 0, bytes.length);
        }
        return b;
    }

    public static String bytesToAscii(ByteBuf buf, int len) {
        byte[] bytes = new byte[len];
        buf.readBytes(bytes);
        return bytesToAscii(bytes);
    }

    public static String bytesToAsciiGBK(ByteBuf buf, int len) {
        byte[] bytes = new byte[len];
        buf.readBytes(bytes);
        return new String(bytes, Charset.forName("GBK")).trim();
    }
}
