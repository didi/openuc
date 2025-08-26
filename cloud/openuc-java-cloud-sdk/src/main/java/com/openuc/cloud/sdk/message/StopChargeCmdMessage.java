package com.openuc.cloud.sdk.message;

import java.math.BigDecimal;

public class StopChargeCmdMessage extends CmdMessage {
    private String chargeFlowSn; //交易流水号

    private int gunCode; //枪号

    private int bmsStopSoc; //BMS 中⽌荷电状态SOC

    private BigDecimal bmsBatterySingleMinVoltage; //BMS 动⼒蓄电池单体最低电压

    private int bmsBatterySingleMaxVoltage; //BMS 动⼒蓄电池单体最⾼电压

    private int bmsBatteryMinTemperature; //BMS 动⼒蓄电池最低温度

    private int bmsBatteryMaxTemperature; //BMS 动⼒蓄电池最⾼温度

    private int bmsTotalChargeTimeMin; //电桩累计充电时间

    private BigDecimal bmsOutputPower; //电桩输出能量

    private long bmsPileNumber; //电桩充电机编号

    public String getChargeFlowSn() {
        return chargeFlowSn;
    }

    public void setChargeFlowSn(String chargeFlowSn) {
        this.chargeFlowSn = chargeFlowSn;
    }

    public int getGunCode() {
        return gunCode;
    }

    public void setGunCode(int gunCode) {
        this.gunCode = gunCode;
    }

    public int getBmsStopSoc() {
        return bmsStopSoc;
    }

    public void setBmsStopSoc(int bmsStopSoc) {
        this.bmsStopSoc = bmsStopSoc;
    }

    public BigDecimal getBmsBatterySingleMinVoltage() {
        return bmsBatterySingleMinVoltage;
    }

    public void setBmsBatterySingleMinVoltage(BigDecimal bmsBatterySingleMinVoltage) {
        this.bmsBatterySingleMinVoltage = bmsBatterySingleMinVoltage;
    }

    public int getBmsBatterySingleMaxVoltage() {
        return bmsBatterySingleMaxVoltage;
    }

    public void setBmsBatterySingleMaxVoltage(int bmsBatterySingleMaxVoltage) {
        this.bmsBatterySingleMaxVoltage = bmsBatterySingleMaxVoltage;
    }

    public int getBmsBatteryMinTemperature() {
        return bmsBatteryMinTemperature;
    }

    public void setBmsBatteryMinTemperature(int bmsBatteryMinTemperature) {
        this.bmsBatteryMinTemperature = bmsBatteryMinTemperature;
    }

    public int getBmsBatteryMaxTemperature() {
        return bmsBatteryMaxTemperature;
    }

    public void setBmsBatteryMaxTemperature(int bmsBatteryMaxTemperature) {
        this.bmsBatteryMaxTemperature = bmsBatteryMaxTemperature;
    }

    public int getBmsTotalChargeTimeMin() {
        return bmsTotalChargeTimeMin;
    }

    public void setBmsTotalChargeTimeMin(int bmsTotalChargeTimeMin) {
        this.bmsTotalChargeTimeMin = bmsTotalChargeTimeMin;
    }

    public BigDecimal getBmsOutputPower() {
        return bmsOutputPower;
    }

    public void setBmsOutputPower(BigDecimal bmsOutputPower) {
        this.bmsOutputPower = bmsOutputPower;
    }

    public long getBmsPileNumber() {
        return bmsPileNumber;
    }

    public void setBmsPileNumber(long bmsPileNumber) {
        this.bmsPileNumber = bmsPileNumber;
    }

    public StopChargeCmdMessage(int cmdCode, String pilecode) {
        super(cmdCode, pilecode);
    }
}
