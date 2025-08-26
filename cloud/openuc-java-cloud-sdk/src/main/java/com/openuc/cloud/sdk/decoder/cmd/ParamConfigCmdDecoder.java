package com.openuc.cloud.sdk.decoder.cmd;

import com.openuc.cloud.sdk.message.ApplicationMessage;
import com.openuc.cloud.sdk.message.CmdMessage;
import com.openuc.cloud.sdk.message.ParamConfigCmdMessage;
import com.openuc.cloud.sdk.util.BCD;
import com.openuc.cloud.sdk.util.ChargeFlowSnUtil;
import com.openuc.cloud.sdk.util.NumberUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.math.BigDecimal;

/**
 * 0x17
 * 参数配置
 */
public class ParamConfigCmdDecoder implements CmdDecoder{
    @Override
    public int getCmdCode() {
        return 0x17;
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

        ParamConfigCmdMessage paramConfigCmdMessage = new ParamConfigCmdMessage(getCmdCode(),pileCode);
        paramConfigCmdMessage.setChargeFlowSn(chargeFlowSn);

        //3.枪号
        byte[] gunCodeB = new byte[1];
        byteBuf.readBytes(gunCodeB);
        int gunCode = Integer.parseInt(BCD.bcdToStr(gunCodeB));
        paramConfigCmdMessage.setGunCode(gunCode);

        //4.BMS 单体动⼒蓄电池最⾼允许 充电电压
        BigDecimal bmsSingleAllowMaxVoltage = NumberUtil.intToBigDecimalDivideMultiple(byteBuf.readUnsignedShortLE(),100);
        paramConfigCmdMessage.setBmsSingleAllowMaxVoltage(bmsSingleAllowMaxVoltage);

        //5.BMS 最⾼允许充电电流
        BigDecimal bmsAllowMaxCurrent = new BigDecimal(Math.abs(NumberUtil.intToBigDecimalDivideMultiple(byteBuf.readUnsignedShortLE(), 10).subtract(new BigDecimal(400)).doubleValue()));
        paramConfigCmdMessage.setBmsAllowMaxCurrent(bmsAllowMaxCurrent);

        //6.BMS 动⼒蓄电池标称总能量
        BigDecimal bmsTotalPower = NumberUtil.intToBigDecimalDivideMultiple(byteBuf.readUnsignedShortLE(),10);
        paramConfigCmdMessage.setBmsTotalPower(bmsTotalPower);

        //7.BMS 最⾼允许充电总电压
        BigDecimal bmsAllowMaxVoltage = NumberUtil.intToBigDecimalDivideMultiple(byteBuf.readUnsignedShortLE(),10);
        paramConfigCmdMessage.setBmsAllowMaxVoltage(bmsAllowMaxVoltage);

        //8.BMS 最⾼允许温度
        int bmsAllowMaxTemperature = Math.abs(byteBuf.readUnsignedByte()-50);
        paramConfigCmdMessage.setBmsAllowMaxTemperature(bmsAllowMaxTemperature);

        //9.BMS 整⻋动⼒蓄电池荷电状态
        int bmsCurrentSoc = byteBuf.readUnsignedShortLE();
        paramConfigCmdMessage.setBmsCurrentSoc(bmsCurrentSoc);

        //10.BMS 整⻋动⼒蓄电池当前电池电压
        int bmsCurrentVoltage = byteBuf.readUnsignedShortLE();
        paramConfigCmdMessage.setBmsCurrentVoltage(bmsCurrentVoltage);

        //11.电桩最⾼输出电压
        BigDecimal bmsMaxOutputVoltage = NumberUtil.intToBigDecimalDivideMultiple(byteBuf.readUnsignedShortLE(),10);
        paramConfigCmdMessage.setBmsMaxOutputVoltage(bmsMaxOutputVoltage);

        //12.电桩最低输出电压
        BigDecimal bmsMinOutputVoltage = NumberUtil.intToBigDecimalDivideMultiple(byteBuf.readUnsignedShortLE(),10);
        paramConfigCmdMessage.setBmsMinOutputVoltage(bmsMinOutputVoltage);

        //13.电桩最⼤输出电流
        BigDecimal bmsMaxOutputCurrent = new BigDecimal(Math.abs(NumberUtil.intToBigDecimalDivideMultiple(byteBuf.readUnsignedShortLE(), 10).subtract(new BigDecimal(400)).doubleValue()));
        paramConfigCmdMessage.setBmsMinOutputCurrent(bmsMaxOutputCurrent);

        //14.电桩最⼩输出电流
        BigDecimal bmsMinOutputCurrent = new BigDecimal(Math.abs(NumberUtil.intToBigDecimalDivideMultiple(byteBuf.readUnsignedShortLE(), 10).subtract(new BigDecimal(400)).doubleValue()));
        paramConfigCmdMessage.setBmsMinOutputCurrent(bmsMinOutputCurrent);

        return paramConfigCmdMessage;
    }
}
