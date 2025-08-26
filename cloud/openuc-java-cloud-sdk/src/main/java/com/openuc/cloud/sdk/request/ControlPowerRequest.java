package com.openuc.cloud.sdk.request;


import java.util.Map;

public class ControlPowerRequest extends IssueRequest {

    public ControlPowerRequest(String pileCode, Integer gunCode) {
        super(pileCode, gunCode);
    }

    /**
     * 最大功率的百分比, 最大是1，最大是100
     */
    private Integer percent;
    /**
     * 扩展参数
     */
    private Map<String, Object> extMap;

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    public Map<String, Object> getExtMap() {
        return extMap;
    }

    public void setExtMap(Map<String, Object> extMap) {
        this.extMap = extMap;
    }
}
