package com.openuc.cloud.sdk.message;

public class BmsPileStopCmdMessage extends CmdMessage {
    private String chargeFlowSn; //交易流水号

    private int gunCode; //枪号

    private int bmsPileChargeStopReason; //充电机 中⽌充电原因

    private int bmsPileChargeStopFailReason; //充电机中⽌充电故障原因

    private int bmsPileChargeStopErrorReason; //充电机中⽌充电错误原因

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

    public int getBmsPileChargeStopReason() {
        return bmsPileChargeStopReason;
    }

    public void setBmsPileChargeStopReason(int bmsPileChargeStopReason) {
        this.bmsPileChargeStopReason = bmsPileChargeStopReason;
    }

    public int getBmsPileChargeStopFailReason() {
        return bmsPileChargeStopFailReason;
    }

    public void setBmsPileChargeStopFailReason(int bmsPileChargeStopFailReason) {
        this.bmsPileChargeStopFailReason = bmsPileChargeStopFailReason;
    }

    public int getBmsPileChargeStopErrorReason() {
        return bmsPileChargeStopErrorReason;
    }

    public void setBmsPileChargeStopErrorReason(int bmsPileChargeStopErrorReason) {
        this.bmsPileChargeStopErrorReason = bmsPileChargeStopErrorReason;
    }

    public BmsPileStopCmdMessage(int cmdCode, String pilecode) {
        super(cmdCode, pilecode);
    }
}
