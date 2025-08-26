package com.openuc.cloud.sdk.message;

public class ReplyParallelChargeCmdMessage extends CmdMessage{
    private String chargeFlowSn; //交易流水号
    private int gunCode; //枪号

    private int result; //启动结果

    private int failReason; //失败原因

    private int mainType; //主辅枪标记

    private String parallelNo; //并充序号

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

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getFailReason() {
        return failReason;
    }

    public void setFailReason(int failReason) {
        this.failReason = failReason;
    }

    public int getMainType() {
        return mainType;
    }

    public void setMainType(int mainType) {
        this.mainType = mainType;
    }

    public String getParallelNo() {
        return parallelNo;
    }

    public void setParallelNo(String parallelNo) {
        this.parallelNo = parallelNo;
    }

    public ReplyParallelChargeCmdMessage(int cmdCode, String pilecode) {
        super(cmdCode, pilecode);
    }
}
