package com.openuc.cloud.sdk.encoder;

import com.openuc.cloud.sdk.message.ApplicationMessage;
import com.openuc.cloud.sdk.util.CheckSumUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author fangshun
 */
public class ProtocolEncoder {

    public static  byte[] encode(ApplicationMessage message){
        ByteBuf response = Unpooled.buffer(message.getMsgBody().length + 8);
        response.writeByte(message.getStartFlag());
        response.writeByte(message.getMsgBody().length + 4);
        response.writeShortLE(message.getSeqNo());
        response.writeByte(message.getEncryptionFlag());
        response.writeByte(message.getCmdCode());
        response.writeBytes(message.getMsgBody());

        byte[] checkArr = new byte[message.getMsgBody().length + 4];
        System.arraycopy(response.array(), 2, checkArr, 0, checkArr.length);

        response.writeShortLE(CheckSumUtil.crcSum(checkArr));
        return response.array();
    }
}
