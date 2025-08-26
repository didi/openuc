package com.openuc.cloud.sdk.decoder.cmd;

import com.openuc.cloud.sdk.message.ApplicationMessage;
import com.openuc.cloud.sdk.message.CmdMessage;

/**
 * 指令处理器
 * @author fangshun
 */
public interface CmdDecoder {
    /**
     * 指令码
     * @return
     */
    public int getCmdCode();

    /**
     * 处理指令
     * @param message 应用层帧数据
     * @return 返回解析的数据指令
     */
    public CmdMessage process(ApplicationMessage message);
}
