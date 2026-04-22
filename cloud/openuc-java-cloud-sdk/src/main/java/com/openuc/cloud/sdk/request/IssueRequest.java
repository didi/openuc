package com.openuc.cloud.sdk.request;

/**
 * @author lanxin.liao
 */
public class IssueRequest {
    /**
     * 桩编码
     */
    private String pileCode;
    /**
     * 枪号
     */
    private Integer gunCode;


    public IssueRequest(String pileCode, Integer gunCode) {
        this.pileCode = pileCode;
        this.gunCode = gunCode;
    }

    public String getPileCode() {
        return pileCode;
    }

    public Integer getGunCode() {
        return gunCode;
    }
}
