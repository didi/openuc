package com.openuc.cloud.sdk.decoder.cmd;

import com.openuc.cloud.sdk.message.ApplicationMessage;
import com.openuc.cloud.sdk.message.BmsDemandOutputCmdMessage;
import com.openuc.cloud.sdk.message.CmdMessage;
import com.openuc.cloud.sdk.util.BCD;
import com.openuc.cloud.sdk.util.ChargeFlowSnUtil;
import com.openuc.cloud.sdk.util.NumberUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.math.BigDecimal;


/**
 * 0x23
 * 充电过程 BMS 需求与充电机输出
 */
public class BmsDemandOutputCmdDecoder implements CmdDecoder {
    @Override
    public int getCmdCode() {
        return 0x23;
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
        BmsDemandOutputCmdMessage bmsDemandOutputCmdMessage = new BmsDemandOutputCmdMessage(getCmdCode(), pileCode);
        bmsDemandOutputCmdMessage.setChargeFlowSn(chargeFlowSn);

        //3.枪号
        byte[] gunCodeB = new byte[1];
        byteBuf.readBytes(gunCodeB);
        int gunCode = Integer.parseInt(BCD.bcdToStr(gunCodeB));
        bmsDemandOutputCmdMessage.setGunCode(gunCode);

        //4.BMS 电压需求
        BigDecimal bmsDemandVoltage = NumberUtil.intToBigDecimalDivideMultiple(byteBuf.readUnsignedShortLE(), 10);
        bmsDemandOutputCmdMessage.setBmsDemandVoltage(bmsDemandVoltage);

        //5.BMS 电流需求
        BigDecimal bmsDemandCurrent = new BigDecimal(Math.abs(NumberUtil.intToBigDecimalDivideMultiple(byteBuf.readUnsignedShortLE(), 10).subtract(new BigDecimal(400)).doubleValue()));
        bmsDemandOutputCmdMessage.setBmsDemandCurrent(bmsDemandCurrent);

        //6.BMS 充电模式
        int bmsChargeModel = byteBuf.readUnsignedByte();
        bmsDemandOutputCmdMessage.setBmsChargeModel(bmsChargeModel);

        //7.BMS 充电电压测量值
        BigDecimal bmsChargeMeasureVoltage = NumberUtil.intToBigDecimalDivideMultiple(byteBuf.readUnsignedShortLE(), 10);
        bmsDemandOutputCmdMessage.setBmsChargeMeasureVoltage(bmsChargeMeasureVoltage);

        //8.BMS 充电电流测量值
        BigDecimal bmsChargeMeasureCurrent = new BigDecimal(Math.abs(NumberUtil.intToBigDecimalDivideMultiple(byteBuf.readUnsignedShortLE(), 10).subtract(new BigDecimal(400)).doubleValue()));
        bmsDemandOutputCmdMessage.setBmsChargeMeasureCurrent(bmsChargeMeasureCurrent);

        //9.BMS 最⾼单体动⼒蓄电池电压及组号
        int batteryVolAndSerialNoBytes = byteBuf.readUnsignedShortLE();
        bmsDemandOutputCmdMessage.setBatteryVolAndSerialNoBytes(batteryVolAndSerialNoBytes);

        //10.BMS 当前荷电状态SOC
        int bmsCurrentSoc = byteBuf.readUnsignedByte();
        bmsDemandOutputCmdMessage.setBmsCurrentSoc(bmsCurrentSoc);

        //11.BMS 估算剩余充电时间
        int bmsRemainingChargeTimeMin = byteBuf.readUnsignedShortLE();
        bmsDemandOutputCmdMessage.setBmsRemainingChargeTimeMin(bmsRemainingChargeTimeMin);

        //12.电桩电压输出值
        BigDecimal bmsPileOutputVoltage = NumberUtil.intToBigDecimalDivideMultiple(byteBuf.readUnsignedShortLE(), 10);
        bmsDemandOutputCmdMessage.setBmsPileOutputCurrent(bmsPileOutputVoltage);

        //13.电桩电流输出值
        BigDecimal bmsPileOutputCurrent = new BigDecimal(Math.abs(NumberUtil.intToBigDecimalDivideMultiple(byteBuf.readUnsignedShortLE(), 10).subtract(new BigDecimal(400)).doubleValue()));
        bmsDemandOutputCmdMessage.setBmsPileOutputCurrent(bmsPileOutputCurrent);

        //14.累计充电时间
        int chargeTimeMin = byteBuf.readUnsignedShortLE();
        bmsDemandOutputCmdMessage.setChargeTimeMin(chargeTimeMin);

        return bmsDemandOutputCmdMessage;
    }
}
