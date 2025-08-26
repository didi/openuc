package com.openuc.cloud.sdk.decoder.cmd;

import com.openuc.cloud.sdk.message.ApplicationMessage;
import com.openuc.cloud.sdk.message.CmdMessage;
import com.openuc.cloud.sdk.message.LoginCmdMessage;
import com.openuc.cloud.sdk.util.AsciiUtil;
import com.openuc.cloud.sdk.util.BCD;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 登录指令处理器
 * @author fangshun
 */
public class LoginCmdDecoder implements CmdDecoder{
    @Override
    public int getCmdCode() {
        return 0x01;
    }

    @Override
    public CmdMessage process(ApplicationMessage message) {
        ByteBuf byteBuf = Unpooled.copiedBuffer(message.getMsgBody());

        //1.桩编码
        byte[] pileCodeB = new byte[7];
        byteBuf.readBytes(pileCodeB);
        String pileCode = BCD.bcdToStr(pileCodeB);

        LoginCmdMessage loginCmdMessage = new LoginCmdMessage(getCmdCode(),pileCode);

        //2.桩类型
        int pileType = byteBuf.readUnsignedByte();
        loginCmdMessage.setPileType(pileType);

        //3.充电枪数量
        int gunsNum = byteBuf.readUnsignedByte();
        loginCmdMessage.setGunsNum(gunsNum);

        //4.通信协议版本
        loginCmdMessage.setProtocolVersion(byteBuf.readUnsignedByte());

        //5.程序版本
        byte[] bytes = new byte[8];
        byteBuf.readBytes(bytes);
        loginCmdMessage.setProgramVersion(AsciiUtil.bytesToAscii(bytes));

        //6.⽹络链接类型
        loginCmdMessage.setNetworkLinkType(byteBuf.readUnsignedByte());

        //7.Sim 卡
        byte[] simB =  new byte[10];
        byteBuf.readBytes(simB);
        String sim = BCD.bcdToStr(simB);
        loginCmdMessage.setSimCard(sim);

        //8.运营商
        loginCmdMessage.setOperatorType(byteBuf.readUnsignedByte());

        return loginCmdMessage;
    }
}
