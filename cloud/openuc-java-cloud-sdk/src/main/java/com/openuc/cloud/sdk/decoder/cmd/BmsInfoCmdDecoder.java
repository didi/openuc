package com.openuc.cloud.sdk.decoder.cmd;

import com.openuc.cloud.sdk.message.ApplicationMessage;
import com.openuc.cloud.sdk.message.BmsInfoCmdMessage;
import com.openuc.cloud.sdk.message.CmdMessage;
import com.openuc.cloud.sdk.util.BCD;
import com.openuc.cloud.sdk.util.ChargeFlowSnUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 0x25
 * 充电过程 BMS 信息
 */
public class BmsInfoCmdDecoder implements CmdDecoder {
    @Override
    public int getCmdCode() {
        return 0x25;
    }

    @Override
    public CmdMessage process(ApplicationMessage message) {
        ByteBuf byteBuf = Unpooled.copiedBuffer(message.getMsgBody());

        //1.交易流水号
        byte[] chargeFlowSnB = new byte[16];
        byteBuf.readBytes(chargeFlowSnB);
        String chargeFlowSn = ChargeFlowSnUtil.convertTradeOrderNo(BCD.bcdToStr(chargeFlowSnB));

        //2.桩编码
        byte[] pileCodeSNB = new byte[7];
        byteBuf.readBytes(pileCodeSNB);
        String pileCodeSN = BCD.bcdToStr(pileCodeSNB);

        BmsInfoCmdMessage bmsInfoCmdMessage = new BmsInfoCmdMessage(getCmdCode(), pileCodeSN);
        bmsInfoCmdMessage.setChargeFlowSn(chargeFlowSn);

        //3.枪号
        byte[] gunCodeB = new byte[1];
        byteBuf.readBytes(gunCodeB);
        int gunNo = Integer.parseInt(BCD.bcdToStr(gunCodeB));
        bmsInfoCmdMessage.setGunCode(gunNo);

        //4.BMS 最⾼单体动⼒蓄电池电压所在编号
        int bmsBatteryMaxVoltageNumber = byteBuf.readUnsignedByte();
        bmsInfoCmdMessage.setBmsBatteryMaxTemperature(bmsBatteryMaxVoltageNumber);

        //5.BMS 最⾼动⼒蓄电池温度
        int bmsBatteryMaxTemperature = Math.abs(byteBuf.readUnsignedByte() - 50);
        bmsInfoCmdMessage.setBmsBatteryMaxTemperature(bmsBatteryMaxTemperature);

        //6.最⾼温度检测点编号
        int bmsMaxTemperatureMeasureNumber = byteBuf.readUnsignedByte();
        bmsInfoCmdMessage.setBmsMaxTemperatureMeasureNumber(bmsMaxTemperatureMeasureNumber);

        //7.最低动⼒蓄电池温度
        int bmsBatteryMinTemperature = Math.abs(byteBuf.readUnsignedByte() - 50);
        bmsInfoCmdMessage.setBmsBatteryMinTemperature(bmsBatteryMinTemperature);

        //8.最低动⼒蓄电池温度检测点编号
        int bmsMinTemperatureMeasureNumber = byteBuf.readUnsignedByte();
        bmsInfoCmdMessage.setBmsMinTemperatureMeasureNumber(bmsMinTemperatureMeasureNumber);

        //BMS 单体动⼒蓄电池电压过⾼/过低
        int bmsVoltageExceedLimit = byteBuf.readUnsignedShortLE();
        bmsInfoCmdMessage.setBmsVoltageExceedLimit(bmsVoltageExceedLimit);

        return bmsInfoCmdMessage;

    }
}
