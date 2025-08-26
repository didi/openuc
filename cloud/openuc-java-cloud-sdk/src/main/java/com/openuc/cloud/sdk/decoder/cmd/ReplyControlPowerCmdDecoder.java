package com.openuc.cloud.sdk.decoder.cmd;

import com.openuc.cloud.sdk.message.ApplicationMessage;
import com.openuc.cloud.sdk.message.CmdMessage;
import com.openuc.cloud.sdk.message.ReplyControlPowerCmdMessage;
import com.openuc.cloud.sdk.util.BCD;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;


/**
 * 0x51
 * 充电桩⼯作参数设置应答
 */
public class ReplyControlPowerCmdDecoder implements CmdDecoder {
    @Override
    public int getCmdCode() {
        return 0x51;
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

        ReplyControlPowerCmdMessage replyControlPowerCmdMessage = new ReplyControlPowerCmdMessage(getCmdCode(), pileCode);
        replyControlPowerCmdMessage.setResult(result);

        return replyControlPowerCmdMessage;

    }
}
