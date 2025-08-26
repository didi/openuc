package com.openuc.cloud.sdk.sender;

import com.openuc.cloud.sdk.common.Constants;
import com.openuc.cloud.sdk.request.IssueRequest;
import com.openuc.cloud.sdk.request.ParallerChargeReplyRequest;
import com.openuc.cloud.sdk.util.BCDUtil;
import com.openuc.cloud.sdk.util.SeqNoUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import static com.openuc.cloud.sdk.common.Constants.START_CHARGE_PLATFORM_ACK_CMD;

/**
 * 平台确认启动充电应答发送器
 * @author zhongxin
 */
public class ParallelChargeAckSender extends ProtocolMessagerSender {

    @Override
    public void send(IssueRequest request, Channel channel) {

        ParallerChargeReplyRequest parallerChargeReplyRequest = (ParallerChargeReplyRequest) request;

        ByteBuf respBody = Unpooled.buffer();
        respBody.writeBytes(BCDUtil.strToBCDAndFillLeft(String.valueOf(parallerChargeReplyRequest.getTradeOrderNoExt()), 32, "0"));
        respBody.writeBytes(BCDUtil.str2bcd(request.getPileCode()));
        respBody.writeByte(request.getGunCode());
        respBody.writeBytes(parallerChargeReplyRequest.getChargeCardNo().getBytes());
        // 充电金额阈值
        respBody.writeIntLE(99999);
        respBody.writeByte(1 ); // 0失败 1成功
        respBody.writeByte( 0); // 1账户不存在
        respBody.writeBytes(BCDUtil.strToBCDAndFillLeft(String.valueOf(parallerChargeReplyRequest.getTradeOrderNoExt()), 6, "0"));

        send(build(respBody, SeqNoUtil.getSeqNo(), 0, START_CHARGE_PLATFORM_ACK_CMD), parallerChargeReplyRequest.getPileCode(), channel);

    }

}
