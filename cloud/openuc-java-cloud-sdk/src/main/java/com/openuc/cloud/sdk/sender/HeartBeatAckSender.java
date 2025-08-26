package com.openuc.cloud.sdk.sender;

import com.openuc.cloud.sdk.common.Constants;
import com.openuc.cloud.sdk.request.IssueRequest;
import com.openuc.cloud.sdk.util.BCDUtil;
import com.openuc.cloud.sdk.util.SeqNoUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import static com.openuc.cloud.sdk.common.Constants.HEARTBEAT_CMD;

/**
 * 心跳应答发送器
 * @author zhongxin
 */
public class HeartBeatAckSender extends ProtocolMessagerSender {

    @Override
    public void send(IssueRequest request, Channel channel) {

        ByteBuf respBodyBuf = Unpooled.buffer(9);
        respBodyBuf.writeBytes(BCDUtil.str2bcd(request.getPileCode()));
        respBodyBuf.writeBytes(BCDUtil.str2bcd(String.valueOf(request.getGunCode())));
        respBodyBuf.writeByte(0);

        send(build(respBodyBuf, SeqNoUtil.getSeqNo(), 0, HEARTBEAT_CMD), request.getPileCode(), channel);
    }
}
