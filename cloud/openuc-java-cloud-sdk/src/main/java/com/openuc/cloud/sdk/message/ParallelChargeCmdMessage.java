package com.openuc.cloud.sdk.message;

public class ParallelChargeCmdMessage extends CmdMessage {

    private int gunCode; //枪号

    private int startType; //启动方式

    private int needPwd; //是否需要密码

    private long chargeCardNo; //物理卡号

    private String password; //输入密码

    private String vinCode; //vin

    private int mainType; //主辅枪标记

    private String parallelNo; //并充序号

    public int getGunCode() {
        return gunCode;
    }

    public void setGunCode(int gunCode) {
        this.gunCode = gunCode;
    }

    public int getStartType() {
        return startType;
    }

    public void setStartType(int startType) {
        this.startType = startType;
    }

    public int getNeedPwd() {
        return needPwd;
    }

    public void setNeedPwd(int needPwd) {
        this.needPwd = needPwd;
    }

    public long getChargeCardNo() {
        return chargeCardNo;
    }

    public void setChargeCardNo(long chargeCardNo) {
        this.chargeCardNo = chargeCardNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVinCode() {
        return vinCode;
    }

    public void setVinCode(String vinCode) {
        this.vinCode = vinCode;
    }

    public int getMainType() {
        return mainType;
    }

    public void setMainType(int mainType) {
        this.mainType = mainType;
    }

    public String getParallelNo() {
        return parallelNo;
    }

    public void setParallelNo(String parallelNo) {
        this.parallelNo = parallelNo;
    }

    public ParallelChargeCmdMessage(int cmdCode, String pilecode) {
        super(cmdCode, pilecode);
    }
}
