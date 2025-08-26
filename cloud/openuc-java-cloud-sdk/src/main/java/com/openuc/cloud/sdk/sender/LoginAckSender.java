package com.openuc.cloud.sdk.sender;

import com.openuc.cloud.sdk.common.Constants;
import com.openuc.cloud.sdk.request.IssueRequest;
import com.openuc.cloud.sdk.util.BCDUtil;
import com.openuc.cloud.sdk.util.SeqNoUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import static com.openuc.cloud.sdk.common.Constants.LOGIN_ACK_CMD;

/**
 * 登录应答发送器
 * @author zhongxin
 */
public class LoginAckSender extends ProtocolMessagerSender{
    @Override
    public void send(IssueRequest request, Channel channel) {
        ByteBuf respBodyBuf = Unpooled.buffer(8);
        respBodyBuf.writeBytes(BCDUtil.str2bcd(request.getPileCode()));
        respBodyBuf.writeByte(0x00);
        send(build(respBodyBuf, SeqNoUtil.getSeqNo(), 0, LOGIN_ACK_CMD), request.getPileCode(), channel);
    }
}
