package com.openuc.cloud.sdk.message;

public class ReplyControlPowerCmdMessage extends CmdMessage{

    private int result; //结果

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public ReplyControlPowerCmdMessage(int cmdCode, String pilecode) {
        super(cmdCode, pilecode);
    }
}
