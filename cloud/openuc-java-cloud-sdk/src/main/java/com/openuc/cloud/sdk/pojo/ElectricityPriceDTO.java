package com.openuc.cloud.sdk.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class ElectricityPriceDTO implements Serializable {

    /**
     * 场站ID
     */
    private String stationId;
    /**
     * 桩号
     */
    private String pileCode;
    /**
     * 尖电费费率
     */
    private BigDecimal jianElectricityFee;
    /**
     * 峰电费费率
     */
    private BigDecimal fengElectricityFee;
    /**
     * 平电费费率
     */
    private BigDecimal pingElectricityFee;
    /**
     * 谷电费费率
     */
    private BigDecimal guElectricityFee;
    /**
     * 深谷电费费率
     */
    private BigDecimal deepGuElectricityFee;
    /**
     * 尖服务费费率
     */
    private BigDecimal jianServiceFee;
    /**
     * 峰服务费费率
     */
    private BigDecimal fengServiceFee;
    /**
     * 平服务费费率
     */
    private BigDecimal pingServiceFee;
    /**
     * 谷服务费费率
     */
    private BigDecimal guServiceFee;
    /**
     * 深谷服务费费率
     */
    private BigDecimal deepGuServiceFee;
    /**
     * 计费时段信息明细
     */
    private List<ElectricityPeriodTimeDTO> periodTimeDTOList;

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getPileCode() {
        return pileCode;
    }

    public void setPileCode(String pileCode) {
        this.pileCode = pileCode;
    }

    public BigDecimal getJianElectricityFee() {
        return jianElectricityFee;
    }

    public void setJianElectricityFee(BigDecimal jianElectricityFee) {
        this.jianElectricityFee = jianElectricityFee;
    }

    public BigDecimal getFengElectricityFee() {
        return fengElectricityFee;
    }

    public void setFengElectricityFee(BigDecimal fengElectricityFee) {
        this.fengElectricityFee = fengElectricityFee;
    }

    public BigDecimal getPingElectricityFee() {
        return pingElectricityFee;
    }

    public void setPingElectricityFee(BigDecimal pingElectricityFee) {
        this.pingElectricityFee = pingElectricityFee;
    }

    public BigDecimal getGuElectricityFee() {
        return guElectricityFee;
    }

    public void setGuElectricityFee(BigDecimal guElectricityFee) {
        this.guElectricityFee = guElectricityFee;
    }

    public BigDecimal getDeepGuElectricityFee() {
        return deepGuElectricityFee;
    }

    public void setDeepGuElectricityFee(BigDecimal deepGuElectricityFee) {
        this.deepGuElectricityFee = deepGuElectricityFee;
    }

    public BigDecimal getJianServiceFee() {
        return jianServiceFee;
    }

    public void setJianServiceFee(BigDecimal jianServiceFee) {
        this.jianServiceFee = jianServiceFee;
    }

    public BigDecimal getFengServiceFee() {
        return fengServiceFee;
    }

    public void setFengServiceFee(BigDecimal fengServiceFee) {
        this.fengServiceFee = fengServiceFee;
    }

    public BigDecimal getPingServiceFee() {
        return pingServiceFee;
    }

    public void setPingServiceFee(BigDecimal pingServiceFee) {
        this.pingServiceFee = pingServiceFee;
    }

    public BigDecimal getGuServiceFee() {
        return guServiceFee;
    }

    public void setGuServiceFee(BigDecimal guServiceFee) {
        this.guServiceFee = guServiceFee;
    }

    public BigDecimal getDeepGuServiceFee() {
        return deepGuServiceFee;
    }

    public void setDeepGuServiceFee(BigDecimal deepGuServiceFee) {
        this.deepGuServiceFee = deepGuServiceFee;
    }

    public List<ElectricityPeriodTimeDTO> getPeriodTimeDTOList() {
        return periodTimeDTOList;
    }

    public void setPeriodTimeDTOList(List<ElectricityPeriodTimeDTO> periodTimeDTOList) {
        this.periodTimeDTOList = periodTimeDTOList;
    }
}

