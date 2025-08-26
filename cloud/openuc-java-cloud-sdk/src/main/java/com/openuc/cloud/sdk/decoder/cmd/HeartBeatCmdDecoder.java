package com.openuc.cloud.sdk.decoder.cmd;

import com.openuc.cloud.sdk.message.ApplicationMessage;
import com.openuc.cloud.sdk.message.CmdMessage;
import com.openuc.cloud.sdk.message.HeartbeatCmdMessage;
import com.openuc.cloud.sdk.util.BCD;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;


/**
 * 0x03
 * 充电桩心跳包
 */
public class HeartBeatCmdDecoder implements CmdDecoder{
    @Override
    public int getCmdCode() {
        return 0x03;
    }

    @Override
    public CmdMessage process(ApplicationMessage message) {
        ByteBuf byteBuf = Unpooled.copiedBuffer(message.getMsgBody());

        //1.桩编码
        byte[] pileCodeB = new byte[7];
        byteBuf.readBytes(pileCodeB);
        String pileCode = BCD.bcdToStr(pileCodeB);

        HeartbeatCmdMessage heartbeatCmdMessage = new HeartbeatCmdMessage(getCmdCode(),pileCode);

        //2.枪号
        byte[] gunCodeB = new byte[1];
        byteBuf.readBytes(gunCodeB);
        int gunCode = Integer.parseInt(BCD.bcdToStr(gunCodeB));
        heartbeatCmdMessage.setGunCode(gunCode);

        //3.枪状态
        int gunState = byteBuf.readUnsignedByte();
        heartbeatCmdMessage.setGunState(gunState);

        return heartbeatCmdMessage;
    }
}
