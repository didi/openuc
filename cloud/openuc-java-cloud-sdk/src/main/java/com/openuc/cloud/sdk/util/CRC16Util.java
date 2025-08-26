package com.openuc.cloud.sdk.util;

/**
 * CRC 校验和工具类
 * @author fangshun
 */
public class CRC16Util {
    /**
     * CRC 校验和计算
     * @param bytes 待校验数据
     * @return 数据CRC校验和值
     */
    public static int modbusCRC(byte[] bytes) {
        int CRC = 0x0000ffff;
        int POLYNOMIAL = 0x0000a001;
        int i, j;
        for (i = 0; i < bytes.length; i++) {
            CRC ^= (int) bytes[i] &0x000000ff;
            for (j = 0; j < 8; j++) {
                if ((CRC & 0x00000001) != 0) {
                    CRC >>= 1;
                    CRC ^= POLYNOMIAL;
                } else {
                    CRC >>= 1;
                }
            }
        }
        return  CRC;
    }
}
