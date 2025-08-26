package com.openuc.cloud.sdk.sender;

import com.openuc.cloud.sdk.common.Constants;
import com.openuc.cloud.sdk.encoder.ProtocolEncoder;
import com.openuc.cloud.sdk.message.ApplicationMessage;
import com.openuc.cloud.sdk.request.IssueRequest;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

/**
 * 从云平台发送数据到充电桩
 * @author fangshun
 */
public abstract class ProtocolMessagerSender {


    public abstract void send(IssueRequest request, Channel channel);
    /**
     * 发送数据到充电桩
     * @param message 请求数据，由不同的子类自行实现
     * @param pileCode 桩编码
     * @param channel 发送通道（netty）
     */
    protected void send(ApplicationMessage message, String pileCode, Channel channel) {

        ChannelFuture channelFuture = channel.writeAndFlush(Unpooled.copiedBuffer(ProtocolEncoder.encode(message)));
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (!channelFuture.isSuccess() && channelFuture.isDone()) {
                    throw new RuntimeException(String.format("协议下发消息失败：管道未发送成功, pileCode:%s, CMD:%s ,channel:%s", pileCode, message.getCmdCode(),channel.toString()));
                }
            }
        }) ;
    }

    protected ApplicationMessage build(ByteBuf msgBody, final int seqNo, final int encryptFlag, final int cmd){
        int msgLength = msgBody.readableBytes();
        byte[] msgBodyBytes = new byte[msgLength];

        ApplicationMessage message = new ApplicationMessage();

        message.setStartFlag(Constants.HEADER_CODE);
        message.setDataLength(msgLength);
        message.setSeqNo(seqNo);
        message.setEncryptionFlag(encryptFlag);
        message.setCmdCode(cmd);

        msgBody.readBytes(msgBodyBytes);
        message.setMsgBody(msgBodyBytes);

        // 校验和在最后面统一计算
        return message;
    }
}
