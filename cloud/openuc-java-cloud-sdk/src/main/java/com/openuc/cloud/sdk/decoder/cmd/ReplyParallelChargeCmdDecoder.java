package com.openuc.cloud.sdk.decoder.cmd;

import com.openuc.cloud.sdk.message.ApplicationMessage;
import com.openuc.cloud.sdk.message.CmdMessage;
import com.openuc.cloud.sdk.message.ReplyParallelChargeCmdMessage;
import com.openuc.cloud.sdk.util.BCD;
import com.openuc.cloud.sdk.util.BCDUtil;
import com.openuc.cloud.sdk.util.ChargeFlowSnUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 0xA3
 * 远程并充启机命令回复
 */
public class ReplyParallelChargeCmdDecoder implements CmdDecoder {
    @Override
    public int getCmdCode() {
        return 0xA3;
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

        ReplyParallelChargeCmdMessage replyParallelChargeCmdMessage = new ReplyParallelChargeCmdMessage(getCmdCode(), pileCode);
        replyParallelChargeCmdMessage.setChargeFlowSn(chargeFlowSn);

        //3.枪号
        byte[] gunCodeB = new byte[1];
        byteBuf.readBytes(gunCodeB);
        int gunCode = Integer.parseInt(BCD.bcdToStr(gunCodeB));
        replyParallelChargeCmdMessage.setGunCode(gunCode);

        //4.结果
        int result = byteBuf.readUnsignedByte();
        replyParallelChargeCmdMessage.setResult(result);

        //5.失败原因
        int failReason = byteBuf.readUnsignedByte();
        replyParallelChargeCmdMessage.setFailReason(failReason);

        //6.主辅枪标记
        int mainType = byteBuf.readUnsignedByte();
        replyParallelChargeCmdMessage.setMainType(mainType);

        //7.并充序号
        byte[] parallelNoB = new byte[6];
        byteBuf.readBytes(parallelNoB);
        String parallelNo = BCDUtil.bcd2Str(parallelNoB);
        replyParallelChargeCmdMessage.setParallelNo(parallelNo);

        return replyParallelChargeCmdMessage;

    }
}
