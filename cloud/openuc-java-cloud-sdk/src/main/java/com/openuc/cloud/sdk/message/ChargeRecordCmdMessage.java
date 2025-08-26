package com.openuc.cloud.sdk.message;

import java.math.BigDecimal;
import java.util.Date;

public class ChargeRecordCmdMessage extends CmdMessage {

    private String chargeFlowSn; //交易流水号

    private int gunNo;// 枪号

    private Date orderStartTime;//开始时间

    private Date orderEndTime; //结束时间

    private BigDecimal jianPrice;//尖单价

    private BigDecimal jianPower; //尖电量

    private BigDecimal jianLosePower;//计损尖电量

    private BigDecimal jianAmount;//尖金额

    private BigDecimal fengPrice;//峰单价

    private BigDecimal fengPower;//峰电量

    private BigDecimal fengLosePower;//计损峰电量

    private BigDecimal fengAmount; //峰金额

    private BigDecimal pingPrice;//平单价

    private BigDecimal pingPower;//平电量

    private BigDecimal pingLosePower;//计损平电量

    private BigDecimal pingAmount; //平金额

    private BigDecimal gugPrice; //谷单价

    private BigDecimal guPower; //谷电量

    private BigDecimal guLosePower; //计损谷电量

    private BigDecimal guAmount; //谷金额

    private BigDecimal startE;//电表总起值

    private BigDecimal endE; //电表总止值

    private BigDecimal totalPower; //总电量

    private BigDecimal totalLosePower; //计损总电量

    private BigDecimal totalAmount; //消费金额

    private String carVIN;//电动汽车唯一标识

    private int chargeStartType;//交易标识

    private Date orderTime;//交易日期、时间

    private int stopReason;//停止原因

    private long chargeCardNo;   // 物理卡号

    public String getChargeFlowSn() {
        return chargeFlowSn;
    }

    public void setChargeFlowSn(String chargeFlowSn) {
        this.chargeFlowSn = chargeFlowSn;
    }

    public int getGunNo() {
        return gunNo;
    }

    public void setGunNo(int gunNo) {
        this.gunNo = gunNo;
    }

    public Date getOrderStartTime() {
        return orderStartTime;
    }

    public void setOrderStartTime(Date orderStartTime) {
        this.orderStartTime = orderStartTime;
    }

    public Date getOrderEndTime() {
        return orderEndTime;
    }

    public void setOrderEndTime(Date orderEndTime) {
        this.orderEndTime = orderEndTime;
    }

    public BigDecimal getJianPrice() {
        return jianPrice;
    }

    public void setJianPrice(BigDecimal jianPrice) {
        this.jianPrice = jianPrice;
    }

    public BigDecimal getJianPower() {
        return jianPower;
    }

    public void setJianPower(BigDecimal jianPower) {
        this.jianPower = jianPower;
    }

    public BigDecimal getJianLosePower() {
        return jianLosePower;
    }

    public void setJianLosePower(BigDecimal jianLosePower) {
        this.jianLosePower = jianLosePower;
    }

    public BigDecimal getJianAmount() {
        return jianAmount;
    }

    public void setJianAmount(BigDecimal jianAmount) {
        this.jianAmount = jianAmount;
    }

    public BigDecimal getFengPrice() {
        return fengPrice;
    }

    public void setFengPrice(BigDecimal fengPrice) {
        this.fengPrice = fengPrice;
    }

    public BigDecimal getFengPower() {
        return fengPower;
    }

    public void setFengPower(BigDecimal fengPower) {
        this.fengPower = fengPower;
    }

    public BigDecimal getFengLosePower() {
        return fengLosePower;
    }

    public void setFengLosePower(BigDecimal fengLosePower) {
        this.fengLosePower = fengLosePower;
    }

    public BigDecimal getFengAmount() {
        return fengAmount;
    }

    public void setFengAmount(BigDecimal fengAmount) {
        this.fengAmount = fengAmount;
    }

    public BigDecimal getPingPrice() {
        return pingPrice;
    }

    public void setPingPrice(BigDecimal pingPrice) {
        this.pingPrice = pingPrice;
    }

    public BigDecimal getPingPower() {
        return pingPower;
    }

    public void setPingPower(BigDecimal pingPower) {
        this.pingPower = pingPower;
    }

    public BigDecimal getPingLosePower() {
        return pingLosePower;
    }

    public void setPingLosePower(BigDecimal pingLosePower) {
        this.pingLosePower = pingLosePower;
    }

    public BigDecimal getPingAmount() {
        return pingAmount;
    }

    public void setPingAmount(BigDecimal pingAmount) {
        this.pingAmount = pingAmount;
    }

    public BigDecimal getGugPrice() {
        return gugPrice;
    }

    public void setGugPrice(BigDecimal gugPrice) {
        this.gugPrice = gugPrice;
    }

    public BigDecimal getGuPower() {
        return guPower;
    }

    public void setGuPower(BigDecimal guPower) {
        this.guPower = guPower;
    }

    public BigDecimal getGuLosePower() {
        return guLosePower;
    }

    public void setGuLosePower(BigDecimal guLosePower) {
        this.guLosePower = guLosePower;
    }

    public BigDecimal getGuAmount() {
        return guAmount;
    }

    public void setGuAmount(BigDecimal guAmount) {
        this.guAmount = guAmount;
    }

    public BigDecimal getStartE() {
        return startE;
    }

    public void setStartE(BigDecimal startE) {
        this.startE = startE;
    }

    public BigDecimal getEndE() {
        return endE;
    }

    public void setEndE(BigDecimal endE) {
        this.endE = endE;
    }

    public BigDecimal getTotalPower() {
        return totalPower;
    }

    public void setTotalPower(BigDecimal totalPower) {
        this.totalPower = totalPower;
    }

    public BigDecimal getTotalLosePower() {
        return totalLosePower;
    }

    public void setTotalLosePower(BigDecimal totalLosePower) {
        this.totalLosePower = totalLosePower;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCarVIN() {
        return carVIN;
    }

    public void setCarVIN(String carVIN) {
        this.carVIN = carVIN;
    }

    public int getChargeStartType() {
        return chargeStartType;
    }

    public void setChargeStartType(int chargeStartType) {
        this.chargeStartType = chargeStartType;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public int getStopReason() {
        return stopReason;
    }

    public void setStopReason(int stopReason) {
        this.stopReason = stopReason;
    }

    public long getChargeCardNo() {
        return chargeCardNo;
    }

    public void setChargeCardNo(long chargeCardNo) {
        this.chargeCardNo = chargeCardNo;
    }

    public ChargeRecordCmdMessage(int cmdCode, String pilecode) {
        super(cmdCode, pilecode);
    }
}
