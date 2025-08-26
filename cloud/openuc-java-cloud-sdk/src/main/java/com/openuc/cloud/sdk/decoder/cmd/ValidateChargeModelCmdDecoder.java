package com.openuc.cloud.sdk.decoder.cmd;

import com.openuc.cloud.sdk.message.ApplicationMessage;
import com.openuc.cloud.sdk.message.CmdMessage;
import com.openuc.cloud.sdk.message.ValidateChargeModelCmdMessage;
import com.openuc.cloud.sdk.util.BCD;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 0x05
 * 计费模型验证请求
 */
public class ValidateChargeModelCmdDecoder implements CmdDecoder{
    @Override
    public int getCmdCode() {
        return 0x05;
    }

    @Override
    public CmdMessage process(ApplicationMessage message) {
        ByteBuf byteBuf = Unpooled.copiedBuffer(message.getMsgBody());

        //1.桩编码
        byte[] pileCodeB = new byte[7];
        byteBuf.readBytes(pileCodeB);
        String pileCode = BCD.bcdToStr(pileCodeB);

        ValidateChargeModelCmdMessage validateModelCmdMessage = new ValidateChargeModelCmdMessage(getCmdCode(),pileCode);

        //2.计费模型编号
        byte[] feeModelB = new byte[7];
        byteBuf.readBytes(feeModelB);
        String feeModelNo = BCD.bcdToStr(feeModelB);
        validateModelCmdMessage.setFeeModelNo(feeModelNo);

        return validateModelCmdMessage;
    }
}
