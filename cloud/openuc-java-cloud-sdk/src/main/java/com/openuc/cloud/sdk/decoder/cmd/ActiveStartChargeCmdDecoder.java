package com.openuc.cloud.sdk.decoder.cmd;

import com.openuc.cloud.sdk.message.ActiveStartChargeCmdMessage;
import com.openuc.cloud.sdk.message.ApplicationMessage;
import com.openuc.cloud.sdk.message.CmdMessage;
import com.openuc.cloud.sdk.util.AsciiUtil;
import com.openuc.cloud.sdk.util.BCD;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 0x31
 * 充电桩主动申请启动充电
 */
public class ActiveStartChargeCmdDecoder implements CmdDecoder {
    @Override
    public int getCmdCode() {
        return 0x31;
    }

    @Override
    public CmdMessage process(ApplicationMessage message) {
        ByteBuf byteBuf = Unpooled.copiedBuffer(message.getMsgBody());

        //1.桩编码
        byte[] pileCodeB = new byte[7];
        byteBuf.readBytes(pileCodeB);
        String pileCode = BCD.bcdToStr(pileCodeB);

        ActiveStartChargeCmdMessage activeStartChargeCmdMessage = new ActiveStartChargeCmdMessage(getCmdCode(), pileCode);

        //2.枪号
        byte[] gunCodeB = new byte[1];
        byteBuf.readBytes(gunCodeB);
        int gunCode = Integer.parseInt(BCD.bcdToStr(gunCodeB));
        activeStartChargeCmdMessage.setGunCode(gunCode);

        //3.启动方式
        int startType = byteBuf.readUnsignedByte();
        activeStartChargeCmdMessage.setStartType(startType);

        //4.是否需要密码
        int needPwd = byteBuf.readUnsignedByte();
        activeStartChargeCmdMessage.setNeedPwd(needPwd);

        //5.账号或者物理卡号
        long chargeCardNo = byteBuf.readLongLE();
        activeStartChargeCmdMessage.setChargeCardNo(chargeCardNo);

        //6.输入密码
        byte[] passwordB = new byte[16];
        byteBuf.readBytes(passwordB);
        String password = AsciiUtil.bytesToAscii(passwordB);
        activeStartChargeCmdMessage.setPassword(password);

        //7.vin码
        byte[] vinCodeB = new byte[17];
        byteBuf.readBytes(vinCodeB);
        String vinCode = AsciiUtil.bytesToAscii(vinCodeB);
        activeStartChargeCmdMessage.setVinCode(vinCode);

        return activeStartChargeCmdMessage;
    }
}
