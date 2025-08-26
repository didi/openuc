package com.openuc.cloud.sdk.message;

/**
 * 指令数据消息体
 * @author fangshun
 */
public class CmdMessage {
    /**
     * 指令编号
     */
    private int cmdCode;
    /**
     * 充电桩编号
     */
    private String pileCode;

    public CmdMessage(int cmdCode, String pilecode) {
        this.cmdCode = cmdCode;
        this.pileCode = pilecode;
    }

    public int getCmdCode() {
        return cmdCode;
    }

    public String getPileCode() {
        return pileCode;
    }

}
