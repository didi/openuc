package com.openuc.cloud.sdk.decoder.cmd;

import com.openuc.cloud.sdk.message.ApplicationMessage;
import com.openuc.cloud.sdk.message.BmsPileStopCmdMessage;
import com.openuc.cloud.sdk.message.CmdMessage;
import com.openuc.cloud.sdk.util.BCD;
import com.openuc.cloud.sdk.util.ChargeFlowSnUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.math.BigDecimal;

/**
 * 0x21
 * 充电结束⽌
 */
public class BmsPileStopCmdDecoder implements CmdDecoder {
    @Override
    public int getCmdCode() {
        return 0x21;
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

        BmsPileStopCmdMessage bmsPileStopCmdMessage = new BmsPileStopCmdMessage(getCmdCode(), pileCode);
        bmsPileStopCmdMessage.setChargeFlowSn(chargeFlowSn);

        //3.枪号
        byte[] gunCodeB = new byte[1];
        byteBuf.readBytes(gunCodeB);
        int gunCode = Integer.parseInt(BCD.bcdToStr(gunCodeB));
        bmsPileStopCmdMessage.setGunCode(gunCode);

        //4.充电机终止充电原因
        int bmsPileChargeStopReason = byteBuf.readUnsignedByte();
        bmsPileStopCmdMessage.setBmsPileChargeStopReason(bmsPileChargeStopReason);

        //5.充电机中⽌充电故障原因
        int bmsPileChargeStopFailReason = byteBuf.readUnsignedShortLE();
        bmsPileStopCmdMessage.setBmsPileChargeStopFailReason(bmsPileChargeStopFailReason);

        //6.充电机中⽌充电错误原因
        int bmsPileChargeStopErrorReason = byteBuf.readUnsignedByte();
        bmsPileStopCmdMessage.setBmsPileChargeStopErrorReason(bmsPileChargeStopErrorReason);

        return bmsPileStopCmdMessage;

    }
}
