package com.openuc.cloud.sdk.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtil {
    public static BigDecimal intToBigDecimalDivideMultiple(long value, int multiple) {
        return new BigDecimal(value).divide(new BigDecimal(multiple), 3, RoundingMode.HALF_UP);
    }

    public static BigDecimal intToBigDecimalDivideMultiple(long value, int multiple, int scale) {
        return new BigDecimal(value).divide(new BigDecimal(multiple), scale, RoundingMode.HALF_UP);
    }

    public static int parse2Int(String str){
        try {
            return Integer.parseInt(str);
        } catch (Exception e){
            return 0;
        }
    }

    public static String intToBitEightStr(int i) {
        String bitStr = Integer.toBinaryString(i);
        return String.format("%08d", Integer.parseInt(bitStr));
    }
}
