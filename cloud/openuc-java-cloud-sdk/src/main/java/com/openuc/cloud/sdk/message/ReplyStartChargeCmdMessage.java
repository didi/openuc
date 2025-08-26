package com.openuc.cloud.sdk.message;

public class ReplyStartChargeCmdMessage extends CmdMessage {

    private String chargeFlowSn; //交易流水号
    private int gunCode; //枪号

    private int cmdResult; //启动结果

    private int failReason; //失败原因

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

    public int getCmdResult() {
        return cmdResult;
    }

    public void setCmdResult(int cmdResult) {
        this.cmdResult = cmdResult;
    }

    public int getFailReason() {
        return failReason;
    }

    public void setFailReason(int failReason) {
        this.failReason = failReason;
    }

    public ReplyStartChargeCmdMessage(int cmdCode, String pilecode) {
        super(cmdCode, pilecode);
    }
}
