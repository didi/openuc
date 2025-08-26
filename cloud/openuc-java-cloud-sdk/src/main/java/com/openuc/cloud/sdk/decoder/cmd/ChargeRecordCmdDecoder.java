package com.openuc.cloud.sdk.decoder.cmd;

import com.openuc.cloud.sdk.message.ApplicationMessage;
import com.openuc.cloud.sdk.message.ChargeRecordCmdMessage;
import com.openuc.cloud.sdk.message.CmdMessage;
import com.openuc.cloud.sdk.util.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 0x3B
 * 交易记录
 */
public class ChargeRecordCmdDecoder implements CmdDecoder {
    @Override
    public int getCmdCode() {
        return 0x3B;
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

        ChargeRecordCmdMessage chargeRecordCmdMessage = new ChargeRecordCmdMessage(getCmdCode(), pileCode);
        chargeRecordCmdMessage.setChargeFlowSn(chargeFlowSn);

        //3.枪号
        byte[] gunCodeB = new byte[1];
        byteBuf.readBytes(gunCodeB);
        int gunNo = Integer.parseInt(BCD.bcdToStr(gunCodeB));
        chargeRecordCmdMessage.setGunNo(gunNo);

        // 4.开始时间
        byte[] orderStartTimeB = new byte[7];
        byteBuf.readBytes(orderStartTimeB);
        Date orderStartTime = TimeUtil.cp56time2aToDate(orderStartTimeB);
        chargeRecordCmdMessage.setOrderStartTime(orderStartTime);

        // 5.结束时间
        byte[] orderEndTimeB = new byte[7];
        byteBuf.readBytes(orderEndTimeB);
        Date orderEndTime = TimeUtil.cp56time2aToDate(orderEndTimeB);
        chargeRecordCmdMessage.setOrderEndTime(orderEndTime);

        // 6.尖单价
        BigDecimal jianPrice = NumberUtil.intToBigDecimalDivideMultiple(byteBuf.readUnsignedIntLE(), 100000, 5);
        chargeRecordCmdMessage.setJianPrice(jianPrice);

        //7. 尖电量
        BigDecimal jianPower = NumberUtil.intToBigDecimalDivideMultiple(byteBuf.readUnsignedIntLE(), 10000, 4);
        chargeRecordCmdMessage.setJianPower(jianPower);

        //8.计损尖电量
        BigDecimal jianLosePower = NumberUtil.intToBigDecimalDivideMultiple(byteBuf.readUnsignedIntLE(), 10000, 4);
        chargeRecordCmdMessage.setJianLosePower(jianLosePower);

        // 9.尖金额
        BigDecimal jianAmount = NumberUtil.intToBigDecimalDivideMultiple(byteBuf.readUnsignedIntLE(), 10000, 4);
        chargeRecordCmdMessage.setJianAmount(jianAmount);

        // 10.峰单价
        BigDecimal fengPrice = NumberUtil.intToBigDecimalDivideMultiple(byteBuf.readUnsignedIntLE(), 100000, 5);
        chargeRecordCmdMessage.setFengPrice(fengPrice);

        //11. 峰电量
        BigDecimal fengPower = NumberUtil.intToBigDecimalDivideMultiple(byteBuf.readUnsignedIntLE(), 10000, 4);
        chargeRecordCmdMessage.setFengPower(fengPower);

        //12.计损峰电量
        BigDecimal fengLosePower = NumberUtil.intToBigDecimalDivideMultiple(byteBuf.readUnsignedIntLE(), 10000, 4);
        chargeRecordCmdMessage.setFengLosePower(fengLosePower);

        // 13.峰金额
        BigDecimal fengAmount = NumberUtil.intToBigDecimalDivideMultiple(byteBuf.readUnsignedIntLE(), 10000, 4);
        chargeRecordCmdMessage.setFengAmount(fengAmount);

        // 14.平单价
        BigDecimal pingPrice = NumberUtil.intToBigDecimalDivideMultiple(byteBuf.readUnsignedIntLE(), 100000, 5);
        chargeRecordCmdMessage.setPingPrice(pingPrice);

        //15. 平电量
        BigDecimal pingPower = NumberUtil.intToBigDecimalDivideMultiple(byteBuf.readUnsignedIntLE(), 10000, 4);
        chargeRecordCmdMessage.setPingPower(pingPower);

        //16.计损平电量
        BigDecimal pingLosePower = NumberUtil.intToBigDecimalDivideMultiple(byteBuf.readUnsignedIntLE(), 10000, 4);
        chargeRecordCmdMessage.setPingLosePower(pingLosePower);

        // 17.平金额
        BigDecimal pingAmount = NumberUtil.intToBigDecimalDivideMultiple(byteBuf.readUnsignedIntLE(), 10000, 4);
        chargeRecordCmdMessage.setPingAmount(pingAmount);

        // 18.谷单价
        BigDecimal gugPrice = NumberUtil.intToBigDecimalDivideMultiple(byteBuf.readUnsignedIntLE(), 100000, 5);
        chargeRecordCmdMessage.setGugPrice(gugPrice);

        //19. 谷电量
        BigDecimal guPower = NumberUtil.intToBigDecimalDivideMultiple(byteBuf.readUnsignedIntLE(), 10000, 4);
        chargeRecordCmdMessage.setGuPower(guPower);

        //20.计损谷电量
        BigDecimal guLosePower = NumberUtil.intToBigDecimalDivideMultiple(byteBuf.readUnsignedIntLE(), 10000, 4);
        chargeRecordCmdMessage.setGuLosePower(guLosePower);

        // 21.谷金额
        BigDecimal guAmount = NumberUtil.intToBigDecimalDivideMultiple(byteBuf.readUnsignedIntLE(), 10000, 4);
        chargeRecordCmdMessage.setGuAmount(guAmount);

        // 22.电表总起值
        byte[] startMeterValueB = new byte[5];
        byteBuf.readBytes(startMeterValueB);
        BigDecimal startE = NumberUtil.intToBigDecimalDivideMultiple(HexStrUtil.littleEndian5BytesToLong(startMeterValueB), 10000, 4);
        chargeRecordCmdMessage.setStartE(startE);

        // 23.电表总止值
        byte[] endMeterValueB = new byte[5];
        byteBuf.readBytes(endMeterValueB);
        BigDecimal endE = NumberUtil.intToBigDecimalDivideMultiple(HexStrUtil.littleEndian5BytesToLong(endMeterValueB), 10000, 4);
        chargeRecordCmdMessage.setEndE(endE);

        // 24.总电量
        BigDecimal totalPower = NumberUtil.intToBigDecimalDivideMultiple(byteBuf.readUnsignedIntLE(), 10000, 4);
        chargeRecordCmdMessage.setTotalPower(totalPower);

        //25.计损总电量
        BigDecimal totalLosePower = NumberUtil.intToBigDecimalDivideMultiple(byteBuf.readUnsignedIntLE(), 10000, 4);
        chargeRecordCmdMessage.setTotalLosePower(totalLosePower);

        //26 .消费金额
        BigDecimal totalAmount = NumberUtil.intToBigDecimalDivideMultiple(byteBuf.readUnsignedIntLE(), 10000, 4);
        chargeRecordCmdMessage.setTotalAmount(totalAmount);

        //27.电动汽车唯一标识
        byte[] carVINB = new byte[17];
        byteBuf.readBytes(carVINB);
        String carVIN = AsciiUtil.bytesToAscii(carVINB);
        chargeRecordCmdMessage.setCarVIN(carVIN);

        //28.交易标识 0x01：app 启动0x02：卡启动 0x04：离线卡启动 0x05: vin 码启动充电
        int chargeStartType = byteBuf.readUnsignedByte();
        chargeRecordCmdMessage.setChargeStartType(chargeStartType);

        //29.交易日期、时间
        byte[] orderTimeB = new byte[7];
        byteBuf.readBytes(orderTimeB);
        Date orderTime = TimeUtil.cp56time2aToDate(orderTimeB);
        chargeRecordCmdMessage.setOrderTime(orderTime);

        // 30.停止原因
        int stopReason = byteBuf.readUnsignedByte();
        chargeRecordCmdMessage.setStopReason(stopReason);

        //31 物理卡号
        long chargeCardNo = byteBuf.readLongLE();
        chargeRecordCmdMessage.setChargeCardNo(chargeCardNo);

        return chargeRecordCmdMessage;
    }
}
