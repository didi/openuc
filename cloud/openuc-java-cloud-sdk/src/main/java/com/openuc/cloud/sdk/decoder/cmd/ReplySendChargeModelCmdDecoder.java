package com.openuc.cloud.sdk.decoder.cmd;

import com.openuc.cloud.sdk.message.ApplicationMessage;
import com.openuc.cloud.sdk.message.CmdMessage;
import com.openuc.cloud.sdk.message.ReplySendChargeModelCmdMessage;
import com.openuc.cloud.sdk.util.BCD;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;


/**
 * 0x57
 * 计费模型应答
 */
public class ReplySendChargeModelCmdDecoder implements CmdDecoder {
    @Override
    public int getCmdCode() {
        return 0x57;
    }

    @Override
    public CmdMessage process(ApplicationMessage message) {
        ByteBuf byteBuf = Unpooled.copiedBuffer(message.getMsgBody());

        //1.桩编码
        byte[] pileCodeB = new byte[7];
        byteBuf.readBytes(pileCodeB);
        String pileCode = BCD.bcdToStr(pileCodeB);

        //2.结果
        int result = byteBuf.readUnsignedByte();

        ReplySendChargeModelCmdMessage replySendChargeModelCmdMessage = new ReplySendChargeModelCmdMessage(getCmdCode(), pileCode);
        replySendChargeModelCmdMessage.setResult(result);

        return replySendChargeModelCmdMessage;

    }
}
