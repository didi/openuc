package com.openuc.cloud.sdk.message;

import java.util.Map;

/**
 * @author fangshun
 */
public class ApplicationMessage {

    // 起始域
    private int startFlag;

    // 数据长度
    private int dataLength;

    // 序列号
    private int seqNo;

    // 加密标识
    private int encryptionFlag;

    // 指令
    private int cmdCode;

    // 消息体
    private byte[] msgBody;

    // 校验和
    private int checkSum;
  /**-------------
   * 充电桩发送指令到云平台特殊字段数据
   * */
    // 原始报文
    private byte[] protocolMsg;

    // 桩的ipPort
    private String clientIpPort;

    public int getStartFlag() {
        return startFlag;
    }

    public void setStartFlag(int startFlag) {
        this.startFlag = startFlag;
    }

    public int getDataLength() {
        return dataLength;
    }

    public void setDataLength(int dataLength) {
        this.dataLength = dataLength;
    }

    public int getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    public int getEncryptionFlag() {
        return encryptionFlag;
    }

    public void setEncryptionFlag(int encryptionFlag) {
        this.encryptionFlag = encryptionFlag;
    }

    public int getCmdCode() {
        return cmdCode;
    }

    public void setCmdCode(int cmdCode) {
        this.cmdCode = cmdCode;
    }

    public byte[] getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(byte[] msgBody) {
        this.msgBody = msgBody;
    }

    public int getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(int checkSum) {
        this.checkSum = checkSum;
    }

    public byte[] getProtocolMsg() {
        return protocolMsg;
    }

    public void setProtocolMsg(byte[] protocolMsg) {
        this.protocolMsg = protocolMsg;
    }

    public String getClientIpPort() {
        return clientIpPort;
    }

    public void setClientIpPort(String clientIpPort) {
        this.clientIpPort = clientIpPort;
    }
}
