package com.openuc.cloud.sdk.message;

public class BmsChargeStopCmdMessage extends CmdMessage {

    private String chargeFlowSn; //交流流水号

    private int gunCode; //枪号

    private int bmsStopReason; //BMS 中⽌充电原因

    private int bmsStopFailReason; //BMS 中⽌充电故障原因

    private int bmsStopErrorReason; //BMS 中⽌充电错误原因

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

    public int getBmsStopReason() {
        return bmsStopReason;
    }

    public void setBmsStopReason(int bmsStopReason) {
        this.bmsStopReason = bmsStopReason;
    }

    public int getBmsStopFailReason() {
        return bmsStopFailReason;
    }

    public void setBmsStopFailReason(int bmsStopFailReason) {
        this.bmsStopFailReason = bmsStopFailReason;
    }

    public int getBmsStopErrorReason() {
        return bmsStopErrorReason;
    }

    public void setBmsStopErrorReason(int bmsStopErrorReason) {
        this.bmsStopErrorReason = bmsStopErrorReason;
    }

    public BmsChargeStopCmdMessage(int cmdCode, String pilecode) {
        super(cmdCode, pilecode);
    }
}
