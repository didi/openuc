package com.openuc.cloud.sdk.message;

import java.util.Date;

public class ReplyClockCmdMessage extends CmdMessage{

    private Date curTime; //当前时间

    public Date getCurTime() {
        return curTime;
    }

    public void setCurTime(Date curTime) {
        this.curTime = curTime;
    }

    public ReplyClockCmdMessage(int cmdCode, String pilecode) {
        super(cmdCode, pilecode);
    }
}
