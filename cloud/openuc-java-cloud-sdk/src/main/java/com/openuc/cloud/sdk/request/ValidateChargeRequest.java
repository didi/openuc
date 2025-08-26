package com.openuc.cloud.sdk.request;

public class ValidateChargeRequest extends IssueRequest{

    public ValidateChargeRequest(String pileCode, Integer gunCode) {
        super(pileCode, gunCode);
    }

    /**
     * 计费模型编号
     */
    private String chargeModelNo;

    public String getChargeModelNo() {
        return chargeModelNo;
    }

    public void setChargeModelNo(String chargeModelNo) {
        this.chargeModelNo = chargeModelNo;
    }
}
