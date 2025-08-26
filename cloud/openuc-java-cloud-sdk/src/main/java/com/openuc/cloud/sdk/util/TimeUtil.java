package com.openuc.cloud.sdk.util;

import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
    /**
     * CP56time2a
     *
     * @param bytes
     * @return
     */
    public static Date cp56time2aToDate(byte[] bytes) {
        int milliseconds1 = bytes[0] < 0 ? 256 + bytes[0] : bytes[0];
        int milliseconds2 = bytes[1] < 0 ? 256 + bytes[1] : bytes[1];
        int milliseconds = milliseconds1 + milliseconds2 * 256;
        // 位于 0011 1111
        int minutes = bytes[2] & 0x3f;
        // 位于 0001 1111
        int hours = bytes[3] & 0x1f;
        // 位于 0000 1111
        int days = bytes[4] & 0x1f;
        // 位于 0001 1111
        int months = bytes[5] & 0x0f;
        // 位于 0111 1111
        int years = bytes[6] & 0x7f;
        final Calendar aTime = Calendar.getInstance();
        aTime.set(Calendar.SECOND, milliseconds/1000);
        aTime.set(Calendar.MINUTE, minutes);
        aTime.set(Calendar.HOUR_OF_DAY, hours);
        aTime.set(Calendar.DAY_OF_MONTH, days);
        aTime.set(Calendar.MONTH, months - 1);
        aTime.set(Calendar.YEAR, years + 2000);
        return aTime.getTime();
    }

    /**
     * CP56time2a
     *
     * @param aDate
     * @return
     */
    public static byte[] cp56time2aToBytes(Date aDate) {
        byte[] result = new byte[7];
        final Calendar aTime = Calendar.getInstance();
        aTime.setTime(aDate);
        final int milliseconds = aTime.get(Calendar.SECOND) * 1000;
        result[0] = (byte) (milliseconds % 256);
        result[1] = (byte) (milliseconds / 256);
        result[2] = (byte) aTime.get(Calendar.MINUTE);
        result[3] = (byte) aTime.get(Calendar.HOUR_OF_DAY);
        result[4] = (byte) aTime.get(Calendar.DAY_OF_MONTH);
        result[5] = (byte) (aTime.get(Calendar.MONTH) + 1);
        result[6] = (byte) (aTime.get(Calendar.YEAR) % 100);
        return result;
    }

}
