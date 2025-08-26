package com.openuc.cloud.sdk.decoder;

import com.openuc.cloud.sdk.message.ApplicationMessage;
import com.openuc.cloud.sdk.util.CheckSumUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

import static com.openuc.cloud.sdk.common.Constants.HEADER_CODE;

/**
 * 协议解码器
 * @author  liaofangshun
 */
public class ProtocolDecoder {
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        in.markReaderIndex();
        findStartFlag(in);
        byte[] checkArr = new byte[in.readableBytes()];
        in.duplicate().readBytes(checkArr);

        // 判断是否可以读取报头，8个字节
        if (in.readableBytes() < 6) {
            in.resetReaderIndex();
            return;
        }

        // 起始标识, 固定为0x68
        int startFlag = in.readUnsignedByte();
        if (startFlag != HEADER_CODE) {
            in.resetReaderIndex();
            return;
        }

        // 数据长度，序列号域+加密标志+帧类型标志+消息体 之和
        int dataLength = in.readUnsignedByte();

        // 报文的流水号
        int seqNo = in.readUnsignedShortLE();

        //加密标志，代表次帧是否加密
        int encrpyFlag = in.readUnsignedByte();

        // 帧类型标志
        int cmdCode = in.readUnsignedByte();

        // 判断是否可以读取消息体，N-4个字节
        int msgBodyLength = dataLength - 4;
        if (in.readableBytes() < msgBodyLength) {
            in.resetReaderIndex();
            return;
        }
        // 消息体
        byte[] msgBody = new byte[msgBodyLength];
        in.readBytes(msgBody);

        // 判断是否可以读取校验和， 2个字节
        if (in.readableBytes() < 2) {
            in.resetReaderIndex();
            return;
        }

        byte byCheckSum[] = new byte[2];
        in.readBytes(byCheckSum);
        ByteBuf temp = Unpooled.buffer();
        temp.writeBytes(byCheckSum);
        int checkSum = temp.readUnsignedShort();

        // 校验和校验
        byte[] check = new byte[dataLength];
        System.arraycopy(checkArr, 2, check, 0, dataLength);
        boolean checkResult = CheckSumUtil.checkCrcSum(check, checkSum);
        if (!checkResult) {
            //检验和二次检查
            temp.writeBytes(byCheckSum);
            checkSum = temp.readUnsignedShortLE();
            checkResult = CheckSumUtil.checkCrcSum(check, checkSum);
        }
        if(!checkResult){
            throw new IllegalArgumentException("数据异常，校验和失败!");
        }

        // 只有校验和通过，消息才有意义
        ApplicationMessage message = new ApplicationMessage();

        message.setStartFlag(startFlag);
        message.setDataLength(dataLength);
        message.setSeqNo(seqNo);
        message.setEncryptionFlag(encrpyFlag);
        message.setCmdCode(cmdCode);
        message.setMsgBody(msgBody);
        message.setCheckSum(checkSum);
        byte[] protocolMsgB = new byte[dataLength + 4];
        System.arraycopy(checkArr, 0, protocolMsgB, 0, protocolMsgB.length);
        message.setProtocolMsg(protocolMsgB);
        message.setClientIpPort(ctx.channel().remoteAddress().toString());

        out.add(message);
    }

    /**
     * 寻找起始标识,并且将数据移动到起始标识位置，起始标志之后的数据才是有效数据<br>
     * 如果没有找到起始标识，则将readerIndex重置到起始位置
     * @param buf
     */
    private static void findStartFlag(ByteBuf buf) {
        int count = buf.readableBytes();
        for (int index = buf.readerIndex(); index < count - 1; index++) {
            if (buf.getByte(index) == (byte) 0x68 ) {
                buf.readerIndex(index);
                return;
            }
        }
        buf.resetReaderIndex();
    }
}
