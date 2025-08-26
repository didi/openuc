package com.openuc.cloud.sdk.message;

public class ValidateChargeModelCmdMessage extends CmdMessage {


    private  String feeModelNo; //计费模型编号

    public String getFeeModelNo() {
        return feeModelNo;
    }

    public void setFeeModelNo(String feeModelNo) {
        this.feeModelNo = feeModelNo;
    }

    public ValidateChargeModelCmdMessage(int cmdCode, String pilecode) {
        super(cmdCode, pilecode);
    }
}
