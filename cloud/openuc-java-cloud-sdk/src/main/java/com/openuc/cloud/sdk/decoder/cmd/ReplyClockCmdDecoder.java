package com.openuc.cloud.sdk.decoder.cmd;

import com.openuc.cloud.sdk.message.ApplicationMessage;
import com.openuc.cloud.sdk.message.CmdMessage;
import com.openuc.cloud.sdk.message.ReplyClockCmdMessage;
import com.openuc.cloud.sdk.util.BCD;
import com.openuc.cloud.sdk.util.TimeUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Date;



/**
 * 0x55
 * 对时设置应答
 */
public class ReplyClockCmdDecoder implements CmdDecoder{
    @Override
    public int getCmdCode() {
        return 0x55;
    }

    @Override
    public CmdMessage process(ApplicationMessage message) {
        ByteBuf byteBuf = Unpooled.copiedBuffer(message.getMsgBody());

        //1.桩编码
        byte[] pileCodeB = new byte[7];
        byteBuf.readBytes(pileCodeB);
        String pileCode = BCD.bcdToStr(pileCodeB);

        byte[] clockTime = new byte[7];
        byteBuf.readBytes(clockTime);
        Date curTime = TimeUtil.cp56time2aToDate(clockTime);

        ReplyClockCmdMessage replyClockCmdMessage = new ReplyClockCmdMessage(getCmdCode(),pileCode);
        replyClockCmdMessage.setCurTime(curTime);

        return replyClockCmdMessage;
    }
}
