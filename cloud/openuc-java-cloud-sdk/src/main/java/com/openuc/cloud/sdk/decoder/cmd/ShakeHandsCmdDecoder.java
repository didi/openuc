package com.openuc.cloud.sdk.decoder.cmd;

import com.openuc.cloud.sdk.message.ApplicationMessage;
import com.openuc.cloud.sdk.message.CmdMessage;
import com.openuc.cloud.sdk.message.ShakeHandsCmdMessage;
import com.openuc.cloud.sdk.util.AsciiUtil;
import com.openuc.cloud.sdk.util.BCD;
import com.openuc.cloud.sdk.util.ChargeFlowSnUtil;
import com.openuc.cloud.sdk.util.NumberUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.math.BigDecimal;

/**
 * 0x15
 * 充电握手
 */
public class ShakeHandsCmdDecoder implements CmdDecoder {
    @Override
    public int getCmdCode() {
        return 0x15;
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

        ShakeHandsCmdMessage shakeHandsCmdMessage = new ShakeHandsCmdMessage(getCmdCode(), pileCode);
        shakeHandsCmdMessage.setChargeFlowSn(chargeFlowSn);

        //3.枪号
        byte[] gunCodeB = new byte[1];
        byteBuf.readBytes(gunCodeB);
        int gunNo = Integer.parseInt(BCD.bcdToStr(gunCodeB));
        shakeHandsCmdMessage.setGunNo(gunNo);

        //4.BMS 通信协议版本号
        byte[] bmsConnectVersionB = new byte[3];
        byteBuf.readBytes(bmsConnectVersionB);
        int bmsConnectVersion = Integer.parseInt(BCD.bcdToStr(bmsConnectVersionB));
        shakeHandsCmdMessage.setBmsConnectVersion(bmsConnectVersion);

        //5.BMS 电池类型
        int bmsBatteryType = byteBuf.readUnsignedByte();
        shakeHandsCmdMessage.setBmsBatteryType(bmsBatteryType);

        //6.BMS 整⻋动⼒蓄电池系统额定容量
        BigDecimal bmsPowerCapacity = NumberUtil.intToBigDecimalDivideMultiple(byteBuf.readUnsignedShortLE(), 100);
        shakeHandsCmdMessage.setBmsPowerCapacity(bmsPowerCapacity);

        //7.BMS 整⻋动⼒蓄电池系统额定 总电压
        BigDecimal bmsPowerMaxVoltage = NumberUtil.intToBigDecimalDivideMultiple(byteBuf.readUnsignedShortLE(), 100);
        shakeHandsCmdMessage.setBmsPowerMaxVoltage(bmsPowerMaxVoltage);

        //8.BMS 电池⽣产⼚商名称
        byte[] bmsFactoryB = new byte[4];
        byteBuf.readBytes(bmsFactoryB);
        String bmsFactory = AsciiUtil.bytesToAscii(bmsFactoryB);
        shakeHandsCmdMessage.setBmsFactory(bmsFactory);

        //9.BMS 电池组序号
        long bmsSerialNo = byteBuf.readUnsignedIntLE();
        shakeHandsCmdMessage.setBmsSerialNo(bmsSerialNo);

        //10.BMS 电池组⽣产⽇期年
        int bmsCreateYear = byteBuf.readUnsignedByte();
        shakeHandsCmdMessage.setBmsCreateYear(bmsCreateYear);

        //11.BMS 电池组⽣产⽇期⽉
        int bmsCreateMonth = byteBuf.readUnsignedByte();
        shakeHandsCmdMessage.setBmsCreateMonth(bmsCreateMonth);

        //12.BMS 电池组⽣产⽇期⽇
        int bmsCreateDay = byteBuf.readUnsignedByte();
        shakeHandsCmdMessage.setBmsCreateDay(bmsCreateDay);

        //13.BMS 电池组充电次数
        int bmsChargeCount = byteBuf.readUnsignedMediumLE();
        shakeHandsCmdMessage.setBmsChargeCount(bmsChargeCount);

        //14.BMS 电池组产权标识
        int bmsPropertyRightLabel = byteBuf.readUnsignedByte();
        shakeHandsCmdMessage.setBmsPropertyRightLabel(bmsPropertyRightLabel);

        //15.预留位
        int reserved = byteBuf.readUnsignedByte();
        shakeHandsCmdMessage.setReserved(reserved);

        //16.BMS ⻋辆识别码
        byte[] carVinB = new byte[17];
        byteBuf.readBytes(carVinB);
        String carVin = AsciiUtil.bytesToAscii(carVinB);
        shakeHandsCmdMessage.setCarVin(carVin);

        //17.BMS 软件版本号
        long bmsSoftVersionB = byteBuf.readLongLE();
        shakeHandsCmdMessage.setBmsSoftVersionB(bmsSoftVersionB);

        return shakeHandsCmdMessage;
    }
}
