package com.openuc.cloud.sdk.sender;

import com.openuc.cloud.sdk.common.Constants;
import com.openuc.cloud.sdk.request.ChargeRecordReplyRequest;
import com.openuc.cloud.sdk.request.IssueRequest;
import com.openuc.cloud.sdk.util.BCDUtil;
import com.openuc.cloud.sdk.util.SeqNoUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import static com.openuc.cloud.sdk.common.Constants.TRADE_RECORD_ACK_CMD;

/**
 * 云平台交易记录确认应答
 * @author zhongxin
 */
public class ChargeRecordAckSender extends ProtocolMessagerSender {

    @Override
    public void send(IssueRequest request, Channel channel) {
        ChargeRecordReplyRequest chargeRecordReplyRequest = (ChargeRecordReplyRequest) request;
        ByteBuf msgBodyBuf = Unpooled.buffer(17);
        msgBodyBuf.writeBytes(BCDUtil.str2bcd(chargeRecordReplyRequest.getTradeOrderNo()));
        msgBodyBuf.writeByte(0x00);
        send(build(msgBodyBuf, SeqNoUtil.getSeqNo(), 0, TRADE_RECORD_ACK_CMD), request.getPileCode(), channel);
    }
}
