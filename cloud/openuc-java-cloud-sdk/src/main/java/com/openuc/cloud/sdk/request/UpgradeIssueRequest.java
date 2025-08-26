package com.openuc.cloud.sdk.request;

import java.util.Map;
public class UpgradeIssueRequest extends IssueRequest {

    public UpgradeIssueRequest(String pileCode, Integer gunCode) {
        super(pileCode, gunCode);
    }


    /**
     * 远程升级指令内容
     */
    private String upgradeData;
    /**
     * 远程升级指令扩展内容
     */
    private Map<String, Object> extDataMap;

    /**
     * 远程升级地址
     */
    private String ipAddress;

    /**
     * 远程升级端口
     */
    private Integer port;

    /**
     * 远程升级路径
     */
    private String path;

    public String getUpgradeData() {
        return upgradeData;
    }

    public void setUpgradeData(String upgradeData) {
        this.upgradeData = upgradeData;
    }

    public Map<String, Object> getExtDataMap() {
        return extDataMap;
    }

    public void setExtDataMap(Map<String, Object> extDataMap) {
        this.extDataMap = extDataMap;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
