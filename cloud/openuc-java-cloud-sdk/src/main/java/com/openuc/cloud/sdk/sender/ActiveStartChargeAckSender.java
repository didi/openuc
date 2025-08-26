package com.openuc.cloud.sdk.sender;

import com.openuc.cloud.sdk.common.Constants;
import com.openuc.cloud.sdk.request.ActiveStartChargeRequest;
import com.openuc.cloud.sdk.request.IssueRequest;
import com.openuc.cloud.sdk.util.BCDUtil;
import com.openuc.cloud.sdk.util.SeqNoUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import static com.openuc.cloud.sdk.common.Constants.START_CHARGE_ACK_CMD;

/**
 * 平台确认启动充电应答
 * @author zhongxin
 */
public class ActiveStartChargeAckSender extends ProtocolMessagerSender {

    @Override
    public void send(IssueRequest request, Channel channel) {

        ActiveStartChargeRequest activeStartChargeRequest = (ActiveStartChargeRequest) request;
        ByteBuf respBody = Unpooled.buffer();
        respBody.writeBytes(BCDUtil.strToBCDAndFillLeft(String.valueOf(activeStartChargeRequest.getTradeOrderNoExt()), 32, "0"));
        respBody.writeBytes(BCDUtil.str2bcd(request.getPileCode()));
        respBody.writeBytes(BCDUtil.str2bcd(String.valueOf(request.getGunCode())));
        respBody.writeBytes(activeStartChargeRequest.getChargeCardNo().getBytes());
        // 充电金额阈值
        respBody.writeIntLE(99999);
        respBody.writeByte(1 ); // 0失败 1成功
        respBody.writeByte(0 ); // 1账户不存在

        send(build(respBody, SeqNoUtil.getSeqNo(), 0, START_CHARGE_ACK_CMD), activeStartChargeRequest.getPileCode(), channel);
    }
}
