package com.openuc.cloud.sdk.decoder.cmd;

import com.openuc.cloud.sdk.message.ApplicationMessage;
import com.openuc.cloud.sdk.message.CmdMessage;
import com.openuc.cloud.sdk.message.ReplyStartChargeCmdMessage;
import com.openuc.cloud.sdk.util.BCD;
import com.openuc.cloud.sdk.util.ChargeFlowSnUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 0x33
 * 远程启动充电命令回复
 */
public class ReplyStartChargeCmdDecoder implements CmdDecoder {
    @Override
    public int getCmdCode() {
        return 0x33;
    }

    @Override
    public CmdMessage process(ApplicationMessage message) {
        ByteBuf byteBuf = Unpooled.copiedBuffer(message.getMsgBody());

        //1.交易流水号
        byte[] chargeFlowSnB = new byte[16];
        byteBuf.readBytes(chargeFlowSnB);
        String chargeFlowSn = ChargeFlowSnUtil.convertTradeOrderNo(BCD.bcdToStr(chargeFlowSnB));

        //2.桩编码
        byte[] pileCodeB = new byte[7];
        byteBuf.readBytes(pileCodeB);
        String pileCode = BCD.bcdToStr(pileCodeB);

        ReplyStartChargeCmdMessage replyStartChargeCmdMessage = new ReplyStartChargeCmdMessage(getCmdCode(),pileCode);
        replyStartChargeCmdMessage.setChargeFlowSn(chargeFlowSn);

        //3.枪号
        byte[] gunCodeB = new byte[1];
        byteBuf.readBytes(gunCodeB);
        int gunNo = Integer.parseInt(BCD.bcdToStr(gunCodeB));
        replyStartChargeCmdMessage.setGunCode(gunNo);

        //4.启动结果
        int cmdResult = byteBuf.readUnsignedByte();
        replyStartChargeCmdMessage.setCmdResult(cmdResult);

        //5.失败原因
        int failReason = byteBuf.readUnsignedByte();
        replyStartChargeCmdMessage.setFailReason(failReason);

        return replyStartChargeCmdMessage;

    }
}
