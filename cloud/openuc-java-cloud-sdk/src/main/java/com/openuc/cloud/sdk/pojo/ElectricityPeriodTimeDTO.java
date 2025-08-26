package com.openuc.cloud.sdk.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class ElectricityPeriodTimeDTO implements Serializable {

    /**
     *   开始小时0-24小时
     */
    private Integer startHour;
    /**
     *   开始分钟
     */
    private Integer startMin;
    /**
     *  结速小时0-24小时
     */
    private Integer endHour;
    /**
     *   结束分钟
     */
    private Integer endMin;
    /**
     *  电费费率
     */
    private BigDecimal electricityFee;
    /**
     * 服务费费率
     */
    private BigDecimal serviceFree;
    /**
     *  费率标示 尖 1 峰 2 平 3 谷 4
     */
    private Integer feeMark;

    public Integer getStartHour() {
        return startHour;
    }

    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
    }

    public Integer getStartMin() {
        return startMin;
    }

    public void setStartMin(Integer startMin) {
        this.startMin = startMin;
    }

    public Integer getEndHour() {
        return endHour;
    }

    public void setEndHour(Integer endHour) {
        this.endHour = endHour;
    }

    public Integer getEndMin() {
        return endMin;
    }

    public void setEndMin(Integer endMin) {
        this.endMin = endMin;
    }

    public BigDecimal getElectricityFee() {
        return electricityFee;
    }

    public void setElectricityFee(BigDecimal electricityFee) {
        this.electricityFee = electricityFee;
    }

    public BigDecimal getServiceFree() {
        return serviceFree;
    }

    public void setServiceFree(BigDecimal serviceFree) {
        this.serviceFree = serviceFree;
    }

    public Integer getFeeMark() {
        return feeMark;
    }

    public void setFeeMark(Integer feeMark) {
        this.feeMark = feeMark;
    }
}
