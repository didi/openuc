package com.openuc.cloud.sdk.message;

import java.math.BigDecimal;

public class BmsDemandOutputCmdMessage extends CmdMessage {

    private String chargeFlowSn; //交易流水号

    private int gunCode; //枪号
    private BigDecimal bmsDemandVoltage; //BMS 电压需求

    private BigDecimal bmsDemandCurrent; //BMS 电流需求 已经-400A 偏移量

    private int bmsChargeModel; //BMS 充电模式

    private BigDecimal bmsChargeMeasureVoltage; //BMS 充电电压测量值

    private BigDecimal bmsChargeMeasureCurrent; //BMS 充电电流测量值 已经-400A 偏移量

    private int batteryVolAndSerialNoBytes; //BMS 最⾼单体动⼒蓄电池电压及组号

    private int bmsCurrentSoc; //BMS 当前荷电状态SOC

    private int bmsRemainingChargeTimeMin; //BMS 估算剩余充电时间

    private BigDecimal bmsPileOutputVoltage; //电桩电压输出值

    private BigDecimal bmsPileOutputCurrent; //电桩电流输出值

    private int chargeTimeMin;  //累计充电时间

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

    public BigDecimal getBmsDemandVoltage() {
        return bmsDemandVoltage;
    }

    public void setBmsDemandVoltage(BigDecimal bmsDemandVoltage) {
        this.bmsDemandVoltage = bmsDemandVoltage;
    }

    public BigDecimal getBmsDemandCurrent() {
        return bmsDemandCurrent;
    }

    public void setBmsDemandCurrent(BigDecimal bmsDemandCurrent) {
        this.bmsDemandCurrent = bmsDemandCurrent;
    }

    public int getBmsChargeModel() {
        return bmsChargeModel;
    }

    public void setBmsChargeModel(int bmsChargeModel) {
        this.bmsChargeModel = bmsChargeModel;
    }

    public BigDecimal getBmsChargeMeasureVoltage() {
        return bmsChargeMeasureVoltage;
    }

    public void setBmsChargeMeasureVoltage(BigDecimal bmsChargeMeasureVoltage) {
        this.bmsChargeMeasureVoltage = bmsChargeMeasureVoltage;
    }

    public BigDecimal getBmsChargeMeasureCurrent() {
        return bmsChargeMeasureCurrent;
    }

    public void setBmsChargeMeasureCurrent(BigDecimal bmsChargeMeasureCurrent) {
        this.bmsChargeMeasureCurrent = bmsChargeMeasureCurrent;
    }

    public int getBatteryVolAndSerialNoBytes() {
        return batteryVolAndSerialNoBytes;
    }

    public void setBatteryVolAndSerialNoBytes(int batteryVolAndSerialNoBytes) {
        this.batteryVolAndSerialNoBytes = batteryVolAndSerialNoBytes;
    }

    public int getBmsCurrentSoc() {
        return bmsCurrentSoc;
    }

    public void setBmsCurrentSoc(int bmsCurrentSoc) {
        this.bmsCurrentSoc = bmsCurrentSoc;
    }

    public int getBmsRemainingChargeTimeMin() {
        return bmsRemainingChargeTimeMin;
    }

    public void setBmsRemainingChargeTimeMin(int bmsRemainingChargeTimeMin) {
        this.bmsRemainingChargeTimeMin = bmsRemainingChargeTimeMin;
    }

    public BigDecimal getBmsPileOutputVoltage() {
        return bmsPileOutputVoltage;
    }

    public void setBmsPileOutputVoltage(BigDecimal bmsPileOutputVoltage) {
        this.bmsPileOutputVoltage = bmsPileOutputVoltage;
    }

    public BigDecimal getBmsPileOutputCurrent() {
        return bmsPileOutputCurrent;
    }

    public void setBmsPileOutputCurrent(BigDecimal bmsPileOutputCurrent) {
        this.bmsPileOutputCurrent = bmsPileOutputCurrent;
    }

    public int getChargeTimeMin() {
        return chargeTimeMin;
    }

    public void setChargeTimeMin(int chargeTimeMin) {
        this.chargeTimeMin = chargeTimeMin;
    }

    public BmsDemandOutputCmdMessage(int cmdCode, String pilecode) {
        super(cmdCode, pilecode);
    }
}
