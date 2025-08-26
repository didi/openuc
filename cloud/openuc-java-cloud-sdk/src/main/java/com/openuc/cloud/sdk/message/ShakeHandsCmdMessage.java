package com.openuc.cloud.sdk.message;

import java.math.BigDecimal;

public class ShakeHandsCmdMessage extends CmdMessage {

    private String chargeFlowSn; //交易流水号

    private int gunNo; //枪号

    private int bmsConnectVersion; //BMS 通信协议版本号

    private int bmsBatteryType; //BMS 电池类型

    private BigDecimal bmsPowerCapacity; //BMS 整⻋动⼒蓄电池系统额定容量

    private BigDecimal bmsPowerMaxVoltage; //BMS 整⻋动⼒蓄电池系统额定 总电压

    private String bmsFactory;//BMS 电池⽣产⼚商名称

    private long bmsSerialNo;//BMS 电池组序号

    private int bmsCreateYear; //BMS 电池组⽣产⽇期年

    private int bmsCreateMonth; //BMS 电池组⽣产⽇期⽉

    private int bmsCreateDay; //BMS 电池组⽣产⽇期⽇

    private int bmsChargeCount; //BMS 电池组充电次数

    private int bmsPropertyRightLabel; //BMS 电池组产权标识

    private int reserved; //预留

    private String carVin; //BMS ⻋辆识别码

    private long bmsSoftVersionB; //BMS 软件版本号

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

    public int getBmsConnectVersion() {
        return bmsConnectVersion;
    }

    public void setBmsConnectVersion(int bmsConnectVersion) {
        this.bmsConnectVersion = bmsConnectVersion;
    }

    public int getBmsBatteryType() {
        return bmsBatteryType;
    }

    public void setBmsBatteryType(int bmsBatteryType) {
        this.bmsBatteryType = bmsBatteryType;
    }

    public BigDecimal getBmsPowerCapacity() {
        return bmsPowerCapacity;
    }

    public void setBmsPowerCapacity(BigDecimal bmsPowerCapacity) {
        this.bmsPowerCapacity = bmsPowerCapacity;
    }

    public BigDecimal getBmsPowerMaxVoltage() {
        return bmsPowerMaxVoltage;
    }

    public void setBmsPowerMaxVoltage(BigDecimal bmsPowerMaxVoltage) {
        this.bmsPowerMaxVoltage = bmsPowerMaxVoltage;
    }

    public String getBmsFactory() {
        return bmsFactory;
    }

    public void setBmsFactory(String bmsFactory) {
        this.bmsFactory = bmsFactory;
    }

    public long getBmsSerialNo() {
        return bmsSerialNo;
    }

    public void setBmsSerialNo(long bmsSerialNo) {
        this.bmsSerialNo = bmsSerialNo;
    }

    public int getBmsCreateYear() {
        return bmsCreateYear;
    }

    public void setBmsCreateYear(int bmsCreateYear) {
        this.bmsCreateYear = bmsCreateYear;
    }

    public int getBmsCreateMonth() {
        return bmsCreateMonth;
    }

    public void setBmsCreateMonth(int bmsCreateMonth) {
        this.bmsCreateMonth = bmsCreateMonth;
    }

    public int getBmsCreateDay() {
        return bmsCreateDay;
    }

    public void setBmsCreateDay(int bmsCreateDay) {
        this.bmsCreateDay = bmsCreateDay;
    }

    public int getBmsChargeCount() {
        return bmsChargeCount;
    }

    public void setBmsChargeCount(int bmsChargeCount) {
        this.bmsChargeCount = bmsChargeCount;
    }

    public int getBmsPropertyRightLabel() {
        return bmsPropertyRightLabel;
    }

    public void setBmsPropertyRightLabel(int bmsPropertyRightLabel) {
        this.bmsPropertyRightLabel = bmsPropertyRightLabel;
    }

    public int getReserved() {
        return reserved;
    }

    public void setReserved(int reserved) {
        this.reserved = reserved;
    }

    public String getCarVin() {
        return carVin;
    }

    public void setCarVin(String carVin) {
        this.carVin = carVin;
    }

    public long getBmsSoftVersionB() {
        return bmsSoftVersionB;
    }

    public void setBmsSoftVersionB(long bmsSoftVersionB) {
        this.bmsSoftVersionB = bmsSoftVersionB;
    }

    public ShakeHandsCmdMessage(int cmdCode, String pilecode) {
        super(cmdCode, pilecode);
    }
}
