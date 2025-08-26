package com.openuc.cloud.sdk.sender;

import com.openuc.cloud.sdk.request.IssueRequest;
import com.openuc.cloud.sdk.util.BCDUtil;
import com.openuc.cloud.sdk.util.SeqNoUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import static com.openuc.cloud.sdk.common.Constants.STOP_CHARGE_CMD;

/**
 * 停止充电命令发送器
 * @author zhongxin
 */
public class StopChargeCmdSender extends ProtocolMessagerSender{
    @Override
    public void send(IssueRequest request, Channel channel) {
        // 构建消息体
        ByteBuf msgBodyBuf = Unpooled.buffer(8);
        // 填充消息体内容...
        msgBodyBuf.writeBytes(BCDUtil.str2bcd(request.getPileCode()));
        msgBodyBuf.writeBytes(BCDUtil.str2bcd(request.getGunCode() < 10 ? "0" + request.getGunCode() : String.valueOf(request.getGunCode())));
        // 发送消息
        send(build(msgBodyBuf, SeqNoUtil.getSeqNo(), 0, STOP_CHARGE_CMD), request.getPileCode(), channel);
    }
}
