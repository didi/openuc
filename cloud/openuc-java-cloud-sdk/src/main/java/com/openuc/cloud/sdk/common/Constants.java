package com.openuc.cloud.sdk.common;

/**
 * @author fangshun
 */
public class Constants {
    // 起始字节标识
    public final static int HEADER_CODE = 0x68;

    // 启动充电命令指令值
    public final static int START_CHARGE_CMD = 0x34;

    // 确认启动充电命令指令值
    public final static int START_CHARGE_ACK_CMD = 0x32;

    // 交易记录确认指令值
    public final static int TRADE_RECORD_ACK_CMD = 0x40;

    // 时钟设置命令指令值
    public final static int CLOCK_SYNC_CMD = 0x56;

    // 充电工作参数设置命令指令值
    public final static int CHARGE_WORK_PARAM_SET_CMD = 0x52;

    // 计费模型设置命令指令值
    public final static int CHARGE_BILLING_MODEL_SET_CMD = 0x58;

    // 心跳命令指令值
    public final static int HEARTBEAT_CMD = 0x04;

    // 登录应答命令指令值
    public final static int LOGIN_ACK_CMD = 0x02;

    // 平台确认并启动充电命令指令值
    public final static int START_CHARGE_PLATFORM_ACK_CMD = 0xA2;

    // 远程下发二维码命令指令值
    public final static int QR_CODE_CMD = 0xF0;

    // 计费模型请求应答命令指令值
    public final static int CHARGE_BILLING_MODEL_ACK_CMD = 0x0A;

    // 远程重启
    public final static int REBOOT_CMD = 0x92;

    // 远程更新命令指令值
    public final static int UPDATE_CMD = 0x94;

    // 远程停机命令指令值
    public final static int STOP_CHARGE_CMD = 0x36;

    // 计费模型验证请求应答命令指令值
    public final static int CHARGE_BILLING_MODEL_VERIFY_ACK_CMD = 0x06;

}
