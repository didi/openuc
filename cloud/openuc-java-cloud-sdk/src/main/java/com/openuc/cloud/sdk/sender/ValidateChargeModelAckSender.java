package com.openuc.cloud.sdk.sender;

import com.openuc.cloud.sdk.request.IssueRequest;
import com.openuc.cloud.sdk.request.ValidateChargeRequest;
import com.openuc.cloud.sdk.util.BCDUtil;
import com.openuc.cloud.sdk.util.SeqNoUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import static com.openuc.cloud.sdk.common.Constants.CHARGE_BILLING_MODEL_VERIFY_ACK_CMD;

/**
 * 计费模型验证请求应答
 * @author zhongxin
 */
public class ValidateChargeModelAckSender extends ProtocolMessagerSender {

    @Override
    public void send(IssueRequest request, Channel channel) {

        ValidateChargeRequest validateChargeRequest = (ValidateChargeRequest) request;
        ByteBuf respBodyBuf = Unpooled.buffer(10);
        respBodyBuf.writeBytes(BCDUtil.str2bcd(validateChargeRequest.getPileCode()));
        respBodyBuf.writeBytes(BCDUtil.str2bcd(validateChargeRequest.getChargeModelNo()));
        respBodyBuf.writeByte(0);

        send(build(respBodyBuf, SeqNoUtil.getSeqNo(), 0, CHARGE_BILLING_MODEL_VERIFY_ACK_CMD), validateChargeRequest.getPileCode(), channel);
    }
}
