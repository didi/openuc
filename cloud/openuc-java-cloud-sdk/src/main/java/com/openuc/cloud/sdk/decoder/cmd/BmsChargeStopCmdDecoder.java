package com.openuc.cloud.sdk.decoder.cmd;

import com.openuc.cloud.sdk.message.ApplicationMessage;
import com.openuc.cloud.sdk.message.BmsChargeStopCmdMessage;
import com.openuc.cloud.sdk.message.CmdMessage;
import com.openuc.cloud.sdk.util.BCD;
import com.openuc.cloud.sdk.util.ChargeFlowSnUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 0x1D
 * 充电阶段 BMS 中⽌
 */
public class BmsChargeStopCmdDecoder implements CmdDecoder {
    @Override
    public int getCmdCode() {
        return 0x1D;
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

        BmsChargeStopCmdMessage bmsChargeStopCmdMessage = new BmsChargeStopCmdMessage(getCmdCode(), pileCode);
        bmsChargeStopCmdMessage.setChargeFlowSn(chargeFlowSn);

        //3.枪号
        byte[] gunCodeB = new byte[1];
        byteBuf.readBytes(gunCodeB);
        int gunCode = Integer.parseInt(BCD.bcdToStr(gunCodeB));
        bmsChargeStopCmdMessage.setGunCode(gunCode);

        //4.BMS 中⽌充电原因
        int bmsStopReason = byteBuf.readUnsignedByte();
        bmsChargeStopCmdMessage.setBmsStopReason(bmsStopReason);

        //5.BMS 中⽌充电故障原因
        int bmsStopFailReason = byteBuf.readUnsignedShortLE();
        bmsChargeStopCmdMessage.setBmsStopFailReason(bmsStopFailReason);

        //6.BMS 中⽌充电错误原因
        int bmsStopErrorReason = byteBuf.readUnsignedByte();
        bmsChargeStopCmdMessage.setBmsStopErrorReason(bmsStopErrorReason);

        return bmsChargeStopCmdMessage;

    }
}
