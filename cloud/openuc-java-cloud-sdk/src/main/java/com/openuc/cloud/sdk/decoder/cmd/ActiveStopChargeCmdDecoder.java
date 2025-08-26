package com.openuc.cloud.sdk.decoder.cmd;

import com.openuc.cloud.sdk.message.ApplicationMessage;
import com.openuc.cloud.sdk.message.CmdMessage;
import com.openuc.cloud.sdk.message.StopChargeCmdMessage;
import com.openuc.cloud.sdk.util.BCD;
import com.openuc.cloud.sdk.util.ChargeFlowSnUtil;
import com.openuc.cloud.sdk.util.NumberUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.math.BigDecimal;


/**
 * 0x19
 * 充电结束
 */
public class ActiveStopChargeCmdDecoder implements CmdDecoder {
    @Override
    public int getCmdCode() {
        return 0x19;
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

        StopChargeCmdMessage stopChargeCmdMessage = new StopChargeCmdMessage(getCmdCode(), pileCode);
        stopChargeCmdMessage.setChargeFlowSn(chargeFlowSn);

        //3.枪号
        byte[] gunCodeB = new byte[1];
        byteBuf.readBytes(gunCodeB);
        int gunNo = Integer.parseInt(BCD.bcdToStr(gunCodeB));
        stopChargeCmdMessage.setGunCode(gunNo);

        //4.BMS 中⽌荷电状态SOC
        int bmsStopSoc = byteBuf.readUnsignedByte();
        stopChargeCmdMessage.setBmsStopSoc(bmsStopSoc);

        //5.BMS 动⼒蓄电池单体最低电压
        BigDecimal bmsBatterySingleMinVoltage = NumberUtil.intToBigDecimalDivideMultiple(byteBuf.readUnsignedShortLE(), 100);
        stopChargeCmdMessage.setBmsBatterySingleMinVoltage(bmsBatterySingleMinVoltage);

        //6.BMS 动⼒蓄电池单体最⾼电压
        int bmsBatterySingleMaxVoltage = Math.abs(byteBuf.readUnsignedShortLE() - 50);
        stopChargeCmdMessage.setBmsBatterySingleMaxVoltage(bmsBatterySingleMaxVoltage);

        //7.BMS 动⼒蓄电池最低温度
        int bmsBatteryMinTemperature = Math.abs(byteBuf.readUnsignedShortLE() - 50);
        stopChargeCmdMessage.setBmsBatteryMinTemperature(bmsBatteryMinTemperature);

        //8.BMS 动⼒蓄电池最⾼温度
        int bmsBatteryMaxTemperature = Math.abs(byteBuf.readUnsignedShortLE() - 50);
        stopChargeCmdMessage.setBmsBatteryMaxTemperature(bmsBatteryMaxTemperature);

        //9.电桩累计充电时间
        int bmsTotalChargeTimeMin = byteBuf.readUnsignedShortLE();
        stopChargeCmdMessage.setBmsTotalChargeTimeMin(bmsTotalChargeTimeMin);

        //10.电桩输出能量
        BigDecimal bmsOutputPower = NumberUtil.intToBigDecimalDivideMultiple(byteBuf.readUnsignedShortLE(),10);
        stopChargeCmdMessage.setBmsOutputPower(bmsOutputPower);

        //11.电桩充电机编号
        long bmsPileNumber = byteBuf.readUnsignedIntLE();
        stopChargeCmdMessage.setBmsPileNumber(bmsPileNumber);

        return stopChargeCmdMessage;

    }
}
