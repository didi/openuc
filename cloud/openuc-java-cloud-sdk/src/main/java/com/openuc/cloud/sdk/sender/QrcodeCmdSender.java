package com.openuc.cloud.sdk.sender;

import com.openuc.cloud.sdk.common.Constants;
import com.openuc.cloud.sdk.request.IssueRequest;
import com.openuc.cloud.sdk.request.QrCodeRequest;
import com.openuc.cloud.sdk.util.AsciiUtil;
import com.openuc.cloud.sdk.util.BCD;
import com.openuc.cloud.sdk.util.SeqNoUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import static com.openuc.cloud.sdk.common.Constants.QR_CODE_CMD;

/**
 * 二维码命令发送器
 * 该类负责将二维码请求转换为协议消息并发送到指定的充电桩。
 * @author zhongxin
 */
public class QrcodeCmdSender extends ProtocolMessagerSender {

    @Override
    public void send(IssueRequest request, Channel channel) {
        QrCodeRequest qrCodeRequest = (QrCodeRequest) request;
        ByteBuf respBody = Unpooled.buffer();
        String pileSerialNumber = "";  // 根据request.getPileCode获取桩的序列号
        respBody.writeBytes(BCD.strToBcd(pileSerialNumber));
        respBody.writeByte(1);
        respBody.writeByte(qrCodeRequest.getQrcode().length());
        respBody.writeBytes(AsciiUtil.asciiToBytes(qrCodeRequest.getQrcode(), qrCodeRequest.getQrcode().length()));
        send(build(respBody, SeqNoUtil.getSeqNo(), 0, QR_CODE_CMD), request.getPileCode(), channel);
    }
}
