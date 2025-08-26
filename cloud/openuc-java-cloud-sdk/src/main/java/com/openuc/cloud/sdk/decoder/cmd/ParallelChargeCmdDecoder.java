package com.openuc.cloud.sdk.decoder.cmd;

import com.openuc.cloud.sdk.message.ApplicationMessage;
import com.openuc.cloud.sdk.message.CmdMessage;
import com.openuc.cloud.sdk.message.ParallelChargeCmdMessage;
import com.openuc.cloud.sdk.util.AsciiUtil;
import com.openuc.cloud.sdk.util.BCD;
import com.openuc.cloud.sdk.util.BCDUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 0xA1
 * 充电桩主动申请并充充电
 */
public class ParallelChargeCmdDecoder implements CmdDecoder {
    @Override
    public int getCmdCode() {
        return 0xA1;
    }

    @Override
    public CmdMessage process(ApplicationMessage message) {

        ByteBuf byteBuf = Unpooled.copiedBuffer(message.getMsgBody());

        //1.桩编码
        byte[] pileCodeB = new byte[7];
        byteBuf.readBytes(pileCodeB);
        String pileCode = BCD.bcdToStr(pileCodeB);

        ParallelChargeCmdMessage parallelChargeCmdMessage = new ParallelChargeCmdMessage(getCmdCode(), pileCode);

        //2.枪号
        byte[] gunCodeB = new byte[1];
        byteBuf.readBytes(gunCodeB);
        int gunCode = Integer.parseInt(BCD.bcdToStr(gunCodeB));
        parallelChargeCmdMessage.setGunCode(gunCode);

        //3.启动方式
        int startType = byteBuf.readUnsignedByte();
        parallelChargeCmdMessage.setStartType(startType);

        //4.是否需要密码
        int needPwd = byteBuf.readUnsignedByte();
        parallelChargeCmdMessage.setNeedPwd(needPwd);

        //5.物理卡号
        long chargeCardNo = byteBuf.readLongLE();
        parallelChargeCmdMessage.setChargeCardNo(chargeCardNo);

        //6.输入密码
        byte[] passwordB = new byte[16];
        byteBuf.readBytes(passwordB);
        String password = AsciiUtil.bytesToAscii(passwordB);
        parallelChargeCmdMessage.setPassword(password);

        //7.vin
        byte[] vinCodeB = new byte[17];
        byteBuf.readBytes(vinCodeB);
        String vinCode = AsciiUtil.bytesToAscii(vinCodeB);
        parallelChargeCmdMessage.setVinCode(vinCode);

        //8.主辅枪标记
        int mainType = byteBuf.readUnsignedByte();
        parallelChargeCmdMessage.setMainType(mainType);

        //9.并充序号
        byte[] parallelNoB = new byte[6];
        byteBuf.readBytes(parallelNoB);
        String parallelNo = BCDUtil.bcd2Str(parallelNoB);
        parallelChargeCmdMessage.setParallelNo(parallelNo);

        return parallelChargeCmdMessage;


    }
}
