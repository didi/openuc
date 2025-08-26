package com.openuc.cloud.sdk.util;

import static com.openuc.cloud.sdk.util.HexStrUtil.littleEndian5BytesToLong;

/**
 * @author fangshun
 */
public class HexStrUtilTest {

    public void test() {
        byte[] littleEndianBytes = {0x01, 0x02, 0x03, 0x04, 0x05}; // 示例数据（小端）
        long value = littleEndian5BytesToLong(littleEndianBytes);
        System.out.println("解析结果（十进制）: " + value);
        System.out.println("解析结果（十六进制）: 0x" + Long.toHexString(value));
    }
}
