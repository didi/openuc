package com.openuc.cloud.sdk.message;

public class HeartbeatCmdMessage extends CmdMessage {


    private int gunCode; //枪号


    private int gunState; //枪状态


    public HeartbeatCmdMessage(int cmdCode, String pilecode) {
        super(cmdCode, pilecode);
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
}
