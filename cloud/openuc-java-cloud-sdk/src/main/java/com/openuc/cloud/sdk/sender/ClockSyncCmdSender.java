package com.openuc.cloud.sdk.sender;

import com.openuc.cloud.sdk.common.Constants;
import com.openuc.cloud.sdk.request.IssueRequest;
import com.openuc.cloud.sdk.util.BCD;
import com.openuc.cloud.sdk.util.SeqNoUtil;
import com.openuc.cloud.sdk.util.TimeUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import java.util.Date;

import static com.openuc.cloud.sdk.common.Constants.CLOCK_SYNC_CMD;

/**
 * 发送时钟同步命令
 * @author zhongxin
 */
public class ClockSyncCmdSender extends ProtocolMessagerSender {

    @Override
    public void send(IssueRequest request, Channel channel) {
        ByteBuf msgBodyBuf = Unpooled.buffer(14);
        msgBodyBuf.writeBytes(BCD.strToBcd(request.getPileCode()));
        msgBodyBuf.writeBytes(TimeUtil.cp56time2aToBytes(new Date()));
        // 发送时钟同步命令
        send(build(msgBodyBuf, SeqNoUtil.getSeqNo(), 0, CLOCK_SYNC_CMD), request.getPileCode(), channel);
    }
}
