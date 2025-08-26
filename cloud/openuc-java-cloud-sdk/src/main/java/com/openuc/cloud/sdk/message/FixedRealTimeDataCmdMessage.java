package com.openuc.cloud.sdk.message;

import java.math.BigDecimal;

public class FixedRealTimeDataCmdMessage extends CmdMessage {

    private String chargeFlowSn; //交易流水号

    private int gunCode; //枪号

    private int gunState; //状态

    private int gunPlace; //枪是否归位

    private int isPutGun; //是否插枪

    private BigDecimal outputVoltage; //输出电压

    private BigDecimal outputCurrent; //输出电流

    private int gunLineTemperature; //枪线温度

    private long gunLineCode; //枪线编码

    private int soc; //SOC

    private int batteryPackMaxTemperature; //电池组最⾼温度

    private int totalChargeMin; //累计充电时间

    private int remainMin; //剩余时间

    private BigDecimal chargeDegree; //充电度数

    private BigDecimal loseDegree; //计损充电度数

    private BigDecimal chargeAmount; //已充⾦额

    private int warnCode; //硬件故障

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

    public int getGunState() {
        return gunState;
    }

    public void setGunState(int gunState) {
        this.gunState = gunState;
    }

    public int getGunPlace() {
        return gunPlace;
    }

    public void setGunPlace(int gunPlace) {
        this.gunPlace = gunPlace;
    }

    public int getIsPutGun() {
        return isPutGun;
    }

    public void setIsPutGun(int isPutGun) {
        this.isPutGun = isPutGun;
    }

    public BigDecimal getOutputVoltage() {
        return outputVoltage;
    }

    public void setOutputVoltage(BigDecimal outputVoltage) {
        this.outputVoltage = outputVoltage;
    }

    public BigDecimal getOutputCurrent() {
        return outputCurrent;
    }

    public void setOutputCurrent(BigDecimal outputCurrent) {
        this.outputCurrent = outputCurrent;
    }

    public int getGunLineTemperature() {
        return gunLineTemperature;
    }

    public void setGunLineTemperature(int gunLineTemperature) {
        this.gunLineTemperature = gunLineTemperature;
    }

    public long getGunLineCode() {
        return gunLineCode;
    }

    public void setGunLineCode(long gunLineCode) {
        this.gunLineCode = gunLineCode;
    }

    public int getSoc() {
        return soc;
    }

    public void setSoc(int soc) {
        this.soc = soc;
    }

    public int getBatteryPackMaxTemperature() {
        return batteryPackMaxTemperature;
    }

    public void setBatteryPackMaxTemperature(int batteryPackMaxTemperature) {
        this.batteryPackMaxTemperature = batteryPackMaxTemperature;
    }

    public int getTotalChargeMin() {
        return totalChargeMin;
    }

    public void setTotalChargeMin(int totalChargeMin) {
        this.totalChargeMin = totalChargeMin;
    }

    public int getRemainMin() {
        return remainMin;
    }

    public void setRemainMin(int remainMin) {
        this.remainMin = remainMin;
    }

    public BigDecimal getChargeDegree() {
        return chargeDegree;
    }

    public void setChargeDegree(BigDecimal chargeDegree) {
        this.chargeDegree = chargeDegree;
    }

    public BigDecimal getLoseDegree() {
        return loseDegree;
    }

    public void setLoseDegree(BigDecimal loseDegree) {
        this.loseDegree = loseDegree;
    }

    public BigDecimal getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(BigDecimal chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public int getWarnCode() {
        return warnCode;
    }

    public void setWarnCode(int warnCode) {
        this.warnCode = warnCode;
    }

    public FixedRealTimeDataCmdMessage(int cmdCode, String pilecode) {
        super(cmdCode, pilecode);
    }
}
