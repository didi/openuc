package com.openuc.cloud.sdk.decoder.cmd;

import com.openuc.cloud.sdk.message.ApplicationMessage;
import com.openuc.cloud.sdk.message.CmdMessage;
import com.openuc.cloud.sdk.message.ReplyQrCodeCmdMessage;
import com.openuc.cloud.sdk.util.BCD;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 0xF1
 * 桩应答远程下发⼆维码前缀指令
 */
public class ReplyQrCodeCmdDecoder implements CmdDecoder {
    @Override
    public int getCmdCode() {
        return 0xF1;
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

        ReplyQrCodeCmdMessage replyQrCodeCmdMessage = new ReplyQrCodeCmdMessage(getCmdCode(), pileCode);
        replyQrCodeCmdMessage.setResult(result);

        return replyQrCodeCmdMessage;
    }
}
