package com.openuc.cloud.sdk.sender;

import com.openuc.cloud.sdk.request.IssueRequest;
import com.openuc.cloud.sdk.request.StartChargeRequest;
import com.openuc.cloud.sdk.util.BCD;
import com.openuc.cloud.sdk.util.BCDUtil;
import com.openuc.cloud.sdk.util.SeqNoUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import static com.openuc.cloud.sdk.common.Constants.START_CHARGE_CMD;

/**
 * @author fangshun
 */
public class StartChargeCmdSender extends ProtocolMessagerSender {

    @Override
    public void send(IssueRequest request, Channel channel){

        StartChargeRequest startChargeRequest = (StartChargeRequest) request;

        ByteBuf msgBodyBuf = Unpooled.buffer(44);
        // 交易流水号
        msgBodyBuf.writeBytes(BCDUtil.strToBCDAndFillLeft(startChargeRequest.getTradeOrderNoExt().toString(), 32, "0"));
        // 桩编码
        msgBodyBuf.writeBytes(BCD.strToBcd(request.getPileCode()));
        // 枪号
        msgBodyBuf.writeBytes(BCDUtil.str2bcd(request.getGunCode() < 10 ? "0" + request.getGunCode() : String.valueOf(request.getGunCode())));
        // 逻辑卡号 BCD码
        msgBodyBuf.writeBytes(BCDUtil.strToBCDAndFillLeft(startChargeRequest.getTradeOrderNoExt().toString(), 16, "0"));
        // 物理卡号
        msgBodyBuf.writeBytes(BCDUtil.strToBCDAndFillLeft(startChargeRequest.getTradeOrderNoExt().toString(), 16, "0"));
        // 账户余额
        msgBodyBuf.writeIntLE(startChargeRequest.getAmount());

        //发送
        send(build(msgBodyBuf, SeqNoUtil.getSeqNo(), 0, START_CHARGE_CMD), request.getPileCode(), channel);
    }
}
