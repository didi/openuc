package com.openuc.cloud.sdk.message;

public class BmsInfoCmdMessage extends CmdMessage {
    private String chargeFlowSn; //交易流水号

    private int gunCode; //枪号

    private int bmsBatteryMaxVoltageNumber; //BMS 最⾼单体动⼒蓄电池电压所在编号

    private int bmsBatteryMaxTemperature; //BMS 最⾼动⼒蓄电池温度

    private int bmsMaxTemperatureMeasureNumber; //最⾼温度检测点编号

    private int bmsBatteryMinTemperature; //最低动⼒蓄电池温度

    private int bmsMinTemperatureMeasureNumber; //最低动⼒蓄电池温度检测点编号

    private int bmsVoltageExceedLimit; //BMS 单体动⼒蓄电池电压过⾼/过低

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

    public int getBmsBatteryMaxVoltageNumber() {
        return bmsBatteryMaxVoltageNumber;
    }

    public void setBmsBatteryMaxVoltageNumber(int bmsBatteryMaxVoltageNumber) {
        this.bmsBatteryMaxVoltageNumber = bmsBatteryMaxVoltageNumber;
    }

    public int getBmsBatteryMaxTemperature() {
        return bmsBatteryMaxTemperature;
    }

    public void setBmsBatteryMaxTemperature(int bmsBatteryMaxTemperature) {
        this.bmsBatteryMaxTemperature = bmsBatteryMaxTemperature;
    }

    public int getBmsMaxTemperatureMeasureNumber() {
        return bmsMaxTemperatureMeasureNumber;
    }

    public void setBmsMaxTemperatureMeasureNumber(int bmsMaxTemperatureMeasureNumber) {
        this.bmsMaxTemperatureMeasureNumber = bmsMaxTemperatureMeasureNumber;
    }

    public int getBmsBatteryMinTemperature() {
        return bmsBatteryMinTemperature;
    }

    public void setBmsBatteryMinTemperature(int bmsBatteryMinTemperature) {
        this.bmsBatteryMinTemperature = bmsBatteryMinTemperature;
    }

    public int getBmsMinTemperatureMeasureNumber() {
        return bmsMinTemperatureMeasureNumber;
    }

    public void setBmsMinTemperatureMeasureNumber(int bmsMinTemperatureMeasureNumber) {
        this.bmsMinTemperatureMeasureNumber = bmsMinTemperatureMeasureNumber;
    }

    public int getBmsVoltageExceedLimit() {
        return bmsVoltageExceedLimit;
    }

    public void setBmsVoltageExceedLimit(int bmsVoltageExceedLimit) {
        this.bmsVoltageExceedLimit = bmsVoltageExceedLimit;
    }

    public BmsInfoCmdMessage(int cmdCode, String pilecode) {
        super(cmdCode, pilecode);
    }
}
