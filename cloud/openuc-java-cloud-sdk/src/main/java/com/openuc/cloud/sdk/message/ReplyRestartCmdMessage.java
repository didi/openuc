package com.openuc.cloud.sdk.message;

public class ReplyRestartCmdMessage extends CmdMessage {
    private int result; //结果

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
    public ReplyRestartCmdMessage(int cmdCode, String pilecode) {
        super(cmdCode, pilecode);
    }
}
