package com.openuc.cloud.sdk.sender;

import com.openuc.cloud.sdk.common.Constants;
import com.openuc.cloud.sdk.request.IssueRequest;
import com.openuc.cloud.sdk.request.UpgradeIssueRequest;
import com.openuc.cloud.sdk.util.AsciiUtil;
import com.openuc.cloud.sdk.util.BCD;
import com.openuc.cloud.sdk.util.SeqNoUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import static com.openuc.cloud.sdk.common.Constants.UPDATE_CMD;

/**
 * 远程升级命令发送器
 * 该类负责将远程升级请求转换为协议消息并发送到指定的充电桩。
 * @author zhongxin
 */
public class RemoteUpdateCmdSender extends ProtocolMessagerSender {

    @Override
    public void send(IssueRequest request, Channel channel) {
        UpgradeIssueRequest upgradeIssueRequest = (UpgradeIssueRequest) request;
        ByteBuf msgBodyBuf = Unpooled.buffer();
        msgBodyBuf.writeBytes(BCD.strToBcd(upgradeIssueRequest.getPileCode()));
        msgBodyBuf.writeByte(1);
        msgBodyBuf.writeShortLE(0x0f00);
        msgBodyBuf.writeBytes(AsciiUtil.asciiToBytes(upgradeIssueRequest.getIpAddress(), 16));
        msgBodyBuf.writeShortLE(80);
        msgBodyBuf.writeBytes(new byte[16]);
        msgBodyBuf.writeBytes(new byte[16]);
        msgBodyBuf.writeBytes(AsciiUtil.asciiToBytes(upgradeIssueRequest.getPath(), 32));
        msgBodyBuf.writeByte(1);
        msgBodyBuf.writeByte(30);
        send(build(msgBodyBuf, SeqNoUtil.getSeqNo(), 0, UPDATE_CMD), request.getPileCode(), channel);
    }
}
