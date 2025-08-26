package com.openuc.cloud.sdk.request;

public class ParallerChargeReplyRequest extends IssueRequest{

    public ParallerChargeReplyRequest(String pileCode, Integer gunCode) {
        super(pileCode, gunCode);
    }

    private Integer tradeOrderNoExt;

    private String chargeCardNo;

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
