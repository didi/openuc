package com.openuc.cloud.sdk.decoder.cmd;

import com.openuc.cloud.sdk.message.ApplicationMessage;
import com.openuc.cloud.sdk.message.CmdMessage;
import com.openuc.cloud.sdk.message.ErrorReportCmdMessage;
import com.openuc.cloud.sdk.util.BCD;
import com.openuc.cloud.sdk.util.ChargeFlowSnUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 0x1B
 * 错误报文
 */
public class ErrorReportCmdDecoder implements CmdDecoder {
    @Override
    public int getCmdCode() {
        return 0x1B;
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

        ErrorReportCmdMessage errorReportCmdMessage = new ErrorReportCmdMessage(getCmdCode(), pileCode);
        errorReportCmdMessage.setChargeFlowSn(chargeFlowSn);

        //3.枪号
        byte[] gunCodeB = new byte[1];
        byteBuf.readBytes(gunCodeB);
        int gunCode = Integer.parseInt(BCD.bcdToStr(gunCodeB));
        errorReportCmdMessage.setGunCode(gunCode);

        //4.接收 SPN2560=0x00的充电机辨识报⽂超时
        int spn2560ZeroTimeOut = byteBuf.readUnsignedShortLE();
        errorReportCmdMessage.setSPN2560ZeroTimeOut(spn2560ZeroTimeOut);

        //5.接收 SPN2560=0xAA的充电机辨识报⽂超时
        int spn2560AATimeOut = byteBuf.readUnsignedShortLE();
        errorReportCmdMessage.setSPN2560AATimeOut(spn2560AATimeOut);

        //6.预留位
        byteBuf.skipBytes(4);

        //7.接收充电机的时间同步和充电机最⼤输出能⼒报⽂超时
        int syncTimeMaxPowerOut = byteBuf.readUnsignedShortLE();
        errorReportCmdMessage.setSyncTimeMaxPowerOut(syncTimeMaxPowerOut);

        //8.接收充电机完成充电准备报⽂超时
        int completePrepareTimeOut = byteBuf.readUnsignedShortLE();
        errorReportCmdMessage.setCompletePrepareTimeOut(completePrepareTimeOut);

        //9.预留位
        byteBuf.skipBytes(4);

        //10.接收充电机充电状态报⽂超时
        int chargeStateTimeOut = byteBuf.readUnsignedShortLE();
        errorReportCmdMessage.setChargeStateTimeOut(chargeStateTimeOut);

        //11.接收充电机中⽌充电报⽂超时
        int chargeStopTimeOut = byteBuf.readUnsignedShortLE();
        errorReportCmdMessage.setChargeStopTimeOut(chargeStopTimeOut);

        //12.预留位
        byteBuf.skipBytes(4);

        //13.接收充电机充电统计报⽂超时
        int chargeStatisticsTimeOut = byteBuf.readUnsignedShortLE();
        errorReportCmdMessage.setChargeStatisticsTimeOut(chargeStatisticsTimeOut);

        //14.BMS其他
        byteBuf.skipBytes(6);

        //15.接收 BMS 和⻋辆的辨识报⽂超时
        int carBmsRecognitionTimeOut = byteBuf.readUnsignedShortLE();
        errorReportCmdMessage.setCarBmsRecognitionTimeOut(carBmsRecognitionTimeOut);

        //16.预留位
        byteBuf.skipBytes(6);

        //17.接收电池充电参数报⽂超时
        int batteryParamTimeOut = byteBuf.readUnsignedShortLE();
        errorReportCmdMessage.setBatteryParamTimeOut(batteryParamTimeOut);

        //18.接收 BMS 完成充电准备报⽂超时
        int bmsCompletePrepareTimeOut = byteBuf.readUnsignedShortLE();
        errorReportCmdMessage.setBmsCompletePrepareTimeOut(bmsCompletePrepareTimeOut);

        //19.预留位
        byteBuf.skipBytes(4);

        //20.接收电池充电总状态报⽂超时
        int batteryChargeStateTimeOut = byteBuf.readUnsignedShortLE();
        errorReportCmdMessage.setBatteryChargeStateTimeOut(batteryChargeStateTimeOut);

        //21.接收电池充电要求报⽂超时
        int batteryChargeNeedTimeOut = byteBuf.readUnsignedShortLE();
        errorReportCmdMessage.setBatteryChargeNeedTimeOut(batteryChargeNeedTimeOut);

        //22.接收 BMS 中⽌充电报⽂超时
        int bmsChargeStopTimeOut = byteBuf.readUnsignedShortLE();
        errorReportCmdMessage.setBmsChargeStopTimeOut(bmsChargeStopTimeOut);

        //23.预留位
        byteBuf.skipBytes(2);

        //24.接收 BMS 充电统计报⽂超时
        int bmsChargeStatisticsTimeOut = byteBuf.readUnsignedShortLE();
        errorReportCmdMessage.setBmsChargeStatisticsTimeOut(bmsChargeStatisticsTimeOut);

        //25.充电机其他
        byteBuf.skipBytes(6);

        return errorReportCmdMessage;
    }
}
