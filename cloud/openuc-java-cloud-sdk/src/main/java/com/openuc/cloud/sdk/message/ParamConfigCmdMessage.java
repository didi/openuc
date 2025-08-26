package com.openuc.cloud.sdk.message;

import java.math.BigDecimal;

public class ParamConfigCmdMessage extends CmdMessage {

    private String chargeFlowSn;//交易流水号
    private int gunCode; //枪号

    private BigDecimal bmsSingleAllowMaxVoltage; //BMS 单体动⼒蓄电池最⾼允许 充电电压

    private BigDecimal bmsAllowMaxCurrent; //BMS 最⾼允许充电电流

    private BigDecimal bmsTotalPower; //BMS 动⼒蓄电池标称总能量

    private BigDecimal bmsAllowMaxVoltage; //BMS 最⾼允许充电总电压

    private int bmsAllowMaxTemperature; //BMS 最⾼允许温度

    private int bmsCurrentSoc; //BMS 整⻋动⼒蓄电池荷电状态

    private int bmsCurrentVoltage; //BMS 整⻋动⼒蓄电池当前电池电压

    private BigDecimal bmsMaxOutputVoltage; //电桩最⾼输出电压

    private BigDecimal bmsMinOutputVoltage; //电桩最低输出电压

    private BigDecimal bmsMaxOutputCurrent; //电桩最⼤输出电流

    private BigDecimal bmsMinOutputCurrent;//电桩最⼩输出电流


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

    public BigDecimal getBmsSingleAllowMaxVoltage() {
        return bmsSingleAllowMaxVoltage;
    }

    public void setBmsSingleAllowMaxVoltage(BigDecimal bmsSingleAllowMaxVoltage) {
        this.bmsSingleAllowMaxVoltage = bmsSingleAllowMaxVoltage;
    }

    public BigDecimal getBmsAllowMaxCurrent() {
        return bmsAllowMaxCurrent;
    }

    public void setBmsAllowMaxCurrent(BigDecimal bmsAllowMaxCurrent) {
        this.bmsAllowMaxCurrent = bmsAllowMaxCurrent;
    }

    public BigDecimal getBmsTotalPower() {
        return bmsTotalPower;
    }

    public void setBmsTotalPower(BigDecimal bmsTotalPower) {
        this.bmsTotalPower = bmsTotalPower;
    }

    public BigDecimal getBmsAllowMaxVoltage() {
        return bmsAllowMaxVoltage;
    }

    public void setBmsAllowMaxVoltage(BigDecimal bmsAllowMaxVoltage) {
        this.bmsAllowMaxVoltage = bmsAllowMaxVoltage;
    }

    public int getBmsAllowMaxTemperature() {
        return bmsAllowMaxTemperature;
    }

    public void setBmsAllowMaxTemperature(int bmsAllowMaxTemperature) {
        this.bmsAllowMaxTemperature = bmsAllowMaxTemperature;
    }

    public int getBmsCurrentSoc() {
        return bmsCurrentSoc;
    }

    public void setBmsCurrentSoc(int bmsCurrentSoc) {
        this.bmsCurrentSoc = bmsCurrentSoc;
    }

    public int getBmsCurrentVoltage() {
        return bmsCurrentVoltage;
    }

    public void setBmsCurrentVoltage(int bmsCurrentVoltage) {
        this.bmsCurrentVoltage = bmsCurrentVoltage;
    }

    public BigDecimal getBmsMaxOutputVoltage() {
        return bmsMaxOutputVoltage;
    }

    public void setBmsMaxOutputVoltage(BigDecimal bmsMaxOutputVoltage) {
        this.bmsMaxOutputVoltage = bmsMaxOutputVoltage;
    }

    public BigDecimal getBmsMinOutputVoltage() {
        return bmsMinOutputVoltage;
    }

    public void setBmsMinOutputVoltage(BigDecimal bmsMinOutputVoltage) {
        this.bmsMinOutputVoltage = bmsMinOutputVoltage;
    }

    public BigDecimal getBmsMaxOutputCurrent() {
        return bmsMaxOutputCurrent;
    }

    public void setBmsMaxOutputCurrent(BigDecimal bmsMaxOutputCurrent) {
        this.bmsMaxOutputCurrent = bmsMaxOutputCurrent;
    }

    public BigDecimal getBmsMinOutputCurrent() {
        return bmsMinOutputCurrent;
    }

    public void setBmsMinOutputCurrent(BigDecimal bmsMinOutputCurrent) {
        this.bmsMinOutputCurrent = bmsMinOutputCurrent;
    }

    public ParamConfigCmdMessage(int cmdCode, String pilecode) {
        super(cmdCode, pilecode);
    }
}
