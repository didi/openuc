package com.openuc.cloud.sdk.sender;

import com.openuc.cloud.sdk.common.Constants;
import com.openuc.cloud.sdk.request.IssueRequest;
import com.openuc.cloud.sdk.util.BCD;
import com.openuc.cloud.sdk.util.SeqNoUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import static com.openuc.cloud.sdk.common.Constants.REBOOT_CMD;

/**
 * 发送远程重启命令
 * @author zhongxin
 */
public class RemoteRebootCmdSender extends ProtocolMessagerSender {

    @Override
    public void send(IssueRequest request, Channel channel) {
        ByteBuf msgBodyBuf = Unpooled.buffer();
        msgBodyBuf.writeBytes(BCD.strToBcd(request.getPileCode()));
        msgBodyBuf.writeByte(0x01);// 0x01：立即执行 0x02：空闲执行
        send(build(msgBodyBuf, SeqNoUtil.getSeqNo(), 0, REBOOT_CMD), request.getPileCode(), channel);
    }
}
