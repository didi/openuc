package com.openuc.cloud.sdk.request;

public class QrCodeRequest extends IssueRequest {

    private String qrcode;

    public QrCodeRequest(String pileCode, Integer gunCode) {
        super(pileCode, gunCode);
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }
}
