package com.openuc.cloud.sdk.util;

import com.openuc.cloud.sdk.util.CRC16Util;

/**
 * 校验和验证工具类
 * @author fangshun
 */
public class CheckSumUtil {
    /**
     * 计算校验和
     * @param data 待校验数据
     * @return 校验和值
     */
    public static int crcSum(byte[] data) {
        return  CRC16Util.modbusCRC(data);
    }

    /**
     * 校验校验和
     * @param data 待校验数据
     * @param checkSum 校验和值
     * @return 校验结果：true 校验成功，false 校验失败
     */
    public static boolean checkCrcSum(byte[] data, int checkSum) {
        return crcSum(data) == checkSum;
    }
}
