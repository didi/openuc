package com.openuc.cloud.sdk.decoder.cmd;

import com.openuc.cloud.sdk.message.ApplicationMessage;
import com.openuc.cloud.sdk.message.CmdMessage;
import com.openuc.cloud.sdk.message.FixedRealTimeDataCmdMessage;
import com.openuc.cloud.sdk.util.BCD;
import com.openuc.cloud.sdk.util.ChargeFlowSnUtil;
import com.openuc.cloud.sdk.util.NumberUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.math.BigDecimal;

/**
 * 0x13
 * 上传实时监测数据
 */
public class FixedRealTimeDataCmdDecoder implements CmdDecoder {
    @Override
    public int getCmdCode() {
        return 0x13;
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

        FixedRealTimeDataCmdMessage fixedRealTimeDataCmdMessage = new FixedRealTimeDataCmdMessage(getCmdCode(), pileCode);
        fixedRealTimeDataCmdMessage.setChargeFlowSn(chargeFlowSn);

        //3.枪号
        byte[] gunCodeB = new byte[1];
        byteBuf.readBytes(gunCodeB);
        int gunCode = Integer.parseInt(BCD.bcdToStr(gunCodeB));
        fixedRealTimeDataCmdMessage.setGunCode(gunCode);

        //4.状态
        int gunState = byteBuf.readUnsignedByte();
        fixedRealTimeDataCmdMessage.setGunState(gunState);

        //5.枪是否归位
        int gunPlace = byteBuf.readUnsignedByte();
        fixedRealTimeDataCmdMessage.setGunPlace(gunPlace);

        //6.是否插枪
        int isPutGun = byteBuf.readUnsignedByte();
        fixedRealTimeDataCmdMessage.setIsPutGun(isPutGun);

        //7.输出电压
        BigDecimal outputVoltage = NumberUtil.intToBigDecimalDivideMultiple(byteBuf.readUnsignedShortLE(), 10);
        fixedRealTimeDataCmdMessage.setOutputVoltage(outputVoltage);

        //8.输出电流
        BigDecimal outputCurrent = NumberUtil.intToBigDecimalDivideMultiple(byteBuf.readUnsignedShortLE(), 10);
        fixedRealTimeDataCmdMessage.setOutputCurrent(outputCurrent);

        //9.枪线温度
        int gunLineTemperature = Math.abs(byteBuf.readUnsignedByte() - 50);
        fixedRealTimeDataCmdMessage.setGunLineTemperature(gunLineTemperature);

        //10.枪线编码
        long gunLineCode = byteBuf.readLongLE();
        fixedRealTimeDataCmdMessage.setGunLineCode(gunLineCode);

        //11.soc
        int soc = byteBuf.readUnsignedByte();
        fixedRealTimeDataCmdMessage.setSoc(soc);

        //12.电池组最⾼温度
        int batteryPackMaxTemperature = Math.abs(byteBuf.readUnsignedByte() - 50);
        fixedRealTimeDataCmdMessage.setBatteryPackMaxTemperature(batteryPackMaxTemperature);

        //13.累计充电时间
        int totalChargeMin = byteBuf.readUnsignedShortLE();
        fixedRealTimeDataCmdMessage.setTotalChargeMin(totalChargeMin);

        //14.剩余时间
        int remainMin = byteBuf.readUnsignedShortLE();
        fixedRealTimeDataCmdMessage.setRemainMin(remainMin);

        //15.充电度数
        BigDecimal chargeDegree = NumberUtil.intToBigDecimalDivideMultiple(byteBuf.readUnsignedIntLE(), 10000,4);
        fixedRealTimeDataCmdMessage.setChargeDegree(chargeDegree);

        //16.计损充电度数
        BigDecimal loseDegree = NumberUtil.intToBigDecimalDivideMultiple(byteBuf.readUnsignedIntLE(), 10000,4);
        fixedRealTimeDataCmdMessage.setLoseDegree(loseDegree);

        //17.已充⾦额
        BigDecimal chargeAmount = NumberUtil.intToBigDecimalDivideMultiple(byteBuf.readUnsignedIntLE(), 10000,4);
        fixedRealTimeDataCmdMessage.setChargeAmount(chargeAmount);

        //18.硬件故障
        int warnCode = byteBuf.readUnsignedShortLE();
        fixedRealTimeDataCmdMessage.setWarnCode(warnCode);

        return fixedRealTimeDataCmdMessage;
    }
}
