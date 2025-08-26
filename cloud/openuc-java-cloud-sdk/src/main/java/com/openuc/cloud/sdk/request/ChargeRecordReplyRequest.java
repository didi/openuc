package com.openuc.cloud.sdk.request;


public class ChargeRecordReplyRequest extends IssueRequest{

    public ChargeRecordReplyRequest(String pileCode, Integer gunCode) {
        super(pileCode, gunCode);
    }

    /**
     * 交易流水号
     */
    private String tradeOrderNo;

    public String getTradeOrderNo() {
        return tradeOrderNo;
    }

    public void setTradeOrderNo(String tradeOrderNo) {
        this.tradeOrderNo = tradeOrderNo;
    }
}
