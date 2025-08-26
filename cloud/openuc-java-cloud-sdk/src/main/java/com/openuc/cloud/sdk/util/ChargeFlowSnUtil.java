package com.openuc.cloud.sdk.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ChargeFlowSnUtil {

    public static String convertTradeOrderNo(String chargeFlowSn) {
        int startIndex = 0;
        for (int i = 0; i < chargeFlowSn.length(); i++) {
            if (chargeFlowSn.charAt(i) != '0') {
                startIndex = i;
                break;
            }
        }

        return chargeFlowSn.substring(startIndex);
    }

}
