package com.openuc.cloud.sdk.decoder.cmd;

import com.openuc.cloud.sdk.message.ApplicationMessage;
import com.openuc.cloud.sdk.message.CmdMessage;
import com.openuc.cloud.sdk.message.QueryChargeModelCmdMessage;
import com.openuc.cloud.sdk.util.BCD;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 0x09
 * 充电桩计费模型请求
 */
public class QueryChargeModelCmdDecoder implements CmdDecoder {
    @Override
    public int getCmdCode() {
        return 0x09;
    }

    @Override
    public CmdMessage process(ApplicationMessage message) {
        ByteBuf byteBuf = Unpooled.copiedBuffer(message.getMsgBody());

        //1.桩编码
        byte[] pileCodeB = new byte[7];
        byteBuf.readBytes(pileCodeB);
        String pileCode = BCD.bcdToStr(pileCodeB);

        QueryChargeModelCmdMessage queryModelCmdMessage = new QueryChargeModelCmdMessage(getCmdCode(), pileCode);
        return queryModelCmdMessage;
    }
}
