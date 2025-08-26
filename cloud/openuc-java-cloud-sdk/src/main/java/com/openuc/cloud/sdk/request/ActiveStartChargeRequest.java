package com.openuc.cloud.sdk.request;

public class ActiveStartChargeRequest extends IssueRequest{

    public ActiveStartChargeRequest(String pileCode, Integer gunCode) {
        super(pileCode, gunCode);
    }

    /**
     * 充电卡号
     */
    private String chargeCardNo;

    private Integer tradeOrderNoExt;

    public Integer getTradeOrderNoExt() {
        return tradeOrderNoExt;
    }

    public void setTradeOrderNoExt(Integer tradeOrderNoExt) {
        this.tradeOrderNoExt = tradeOrderNoExt;
    }

    public String getChargeCardNo() {
        return chargeCardNo;
    }

    public void setChargeCardNo(String chargeCardNo) {
        this.chargeCardNo = chargeCardNo;
    }
}
