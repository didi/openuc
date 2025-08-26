package com.openuc.cloud.sdk.sender;

import com.openuc.cloud.sdk.common.Constants;
import com.openuc.cloud.sdk.request.ControlPowerRequest;
import com.openuc.cloud.sdk.request.IssueRequest;
import com.openuc.cloud.sdk.util.BCD;
import com.openuc.cloud.sdk.util.SeqNoUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import static com.openuc.cloud.sdk.common.Constants.CHARGE_WORK_PARAM_SET_CMD;

/**
 * 发送控制功率命令
 * @author zhongxin
 */
public class ControlPowerCmdSender extends ProtocolMessagerSender{
    @Override
    public void send(IssueRequest request, Channel channel) {
        ControlPowerRequest controlPowerRequest = (ControlPowerRequest) request;

        ByteBuf respBody = Unpooled.buffer();
        respBody.writeBytes(BCD.strToBcd(controlPowerRequest.getPileCode()));
        respBody.writeByte(0);
        respBody.writeByte(controlPowerRequest.getPercent());

        send(build(respBody, SeqNoUtil.getSeqNo(), 0, CHARGE_WORK_PARAM_SET_CMD), controlPowerRequest.getPileCode(), channel);
    }
}
