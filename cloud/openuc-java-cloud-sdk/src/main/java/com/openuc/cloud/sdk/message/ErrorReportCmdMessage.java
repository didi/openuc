package com.openuc.cloud.sdk.message;

public class ErrorReportCmdMessage extends CmdMessage {
    private String chargeFlowSn; //交易流水号

    private int gunCode; //枪号

    private int SPN2560ZeroTimeOut; //接收 SPN2560=0x00的充电机辨识报⽂超时

    private int SPN2560AATimeOut; //接收 SPN2560=0xAA的充电机辨识报⽂超时

    private int syncTimeMaxPowerOut; //接收充电机的时间同步和充电机最⼤输出能⼒报⽂超时

    private int completePrepareTimeOut; //接收充电机完成充电准备报⽂超时

    private int chargeStateTimeOut; //接收充电机充电状态报⽂超时

    private int chargeStopTimeOut; //接收充电机中⽌充电报⽂超时

    private int chargeStatisticsTimeOut; //接收充电机充电统计报⽂超时

    private int carBmsRecognitionTimeOut; //接收 BMS 和⻋辆的辨识报⽂超时

    private int batteryParamTimeOut; //接收电池充电参数报⽂超时

    private int bmsCompletePrepareTimeOut; //接收 BMS 完成充电准备报⽂超时

    private int batteryChargeStateTimeOut; //接收电池充电总状态报⽂超时

    private int batteryChargeNeedTimeOut; //接收电池充电要求报⽂超时

    private int bmsChargeStopTimeOut; //接收 BMS 中⽌充电报⽂超时

    private int bmsChargeStatisticsTimeOut; //接收 BMS 充电统计报⽂超时

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

    public int getSPN2560ZeroTimeOut() {
        return SPN2560ZeroTimeOut;
    }

    public void setSPN2560ZeroTimeOut(int SPN2560ZeroTimeOut) {
        this.SPN2560ZeroTimeOut = SPN2560ZeroTimeOut;
    }

    public int getSPN2560AATimeOut() {
        return SPN2560AATimeOut;
    }

    public void setSPN2560AATimeOut(int SPN2560AATimeOut) {
        this.SPN2560AATimeOut = SPN2560AATimeOut;
    }

    public int getSyncTimeMaxPowerOut() {
        return syncTimeMaxPowerOut;
    }

    public void setSyncTimeMaxPowerOut(int syncTimeMaxPowerOut) {
        this.syncTimeMaxPowerOut = syncTimeMaxPowerOut;
    }

    public int getCompletePrepareTimeOut() {
        return completePrepareTimeOut;
    }

    public void setCompletePrepareTimeOut(int completePrepareTimeOut) {
        this.completePrepareTimeOut = completePrepareTimeOut;
    }

    public int getChargeStateTimeOut() {
        return chargeStateTimeOut;
    }

    public void setChargeStateTimeOut(int chargeStateTimeOut) {
        this.chargeStateTimeOut = chargeStateTimeOut;
    }

    public int getChargeStopTimeOut() {
        return chargeStopTimeOut;
    }

    public void setChargeStopTimeOut(int chargeStopTimeOut) {
        this.chargeStopTimeOut = chargeStopTimeOut;
    }

    public int getChargeStatisticsTimeOut() {
        return chargeStatisticsTimeOut;
    }

    public void setChargeStatisticsTimeOut(int chargeStatisticsTimeOut) {
        this.chargeStatisticsTimeOut = chargeStatisticsTimeOut;
    }

    public int getCarBmsRecognitionTimeOut() {
        return carBmsRecognitionTimeOut;
    }

    public void setCarBmsRecognitionTimeOut(int carBmsRecognitionTimeOut) {
        this.carBmsRecognitionTimeOut = carBmsRecognitionTimeOut;
    }

    public int getBatteryParamTimeOut() {
        return batteryParamTimeOut;
    }

    public void setBatteryParamTimeOut(int batteryParamTimeOut) {
        this.batteryParamTimeOut = batteryParamTimeOut;
    }

    public int getBmsCompletePrepareTimeOut() {
        return bmsCompletePrepareTimeOut;
    }

    public void setBmsCompletePrepareTimeOut(int bmsCompletePrepareTimeOut) {
        this.bmsCompletePrepareTimeOut = bmsCompletePrepareTimeOut;
    }

    public int getBatteryChargeStateTimeOut() {
        return batteryChargeStateTimeOut;
    }

    public void setBatteryChargeStateTimeOut(int batteryChargeStateTimeOut) {
        this.batteryChargeStateTimeOut = batteryChargeStateTimeOut;
    }

    public int getBatteryChargeNeedTimeOut() {
        return batteryChargeNeedTimeOut;
    }

    public void setBatteryChargeNeedTimeOut(int batteryChargeNeedTimeOut) {
        this.batteryChargeNeedTimeOut = batteryChargeNeedTimeOut;
    }

    public int getBmsChargeStopTimeOut() {
        return bmsChargeStopTimeOut;
    }

    public void setBmsChargeStopTimeOut(int bmsChargeStopTimeOut) {
        this.bmsChargeStopTimeOut = bmsChargeStopTimeOut;
    }

    public int getBmsChargeStatisticsTimeOut() {
        return bmsChargeStatisticsTimeOut;
    }

    public void setBmsChargeStatisticsTimeOut(int bmsChargeStatisticsTimeOut) {
        this.bmsChargeStatisticsTimeOut = bmsChargeStatisticsTimeOut;
    }

    public ErrorReportCmdMessage(int cmdCode, String pilecode) {
        super(cmdCode, pilecode);
    }
}
