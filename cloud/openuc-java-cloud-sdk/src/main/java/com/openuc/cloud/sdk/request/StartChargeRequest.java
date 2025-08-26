package com.openuc.cloud.sdk.request;

import java.util.Date;

/**
 * @author fangshun
 */
public class StartChargeRequest extends IssueRequest{

    // 用户识别号，传用户ID即可
    private String userid;
    // 交易流水号
    private String tradeOrderNo;
    // 用户余额，没有可以不填
    private int userBalance;
    // 开始时间
    private Date startTime;
    // 整型订单号 启动充电时， tradeOrderNo和tradeOrderNoExt都必须要传
    private Integer tradeOrderNoExt;
    // 扩展字段，用于接入层业务处理
    private String extension;

    //余额 单位是分
    private Integer amount;

    private String equipExtParam;
    public StartChargeRequest(String pileCode, Integer gunCode) {
        super(pileCode, gunCode);
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTradeOrderNo() {
        return tradeOrderNo;
    }

    public void setTradeOrderNo(String tradeOrderNo) {
        this.tradeOrderNo = tradeOrderNo;
    }

    public int getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(int userBalance) {
        this.userBalance = userBalance;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getTradeOrderNoExt() {
        return tradeOrderNoExt;
    }

    public void setTradeOrderNoExt(Integer tradeOrderNoExt) {
        this.tradeOrderNoExt = tradeOrderNoExt;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getEquipExtParam() {
        return equipExtParam;
    }

    public void setEquipExtParam(String equipExtParam) {
        this.equipExtParam = equipExtParam;
    }
}
