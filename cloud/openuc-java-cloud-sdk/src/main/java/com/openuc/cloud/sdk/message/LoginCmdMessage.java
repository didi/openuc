package com.openuc.cloud.sdk.message;

/**
 * @author fangshun
 */
public class LoginCmdMessage extends CmdMessage {

    private int pileType;//桩类型(0直流1交流)
    private int gunsNum;//充电枪数量
    private short protocolVersion;//通信协议版本
    private String programVersion;//程序版本
    private short networkLinkType;//网络链接类型
    private String simCard;//Sim 卡
    private short  operatorType;//运营商类型
    public LoginCmdMessage(int cmdCode, String pilecode) {
        super(cmdCode, pilecode);
    }

    public int getPileType() {
        return pileType;
    }

    public void setPileType(int pileType) {
        this.pileType = pileType;
    }

    public int getGunsNum() {
        return gunsNum;
    }

    public void setGunsNum(int gunsNum) {
        this.gunsNum = gunsNum;
    }

    public short getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(short protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public String getProgramVersion() {
        return programVersion;
    }

    public void setProgramVersion(String programVersion) {
        this.programVersion = programVersion;
    }

    public short getNetworkLinkType() {
        return networkLinkType;
    }

    public void setNetworkLinkType(short networkLinkType) {
        this.networkLinkType = networkLinkType;
    }

    public String getSimCard() {
        return simCard;
    }

    public void setSimCard(String simCard) {
        this.simCard = simCard;
    }

    public short getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(short operatorType) {
        this.operatorType = operatorType;
    }
}
