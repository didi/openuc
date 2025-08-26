#include "Net_deal.h"
#include "DataDeal.h"


ParamSet PSet;//桩体参数设置结构体
stNetConFlag NetFlag;//网络连接相关结构体
stRateInfo	PileRateInfo;	//桩计费模型信息
stChargeInfo PileChargeInfo[CHARGEPILE_GUN_NUM];	//两个枪的充电信息
stBatteryPara BatteryInfo[CHARGEPILE_GUN_NUM];//枪对应车的电池信息
stPileQRCodeInfo QRCodeInfo;//二维码信息


ip_addr_t Link_Server_Ipaddr = {0};	//平台IP地址
struct netconn *tcp_clientconn = NULL;					//netconn for gun A
static int s_ReportingCycle = SYS_DELAY_10s;//状态上报周期 单位秒



#define TIMEOUT_RETRY_MAX  (5)    //最大重发次数
#define TIMEOUT_BUFFER     (500)
typedef struct
{
	u8  ptr[TIMEOUT_BUFFER]; // 指向发送的数据         自动清零
	u16 Len;                 // 指向发送的数据的长度    不用清零
	u32 TimeData;	         // 系统节拍(用于定时重    不用清零
	u8  Count;               // 重发次数
	u32 RepeatCycle;         // 重发周期
}Time_Out;
static Time_Out s_TimeOut[CHARGEPILE_GUN_NUM][NETTIMEOUTMAX] = {0};	//报文重发


enum
{
    CABLE_CHECK,       //检测网线
    IP_CHECK,          //检测IP地址
    SOCKET_CHECK,      //检测socket
};


//程序版本号
char VERSION[8]         = "V9.9.999";



err_t NetSendData(unsigned char *pucBuf,unsigned short usLen)
{
	err_t err = ERR_OK;
	if(PSet.Use4GModule == FALSE)
	{
	    if (tcp_clientconn != NULL)
		{
	        err = netconn_write(tcp_clientconn ,pucBuf,usLen,NETCONN_COPY);
	    }
	}
	NetFlag.NetFailTimes++;
	return err;
}

/*****************************************************************************
* Function: GetTemperaturebyGunNum
* Description: 获取枪温
* Input     : GunNum: 枪序号 GUN_A/GUN_B
* Output	:
* Note(s)	:
* Contributor	:
*****************************************************************************/
int16_t GetTemperaturebyGunNum(u8 GunNum)
{
    if(GunNum == GUN_A)
    {
        return MAX(sPT1000.EVPlugA_PosiT,sPT1000.EVPlugA_NegaT)/100;
    }
    else if (GunNum == GUN_B)
    {
        return MAX(sPT1000_B.EVPlugA_PosiT,sPT1000_B.EVPlugA_NegaT)/100;
    }
    else
    {
        return 0;
    }

}

/*****************************************************************************
* Function: NET_CmdDump
* Description: 打印网络数据
* Input:
* Output		:
* Note(s)		:
* Contributor	: 2024年02月15日
*****************************************************************************/
void NET_CmdDump(u8 *buf, int len)
{
    int i = 0;
    sy_adp_trace(SYADP_TRACE_INFO,"send ");
    for(i=0;i<len;i++)
   	{
    	printf("%02x",buf[i]);
	}
    printf("\r\n");
}

/*****************************************************************************
* Function: GetStateChange
* Description: 检测是否发生状态变化
* Input:
* Output		:
* Note(s)		:
* Contributor	: 2024年02月15日
*****************************************************************************/
static u8 GetStateChange(u8 GunNum)
{
	if(PileChargeInfo[GunNum].PileGunState != PileChargeInfo[GunNum].PileGunStateOld || 
		PileChargeInfo[GunNum].IfGunInsert != PileChargeInfo[GunNum].IfGunInsertOld)
	{
		PileChargeInfo[GunNum].PileGunStateOld = PileChargeInfo[GunNum].PileGunState;
		PileChargeInfo[GunNum].IfGunInsertOld = PileChargeInfo[GunNum].IfGunInsert;
		return TRUE;
	}
	return FALSE;
}



/*****************************************************************************
* Function: PileSendServeiceStart
* Description: 充电桩上传登录平台认证
* Input:
* Output		:
* Note(s)		: 
* Contributor	: 2024年12月29日
*****************************************************************************/
void PileSendServeiceStart(void)
{
	u8 buf[50]={0},len=0;
	u16 CRCdata=0;

	//包头
	len += AddNewCommandByte(HeadCmd, &buf[len]);
	// 包长
	len += 1;
	//序列号域
	len += AddNewCommandInt16(0x0000, &buf[len]);
	//加密标志
	len += AddNewCommandByte(CODETYPE_1, &buf[len]);
	//数据帧类型
	len += AddNewCommandByte(PILELEND_S, &buf[len]);	
	//数据
	len += AddNewCommandBuf(&buf[len],NetFlag.NewPileID,7);// ID:不足7字节 位补 0
	
	len += AddNewCommandByte(DCPILE, &buf[len]);	//桩类型

	len += AddNewCommandByte(CHARGEPILE_GUN_NUM, &buf[len]);//枪个数

	len += AddNewCommandByte(0x0A, &buf[len]);//协议版本  V1.0*10 = 10

	len += AddNewCommandBuf(&buf[len], (INT8U*)&VERSION[0],8);	//程序版本

	len += AddNewCommandByte(NET, &buf[len]);//网络类型

	len += 10;	//sim卡卡号

	len += AddNewCommandByte(0x04, &buf[len]);//运营商

// 包长
	AddNewCommandByte(len-2, &buf[1]);//-2是减去命令头+数据长度字节
// CRC校验
	CRCdata = ModbusCRC(&buf[2],len-2);
	len += AddNewCommandInt16(CRCdata, &buf[len]);

//命令打印
	NET_CmdDump(buf,len);

// 发送
	NetSendData(buf,len);
}
/*****************************************************************************
* Function: PileSendHeart
* Description: 充电桩上传心跳包
* Input:
* Output		:
* Note(s)		: 
* Contributor	: 2024年12月29日
*****************************************************************************/
void PileSendHeart(u8 GunNum)
{
	u8 buf[50]={0},len=0;
	u16 CRCdata=0;
	//包头
	len += AddNewCommandByte(HeadCmd, &buf[len]);
	// 包长
	len += 1;
	//序列号域
	len += AddNewCommandInt16_LOW(0x0000, &buf[len]);
	//加密标志
	len += AddNewCommandByte(CODETYPE_1, &buf[len]);
	//数据帧类型
	len += AddNewCommandByte(PILEHEART_S, &buf[len]);	
	//数据
	len += AddNewCommandBuf(&buf[len],NetFlag.NewPileID,7);// ID:不足7字节 位补 0

	len += AddNewCommandByte(GunNum+1, &buf[len]);

	len += AddNewCommandByte(PileChargeInfo[GunNum].PileGunStateBig, &buf[len]);	//充电枪编号

// 包长
	AddNewCommandByte(len-2, &buf[1]);//-2是减去命令头+数据长度字节
// CRC校验
	CRCdata = ModbusCRC(&buf[2],len-2);
	len += AddNewCommandInt16(CRCdata, &buf[len]);

//命令打印
	NET_CmdDump(buf,len);

// 发送
	NetSendData(buf,len);
}

/*****************************************************************************
* Function: PileSendCheckRate
* Description: 充电桩验证计费模型、如验证正确就不需要向平台索要计费模型、如不正确
			   就需要向平台重新请求计费模型
* Input:
* Output		:
* Note(s)		: 
* Contributor	: 2024年12月29日
*****************************************************************************/
void PileSendCheckRate(void)
{
	u8 buf[50]={0},len=0;
	u16 CRCdata=0;

	//包头
	len += AddNewCommandByte(HeadCmd, &buf[len]);
	// 包长
	len += 1;
	//序列号域
	len += AddNewCommandInt16(0x0000, &buf[len]);
	//加密标志
	len += AddNewCommandByte(CODETYPE_1, &buf[len]);
	//数据帧类型
	len += AddNewCommandByte(PILERATECHECK_S, &buf[len]);	
	//数据
	len += AddNewCommandBuf(&buf[len],NetFlag.NewPileID,7);// ID:不足7字节 位补 0
	
	len += AddNewCommandInt16(NetFlag.RateType, &buf[len]);	//计费模型编码

// 包长
	AddNewCommandByte(len-2, &buf[1]);//-2是减去命令头+数据长度字节
// CRC校验
	CRCdata = ModbusCRC(&buf[2],len-2);
	len += AddNewCommandInt16(CRCdata, &buf[len]);

//命令打印
	NET_CmdDump(buf,len);

// 发送
	NetSendData(buf,len);
}


/*****************************************************************************
* Function: PileSendRequestRate
* Description: 充电桩请求计费模型
* Input:
* Output		:
* Note(s)		: 
* Contributor	: 2024年12月29日
*****************************************************************************/
void PileSendRequestRate(void)
{
	u8 buf[50]={0},len=0;
	u16 CRCdata=0;

	//包头
	len += AddNewCommandByte(HeadCmd, &buf[len]);
	// 包长
	len += 1;
	//序列号域
	len += AddNewCommandInt16(0x0000, &buf[len]);
	//加密标志
	len += AddNewCommandByte(CODETYPE_1, &buf[len]);
	//数据帧类型
	len += AddNewCommandByte(PILERATEASK_S, &buf[len]);	
	//数据
	len += AddNewCommandBuf(&buf[len],NetFlag.NewPileID,7);// ID:不足7字节 位补 0
// 包长
	AddNewCommandByte(len-2, &buf[1]);//-2是减去命令头+数据长度字节
// CRC校验
	CRCdata = ModbusCRC(&buf[2],len-2);
	len += AddNewCommandInt16(CRCdata, &buf[len]);

//命令打印
	NET_CmdDump(buf,len);

// 发送
	NetSendData(buf,len);
}



/*****************************************************************************
* Function: PileSendRealTimeInfo
* Description: 充电桩发送实时信息
* Input:
* Output		:
* Note(s)		: 
* Contributor	: 2024年12月29日
*****************************************************************************/
void PileSendRealTimeInfo(INT8U GunNum)
{
	u8 buf[90]={0},len=0;
	u16 CRCdata=0;

	//包头
	len += AddNewCommandByte(HeadCmd, &buf[len]);
	// 包长
	len += 1;
	//序列号域
	len += AddNewCommandInt16_LOW(0x0000, &buf[len]);
	//加密标志
	len += AddNewCommandByte(CODETYPE_1, &buf[len]);
	//数据帧类型
	len += AddNewCommandByte(OFFLINEDATA, &buf[len]);	
	//数据
	len += AddNewCommandBuf(&buf[len],PileChargeInfo[GunNum].OrderNumber,16);//交易流水号

	len += AddNewCommandBuf(&buf[len],NetFlag.NewPileID,7);//ID:不足7字节 位补 0

	len += AddNewCommandByte((GunNum+1), &buf[len]);//枪编号:A枪为1、B枪为2

	len += AddNewCommandByte(PileChargeInfo[GunNum].PileGunState, &buf[len]);//枪状态

	len += AddNewCommandByte(PileChargeInfo[GunNum].GunReturnState, &buf[len]);//枪归位状态

	len += AddNewCommandByte(PileChargeInfo[GunNum].IfGunInsert, &buf[len]);

	len += AddNewCommandInt16_LOW(PileChargeInfo[GunNum].OutVoltage, &buf[len]);

	len += AddNewCommandInt16_LOW(PileChargeInfo[GunNum].OutCurrent, &buf[len]);

	if(ControlPara[GunNum].ChargedState == TRUE)
	{
		PileChargeInfo[GunNum].GunTemp = GetTemperaturebyGunNum(GunNum);
		PileChargeInfo[GunNum].GunTemp+=50;
	}
	else
	{
		PileChargeInfo[GunNum].GunTemp = 0;
	}
	len += AddNewCommandByte(PileChargeInfo[GunNum].GunTemp, &buf[len]);

	len += 8;
	
	len += AddNewCommandByte(PileChargeInfo[GunNum].GunSOC, &buf[len]);

	len += AddNewCommandByte(PileChargeInfo[GunNum].BatHighTemp, &buf[len]);

	len += AddNewCommandInt16_LOW(PileChargeInfo[GunNum].GunChargeTime, &buf[len]);

	len += AddNewCommandInt16_LOW(PileChargeInfo[GunNum].RemainTime, &buf[len]);

	len += AddNewCommandInt32_LOW(PileChargeInfo[GunNum].ChargeMeterData, &buf[len]);

	len += AddNewCommandInt32_LOW(PileChargeInfo[GunNum].LossMeterData, &buf[len]);

	len += AddNewCommandInt32_LOW(PileChargeInfo[GunNum].ChargeMoney, &buf[len]);

	len += AddNewCommandInt16_LOW(PileChargeInfo[GunNum].HardFaultData, &buf[len]);
	
// 包长
	AddNewCommandByte(len-2, &buf[1]);//-2是减去命令头+数据长度字节
// CRC校验
	CRCdata = ModbusCRC(&buf[2],len-2);
	len += AddNewCommandInt16(CRCdata, &buf[len]);

//命令打印
	NET_CmdDump(buf,len);

// 发送
	NetSendData(buf,len);
}


/**************************************************根据充电流程去调用的函数开始***************************************************************/

/*****************************************************************************
* Function: PileSendChargeStep1Info
* Description: 充电桩发送充电阶段1的信息BHM和BRM的信息,在充电流程中适时发送，直接调用该函数即可
* Input:
* Output		:
* Note(s)		: 
* Contributor	: 2024年12月29日
*****************************************************************************/
void PileSendChargeStep1Info(INT8U GunNum)
{
	u8 buf[90]={0},len=0;
	u16 CRCdata=0;

	//包头
	len += AddNewCommandByte(HeadCmd, &buf[len]);
	// 包长
	len += 1;
	//序列号域
	len += AddNewCommandInt16_LOW(0x0000, &buf[len]);
	//加密标志
	len += AddNewCommandByte(CODETYPE_1, &buf[len]);
	//数据帧类型
	len += AddNewCommandByte(CHARGESTART, &buf[len]);	
	//数据
	len += AddNewCommandBuf(&buf[len],PileChargeInfo[GunNum].OrderNumber,16);//交易流水号

	len += AddNewCommandBuf(&buf[len],NetFlag.NewPileID,7);//ID:不足7字节 位补 0

	len += AddNewCommandByte((GunNum+1), &buf[len]);//枪编号:A枪为1、B枪为2

	len += AddNewCommandBuf(&buf[len],&BatteryInfo[GunNum].ProtocolVer[0],3);

	len += AddNewCommandByte(BatteryInfo[GunNum].BatteryType, &buf[len]);

	len += AddNewCommandInt16_LOW(BatteryInfo[GunNum].CarPowerCapacity, &buf[len]);

	len += AddNewCommandInt16_LOW(BatteryInfo[GunNum].CarVoltageCapacity, &buf[len]);

	len += AddNewCommandInt32_LOW(BatteryInfo[GunNum].BatteryManufacturer, &buf[len]);

	len += AddNewCommandInt32_LOW(BatteryInfo[GunNum].BatteryNumber, &buf[len]);

	len += AddNewCommandByte(BatteryInfo[GunNum].BatteryCreateYear, &buf[len]);

	len += AddNewCommandByte(BatteryInfo[GunNum].BatteryCreateMouth, &buf[len]);

	len += AddNewCommandByte(BatteryInfo[GunNum].BatteryCreateDay, &buf[len]);

	len += AddNewCommandInt24_LOW(BatteryInfo[GunNum].BatteryChargeTimes, &buf[len]);

	len += AddNewCommandByte(BatteryInfo[GunNum].BatteryOwner, &buf[len]);

	len += AddNewCommandByte(0x00, &buf[len]);

	len += AddNewCommandBuf(&buf[len],&BatteryInfo[GunNum].CarVINCode[0],17);

//	memcpy(&buf[len],0x00,8);//BMS软件编译时间直接赋值0x00
	len += 8;	
	
// 包长
	AddNewCommandByte(len-2, &buf[1]);//-2是减去命令头+数据长度字节
// CRC校验
	CRCdata = ModbusCRC(&buf[2],len-2);
	len += AddNewCommandInt16(CRCdata, &buf[len]);

//命令打印
	NET_CmdDump(buf,len);

// 发送
	NetSendData(buf,len);
}



/*****************************************************************************
* Function: PileSendChargeStep2Info
* Description: 充电桩发送充电阶段2的信息、参数配置阶段
* Input:
* Output		:
* Note(s)		: 
* Contributor	: 2024年12月29日
*****************************************************************************/
void PileSendChargeStep2Info(INT8U GunNum)
{
	u8 buf[90]={0},len=0;
	u16 CRCdata=0;

	//包头
	len += AddNewCommandByte(HeadCmd, &buf[len]);
	// 包长
	len += 1;
	//序列号域
	len += AddNewCommandInt16_LOW(0x0000, &buf[len]);
	//加密标志
	len += AddNewCommandByte(CODETYPE_1, &buf[len]);
	//数据帧类型
	len += AddNewCommandByte(CHARGEPARASET, &buf[len]);	
	//数据
	len += AddNewCommandBuf(&buf[len],PileChargeInfo[GunNum].OrderNumber,16);//交易流水号

	len += AddNewCommandBuf(&buf[len],NetFlag.NewPileID,7);//ID:不足7字节 位补 0

	len += AddNewCommandByte((GunNum+1), &buf[len]);//枪编号:A枪为1、B枪为2
	
	len += AddNewCommandInt16_LOW(BatteryInfo[GunNum].SingleBatteryMaxVol, &buf[len]);

	len += AddNewCommandInt16_LOW(BatteryInfo[GunNum].MaxChargeCurrent, &buf[len]);

	len += AddNewCommandInt16_LOW(BatteryInfo[GunNum].BatteryAllPower, &buf[len]);
	
	len += AddNewCommandInt16_LOW(BatteryInfo[GunNum].MaxChargeVol, &buf[len]);

	len += AddNewCommandByte(BatteryInfo[GunNum].AllowMaxTemp, &buf[len]);

	len += AddNewCommandInt16_LOW(BatteryInfo[GunNum].BatterySOC, &buf[len]);

	len += AddNewCommandInt16_LOW(BatteryInfo[GunNum].BatteryTempVol, &buf[len]);

	len += AddNewCommandInt16_LOW((PILEMAXOUTVOL*10), &buf[len]);

	len += AddNewCommandInt16_LOW((PILEMINOUTVOL*10), &buf[len]);

	len += AddNewCommandInt16_LOW(((400-PILEMAXOUTCUR)*10), &buf[len]);

	len += AddNewCommandInt16_LOW(((400-PILEMINOUTCUR)*10), &buf[len]);
// 包长
	AddNewCommandByte(len-2, &buf[1]);//-2是减去命令头+数据长度字节
// CRC校验
	CRCdata = ModbusCRC(&buf[2],len-2);
	len += AddNewCommandInt16(CRCdata, &buf[len]);

//命令打印
	NET_CmdDump(buf,len);

// 发送
	NetSendData(buf,len);
}


/*****************************************************************************
* Function: PileSendChargeStep3Info
* Description: 充电桩发送充电阶段3的信息、结束阶段
* Input:
* Output		:
* Note(s)		: 
* Contributor	: 2024年12月29日
*****************************************************************************/
void PileSendChargeStep3Info(INT8U GunNum)
{
	u8 buf[60]={0},len=0;
	u16 CRCdata=0;

	//包头
	len += AddNewCommandByte(HeadCmd, &buf[len]);
	// 包长
	len += 1;
	//序列号域
	len += AddNewCommandInt16_LOW(0x0000, &buf[len]);
	//加密标志
	len += AddNewCommandByte(CODETYPE_1, &buf[len]);
	//数据帧类型
	len += AddNewCommandByte(CHARGESTOP, &buf[len]);	
	//数据
	len += AddNewCommandBuf(&buf[len],PileChargeInfo[GunNum].OrderNumber,16);//交易流水号

	len += AddNewCommandBuf(&buf[len],NetFlag.NewPileID,7);//ID:不足7字节 位补 0

	len += AddNewCommandByte((GunNum+1), &buf[len]);//枪编号:A枪为1、B枪为2

	len += AddNewCommandByte((BatteryInfo[GunNum].BatterySOC/10), &buf[len]);

	len += AddNewCommandInt16_LOW(BatteryInfo[GunNum].SingleBatteryMinVol, &buf[len]);
	
	len += AddNewCommandInt16_LOW(BatteryInfo[GunNum].SingleBatteryMaxVol, &buf[len]);

	len += AddNewCommandByte(BatteryInfo[GunNum].BatteryMinTemp, &buf[len]);

	len += AddNewCommandByte(BatteryInfo[GunNum].BatteryMaxTemp, &buf[len]);

	len += AddNewCommandInt16_LOW(PileChargeInfo[GunNum].GunChargeTime, &buf[len]);

	len += AddNewCommandInt16_LOW(PileChargeInfo[GunNum].ChargeMeterData/1000, &buf[len]);

	len += AddNewCommandInt32_LOW(0x00, &buf[len]);
	

// 包长
	AddNewCommandByte(len-2, &buf[1]);//-2是减去命令头+数据长度字节
// CRC校验
	CRCdata = ModbusCRC(&buf[2],len-2);
	len += AddNewCommandInt16(CRCdata, &buf[len]);

//命令打印
	NET_CmdDump(buf,len);

// 发送
	NetSendData(buf,len);
}



/*****************************************************************************
* Function: PileSendChargeErrorInfo
* Description: 充电桩发送充电错误信息
* Input:
* Output		:
* Note(s)		: 
* Contributor	: 2024年12月29日
*****************************************************************************/
void PileSendChargeErrorInfo(INT8U GunNum)
{
	u8 buf[50]={0},len=0;
	u16 CRCdata=0;
	u8 TempData = 0x00;

	//包头
	len += AddNewCommandByte(HeadCmd, &buf[len]);
	// 包长
	len += 1;
	//序列号域
	len += AddNewCommandInt16_LOW(0x0000, &buf[len]);
	//加密标志
	len += AddNewCommandByte(CODETYPE_1, &buf[len]);
	//数据帧类型
	len += AddNewCommandByte(ERRORDATA, &buf[len]);	
	//数据
	len += AddNewCommandBuf(&buf[len],PileChargeInfo[GunNum].OrderNumber,16);//交易流水号

	len += AddNewCommandBuf(&buf[len],NetFlag.NewPileID,7);//ID:不足7字节 位补 0

	len += AddNewCommandByte((GunNum+1), &buf[len]);//枪编号:A枪为1、B枪为2

	TempData = 0x00;
	TempData = ((BatteryInfo[GunNum].RecognizeOverTime_2<<2)|BatteryInfo[GunNum].RecognizeOverTime_1);
	len += AddNewCommandByte(TempData, &buf[len]);

	TempData = 0x00;
	TempData = ((BatteryInfo[GunNum].PileReadyOverTime<<2)|BatteryInfo[GunNum].MaxOutputOverTime);
	len += AddNewCommandByte(TempData, &buf[len]);

	TempData = 0x00;
	TempData = ((BatteryInfo[GunNum].PileStopOverTime<<2)|BatteryInfo[GunNum].PileStateOverTime);
	len += AddNewCommandByte(TempData, &buf[len]);

	TempData = 0x00;
	TempData = ((BatteryInfo[GunNum].OtherOverTime<<2)|BatteryInfo[GunNum].PileStatisOverTime);
	len += AddNewCommandByte(TempData, &buf[len]);

	TempData = 0x00;
	TempData = PileChargeInfo[GunNum].Rec_BRM_OverTime;
	len += AddNewCommandByte(TempData, &buf[len]);

	TempData = 0x00;
	TempData = ((PileChargeInfo[GunNum].Rec_BRO_OverTime<<2)|PileChargeInfo[GunNum].Rec_BCP_OverTime);
	len += AddNewCommandByte(TempData, &buf[len]);

	TempData = 0x00;
	TempData = ((PileChargeInfo[GunNum].Rec_BST_OverTime<<4)|(PileChargeInfo[GunNum].Rec_BCL_OverTime<<2)|PileChargeInfo[GunNum].Rec_BCS_OverTime);
	len += AddNewCommandByte(TempData, &buf[len]);	

	TempData = 0x00;
	TempData = ((PileChargeInfo[GunNum].Rec_Other_OverTime<<2)|PileChargeInfo[GunNum].Rec_BSD_OverTime);
	len += AddNewCommandByte(TempData, &buf[len]);	

// 包长
	AddNewCommandByte(len-2, &buf[1]);//-2是减去命令头+数据长度字节
// CRC校验
	CRCdata = ModbusCRC(&buf[2],len-2);
	len += AddNewCommandInt16(CRCdata, &buf[len]);

//命令打印
	NET_CmdDump(buf,len);

// 发送
	NetSendData(buf,len);
}


/*****************************************************************************
* Function: PileSendStop1Info
* Description: 充电桩发送充电阶段 BMS 中止
* Input:
* Output		:
* Note(s)		: 
* Contributor	: 2024年12月29日
*****************************************************************************/
void PileSendStop1Info(INT8U GunNum)
{
	u8 buf[50]={0},len=0;
	u16 CRCdata=0;

	//包头
	len += AddNewCommandByte(HeadCmd, &buf[len]);
	// 包长
	len += 1;
	//序列号域
	len += AddNewCommandInt16_LOW(0x0000, &buf[len]);
	//加密标志
	len += AddNewCommandByte(CODETYPE_1, &buf[len]);
	//数据帧类型
	len += AddNewCommandByte(CHARGESTOP_BMS, &buf[len]);	
	//数据
	len += AddNewCommandBuf(&buf[len],PileChargeInfo[GunNum].OrderNumber,16);//交易流水号

	len += AddNewCommandBuf(&buf[len],NetFlag.NewPileID,7);//ID:不足7字节 位补 0

	len += AddNewCommandByte((GunNum+1), &buf[len]);//枪编号:A枪为1、B枪为2

	len += AddNewCommandByte(BatteryInfo[GunNum].ChargeStopReason, &buf[len]);

	len += AddNewCommandInt16_LOW(BatteryInfo[GunNum].BatteryError1, &buf[len]);

	len += AddNewCommandByte(BatteryInfo[GunNum].BatteryError2, &buf[len]);
// 包长
	AddNewCommandByte(len-2, &buf[1]);//-2是减去命令头+数据长度字节
// CRC校验
	CRCdata = ModbusCRC(&buf[2],len-2);
	len += AddNewCommandInt16(CRCdata, &buf[len]);

//命令打印
	NET_CmdDump(buf,len);

// 发送
	NetSendData(buf,len);
}


/*****************************************************************************
* Function: PileSendStop2Info
* Description: 充电桩发送充电阶段充电机中止
* Input:
* Output		:
* Note(s)		: 
* Contributor	: 2024年12月29日
*****************************************************************************/
void PileSendStop2Info(INT8U GunNum)
{
	u8 buf[50]={0},len=0;
	u16 CRCdata=0;

	//包头
	len += AddNewCommandByte(HeadCmd, &buf[len]);
	// 包长
	len += 1;
	//序列号域
	len += AddNewCommandInt16_LOW(0x0000, &buf[len]);
	//加密标志
	len += AddNewCommandByte(CODETYPE_1, &buf[len]);
	//数据帧类型
	len += AddNewCommandByte(CHARGESTOP_PILE, &buf[len]);	
	//数据
	len += AddNewCommandBuf(&buf[len],PileChargeInfo[GunNum].OrderNumber,16);//交易流水号

	len += AddNewCommandBuf(&buf[len],NetFlag.NewPileID,7);//ID:不足7字节 位补 0

	len += AddNewCommandByte((GunNum+1), &buf[len]);//枪编号:A枪为1、B枪为2

	len += AddNewCommandByte(PileChargeInfo[GunNum].PileStopReason, &buf[len]);

	len += AddNewCommandInt16_LOW(PileChargeInfo[GunNum].PileStopRrror1, &buf[len]);

	len += AddNewCommandByte(PileChargeInfo[GunNum].PileStopRrror2, &buf[len]);
// 包长
	AddNewCommandByte(len-2, &buf[1]);//-2是减去命令头+数据长度字节
// CRC校验
	CRCdata = ModbusCRC(&buf[2],len-2);
	len += AddNewCommandInt16(CRCdata, &buf[len]);

//命令打印
	NET_CmdDump(buf,len);

// 发送
	NetSendData(buf,len);
}

/*****************************************************************************
* Function: PileSendStop2Info
* Description: 充电桩发送充电阶段充电机中止
* Input:
* Output		:
* Note(s)		: 
* Contributor	: 2024年12月29日
*****************************************************************************/
void PileSendCharge1Info(INT8U GunNum)
{
	u8 buf[90]={0},len=0;
	u16 CRCdata=0;

	//包头
	len += AddNewCommandByte(HeadCmd, &buf[len]);
	// 包长
	len += 1;
	//序列号域
	len += AddNewCommandInt16_LOW(0x0000, &buf[len]);
	//加密标志
	len += AddNewCommandByte(CODETYPE_1, &buf[len]);
	//数据帧类型
	len += AddNewCommandByte(CHARGE_BMSREQ, &buf[len]);	
	//数据
	len += AddNewCommandBuf(&buf[len],PileChargeInfo[GunNum].OrderNumber,16);//交易流水号

	len += AddNewCommandBuf(&buf[len],NetFlag.NewPileID,7);//ID:不足7字节 位补 0

	len += AddNewCommandByte((GunNum+1), &buf[len]);//枪编号:A枪为1、B枪为2

	len += AddNewCommandInt16_LOW(BatteryInfo[GunNum].BatteryNeedVol, &buf[len]);

	len += AddNewCommandInt16_LOW(BatteryInfo[GunNum].BatteryNeedCur, &buf[len]);

	len += AddNewCommandByte(BatteryInfo[GunNum].BatteryChargeMode, &buf[len]);

	len += AddNewCommandInt16_LOW(BatteryInfo[GunNum].BatteryMeasureVol, &buf[len]);

	len += AddNewCommandInt16_LOW(BatteryInfo[GunNum].BatteryMeasureCur, &buf[len]);

	len += AddNewCommandInt16_LOW(BatteryInfo[GunNum].Battery_Max_VolAndNum, &buf[len]);

	len += AddNewCommandByte((BatteryInfo[GunNum].BatterySOC/10), &buf[len]);

	len += AddNewCommandInt16_LOW(BatteryInfo[GunNum].RemainTime, &buf[len]);

	len += AddNewCommandInt16_LOW(PileChargeInfo[GunNum].PileOutputVol, &buf[len]);

	len += AddNewCommandInt16_LOW(PileChargeInfo[GunNum].PileOutputCur, &buf[len]);

	len += AddNewCommandInt16_LOW(PileChargeInfo[GunNum].GunChargeTime, &buf[len]);
// 包长
	AddNewCommandByte(len-2, &buf[1]);//-2是减去命令头+数据长度字节
// CRC校验
	CRCdata = ModbusCRC(&buf[2],len-2);
	len += AddNewCommandInt16(CRCdata, &buf[len]);

//命令打印
	NET_CmdDump(buf,len);

// 发送
	NetSendData(buf,len);
}


/*****************************************************************************
* Function: PileSendCharge2Info
* Description: 充电桩发送充电过程 BMS 信息
* Input:
* Output		:
* Note(s)		: 
* Contributor	: 2024年12月29日
*****************************************************************************/
void PileSendCharge2Info(INT8U GunNum)
{
	u8 buf[50]={0},len=0;
	u16 CRCdata=0;

	//包头
	len += AddNewCommandByte(HeadCmd, &buf[len]);
	// 包长
	len += 1;
	//序列号域
	len += AddNewCommandInt16_LOW(0x0000, &buf[len]);
	//加密标志
	len += AddNewCommandByte(CODETYPE_1, &buf[len]);
	//数据帧类型
	len += AddNewCommandByte(CHARGE_BMSINFO, &buf[len]);	
	//数据
	len += AddNewCommandBuf(&buf[len],PileChargeInfo[GunNum].OrderNumber,16);//交易流水号

	len += AddNewCommandBuf(&buf[len],NetFlag.NewPileID,7);//ID:不足7字节 位补 0

	len += AddNewCommandByte((GunNum+1), &buf[len]);//枪编号:A枪为1、B枪为2

	len += AddNewCommandByte((BatteryInfo[GunNum].MaxVol_Num-1), &buf[len]);

	len += AddNewCommandByte(BatteryInfo[GunNum].BatteryMaxTemp, &buf[len]);

	len += AddNewCommandByte((BatteryInfo[GunNum].MaxTempNum-1), &buf[len]);

	len += AddNewCommandByte(BatteryInfo[GunNum].BatteryMinTemp, &buf[len]);

	len += AddNewCommandByte((BatteryInfo[GunNum].MinTempNum-1), &buf[len]);

	len += AddNewCommandInt16_LOW(BatteryInfo[GunNum].BatteryBSM, &buf[len]);
// 包长
	AddNewCommandByte(len-2, &buf[1]);//-2是减去命令头+数据长度字节
// CRC校验
	CRCdata = ModbusCRC(&buf[2],len-2);
	len += AddNewCommandInt16(CRCdata, &buf[len]);

//命令打印
	NET_CmdDump(buf,len);

// 发送
	NetSendData(buf,len);
}
/**************************************************根据充电流程去调用的函数结束***************************************************************/


/*****************************************************************************
* Function: PileSendStartCharge
* Description: 充电桩发送开始刷卡充电
* Input:
* Output		:
* Note(s)		: 
* Contributor	: 2024年12月29日
*****************************************************************************/
void PileSendStartCharge(INT8U GunNum)
{
	u8 buf[90]={0},len=0;
	u16 CRCdata=0;

	//包头
	len += AddNewCommandByte(HeadCmd, &buf[len]);
	// 包长
	len += 1;
	//序列号域
	len += AddNewCommandInt16_LOW(0x0000, &buf[len]);
	//加密标志
	len += AddNewCommandByte(CODETYPE_1, &buf[len]);
	//数据帧类型
	len += AddNewCommandByte(PILENEEDSTARTCHARGE_S, &buf[len]);	
	//数据
	len += AddNewCommandBuf(&buf[len],NetFlag.NewPileID,7);//ID:不足7字节 位补 0

	len += AddNewCommandByte((GunNum+1), &buf[len]);//枪编号:A枪为1、B枪为2

	len += AddNewCommandByte(PileChargeInfo[GunNum].StartChargeMode, &buf[len]);

	len += AddNewCommandByte(0x00, &buf[len]);//是否需要密码			0x00:不需要 0x01:需要

	len += AddNewCommandBuf(&buf[len],PileChargeInfo[GunNum].CardID,8);

	len += AddNewCommandBuf(&buf[len],PileChargeInfo[GunNum].CardCode,16);

	if(PileChargeInfo[GunNum].StartChargeMode == 3)
		len += AddNewCommandBuf(&buf[len],&BatteryInfo[GunNum].CarVINCode[0],17);
	else
		len += 17;
// 包长
	AddNewCommandByte(len-2, &buf[1]);//-2是减去命令头+数据长度字节
// CRC校验
	CRCdata = ModbusCRC(&buf[2],len-2);
	len += AddNewCommandInt16(CRCdata, &buf[len]);

//命令打印
	NET_CmdDump(buf,len);

// 发送
	NetSendData(buf,len);
}



/*****************************************************************************
* Function: PileSendRemotoStartCharge
* Description: 充电桩发送远程启动充电命令回复
* Input:	INT8U Result启动结果：0x00失败 0x01成功
* Output		:
* Note(s)		: 
* Contributor	: 2024年12月29日
*****************************************************************************/
void PileSendRemotoStartCharge(INT8U GunNum,INT8U Result)
{
	u8 buf[50]={0},len=0;
	u16 CRCdata=0;

	//包头
	len += AddNewCommandByte(HeadCmd, &buf[len]);
	// 包长
	len += 1;
	//序列号域
	len += AddNewCommandInt16_LOW(0x0000, &buf[len]);
	//加密标志
	len += AddNewCommandByte(CODETYPE_1, &buf[len]);
	//数据帧类型
	len += AddNewCommandByte(REMOTECHARGE_S, &buf[len]);

	len += AddNewCommandBuf(&buf[len],PileChargeInfo[GunNum].OrderNumber,16);//交易流水号

	len += AddNewCommandBuf(&buf[len],NetFlag.NewPileID,7);//ID:不足7字节 位补 0

	len += AddNewCommandByte((GunNum+1), &buf[len]);//枪编号:A枪为1、B枪为2

	len += AddNewCommandByte(Result, &buf[len]);//启动结果

	len += AddNewCommandByte(PileChargeInfo[GunNum].RemotoStartFailReason, &buf[len]);//失败原因
// 包长
	AddNewCommandByte(len-2, &buf[1]);//-2是减去命令头+数据长度字节
// CRC校验
	CRCdata = ModbusCRC(&buf[2],len-2);
	len += AddNewCommandInt16(CRCdata, &buf[len]);

//命令打印
	NET_CmdDump(buf,len);

// 发送
	NetSendData(buf,len);
}



/*****************************************************************************
* Function: PileSendRemotoStopCharge
* Description: 充电桩发送远程停止充电命令回复
* Input:	INT8U Result启动结果：0x00失败 0x01成功
* Output		:
* Note(s)		: 
* Contributor	: 2024年12月29日
*****************************************************************************/
void PileSendRemotoStopCharge(INT8U GunNum,INT8U Result)
{
	u8 buf[50]={0},len=0;
	u16 CRCdata=0;

	//包头
	len += AddNewCommandByte(HeadCmd, &buf[len]);
	// 包长
	len += 1;
	//序列号域
	len += AddNewCommandInt16_LOW(0x0000, &buf[len]);
	//加密标志
	len += AddNewCommandByte(CODETYPE_1, &buf[len]);
	//数据帧类型
	len += AddNewCommandByte(REMOTESTOP_S, &buf[len]);

	len += AddNewCommandByte(Result, &buf[len]);//停止结果

	len += AddNewCommandByte(PileChargeInfo[GunNum].RemotoStopFailReason, &buf[len]);//失败原因
// 包长
	AddNewCommandByte(len-2, &buf[1]);//-2是减去命令头+数据长度字节
// CRC校验
	CRCdata = ModbusCRC(&buf[2],len-2);
	len += AddNewCommandInt16(CRCdata, &buf[len]);

//命令打印
	NET_CmdDump(buf,len);

// 发送
	NetSendData(buf,len);
}



/*****************************************************************************
* Function: PileSendRecordData
* Description: 充电桩发送订单数据
* Input:	
* Output		:
* Note(s)		: 
* Contributor	: 2024年12月29日
*****************************************************************************/
void PileSendRecordData(INT8U GunNum)
{
	u8 buf[170]={0},len=0;
	u16 CRCdata=0;
	u8 i = 0;

	//包头
	len += AddNewCommandByte(HeadCmd, &buf[len]);
	// 包长
	len += 1;
	//序列号域
	len += AddNewCommandInt16_LOW(0x0000, &buf[len]);
	//加密标志
	len += AddNewCommandByte(CODETYPE_1, &buf[len]);
	//数据帧类型
	len += AddNewCommandByte(CHARGEBILL_S, &buf[len]);

	len += AddNewCommandBuf(&buf[len],PileChargeInfo[GunNum].OrderNumber,16);//交易流水号

	len += AddNewCommandBuf(&buf[len],NetFlag.NewPileID,7);//ID:不足7字节 位补 0

	len += AddNewCommandByte((GunNum+1), &buf[len]);//枪编号:A枪为1、B枪为2	

	len += AddNewCommandBuf(&buf[len],PileChargeInfo[GunNum].StartTime,7);

	len += AddNewCommandBuf(&buf[len],PileChargeInfo[GunNum].StopTime,7);
	//充电金额信息
	for(i=0;i<4;i++)
	{
		len += AddNewCommandInt32_LOW(PileChargeInfo[GunNum].ChargeMoneyInfo.ChargeRateInfo.ElecRate[i], &buf[len]);
		len += AddNewCommandInt32_LOW(PileChargeInfo[GunNum].ChargeMoneyInfo.ChargeMeterData[i], &buf[len]);
		len += AddNewCommandInt32_LOW(PileChargeInfo[GunNum].ChargeMoneyInfo.ChargeLossData[i], &buf[len]);
		len += AddNewCommandInt32_LOW(PileChargeInfo[GunNum].ChargeMoneyInfo.ChargeMoney[i], &buf[len]);
	}
	len += AddNewCommandInt32_LOW(PileChargeInfo[GunNum].ChargeMoneyInfo.StartMeterLow, &buf[len]);
	len += AddNewCommandByte(PileChargeInfo[GunNum].ChargeMoneyInfo.StartMeterHigh, &buf[len]);

	len += AddNewCommandInt32_LOW(PileChargeInfo[GunNum].ChargeMoneyInfo.StopMeterLow, &buf[len]);
	len += AddNewCommandByte(PileChargeInfo[GunNum].ChargeMoneyInfo.StopMeterHigh, &buf[len]);

	len += AddNewCommandInt32_LOW(PileChargeInfo[GunNum].ChargeMeterData, &buf[len]);

	len += AddNewCommandInt32_LOW(PileChargeInfo[GunNum].LossMeterData, &buf[len]);

	len += AddNewCommandInt32_LOW(PileChargeInfo[GunNum].ChargeMoney, &buf[len]);

	len += AddNewCommandBuf(&buf[len],&BatteryInfo[GunNum].CarVINCode[0],17);

	len += AddNewCommandByte(PileChargeInfo[GunNum].RecordStartMode, &buf[len]);

	len += AddNewCommandBuf(&buf[len],PileChargeInfo[GunNum].StopTime,7);

	len += AddNewCommandByte(PileChargeInfo[GunNum].StopChargeReason,&buf[len]);

	len += AddNewCommandBuf(&buf[len],PileChargeInfo[GunNum].CardID,8);
// 包长
	AddNewCommandByte(len-2, &buf[1]);//-2是减去命令头+数据长度字节
// CRC校验
	CRCdata = ModbusCRC(&buf[2],len-2);
	len += AddNewCommandInt16(CRCdata, &buf[len]);
	//账单的字节长度总共是166个字节
//命令打印
	NET_CmdDump(buf,len);
//设置重发数据到EEPROM
	NetTimeOutSet(GunNum, REAL_RECORD_RESEND, &buf[0],len, SYS_DELAY_10s);
// 发送
	NetSendData(buf,len);
}



/*****************************************************************************
* Function: PileSendTimeCheck
* Description: 充电桩发送校时回复
* Input:	
* Output		:
* Note(s)		: 
* Contributor	: 2024年12月29日
*****************************************************************************/
void PileSendTimeCheck(void)
{
	u8 buf[50]={0},len=0;
	u16 CRCdata=0;

	//包头
	len += AddNewCommandByte(HeadCmd, &buf[len]);
	// 包长
	len += 1;
	//序列号域
	len += AddNewCommandInt16_LOW(0x0000, &buf[len]);
	//加密标志
	len += AddNewCommandByte(CODETYPE_1, &buf[len]);
	//数据帧类型
	len += AddNewCommandByte(TIMECHECK_S, &buf[len]);

	len += AddNewCommandBuf(&buf[len],NetFlag.NewPileID,7);//ID:不足7字节 位补 0
	//时间没有加
	len+=7;
// 包长
	AddNewCommandByte(len-2, &buf[1]);//-2是减去命令头+数据长度字节
// CRC校验
	CRCdata = ModbusCRC(&buf[2],len-2);
	len += AddNewCommandInt16(CRCdata, &buf[len]);

//命令打印
	NET_CmdDump(buf,len);

// 发送
	NetSendData(buf,len);
}



/*****************************************************************************
* Function: PileSendRateSet
* Description: 充电桩发送费率设置回复
* Input:	
* Output		:
* Note(s)		: 
* Contributor	: 2024年12月29日
*****************************************************************************/
void PileSendRateSet(void)
{
	u8 buf[50]={0},len=0;
	u16 CRCdata=0;

	//包头
	len += AddNewCommandByte(HeadCmd, &buf[len]);
	// 包长
	len += 1;
	//序列号域
	len += AddNewCommandInt16_LOW(0x0000, &buf[len]);
	//加密标志
	len += AddNewCommandByte(CODETYPE_1, &buf[len]);
	//数据帧类型
	len += AddNewCommandByte(RATESET_S, &buf[len]);

	len += AddNewCommandBuf(&buf[len],NetFlag.NewPileID,7);//ID:不足7字节 位补 0

	len += AddNewCommandByte(0x01, &buf[len]);//默认设置成功
// 包长
	AddNewCommandByte(len-2, &buf[1]);//-2是减去命令头+数据长度字节
// CRC校验
	CRCdata = ModbusCRC(&buf[2],len-2);
	len += AddNewCommandInt16(CRCdata, &buf[len]);

//命令打印
	NET_CmdDump(buf,len);

// 发送
	NetSendData(buf,len);
}



/*****************************************************************************
* Function: PileSendRateSet
* Description: 充电桩发送费率设置回复
* Input:	
* Output		:
* Note(s)		: 
* Contributor	: 2024年12月29日
*****************************************************************************/
void PileSendRenewBalance(u8 GunNum,u8 Result)
{
	u8 buf[50]={0},len=0;
	u16 CRCdata=0;

	//包头
	len += AddNewCommandByte(HeadCmd, &buf[len]);
	// 包长
	len += 1;
	//序列号域
	len += AddNewCommandInt16_LOW(0x0000, &buf[len]);
	//加密标志
	len += AddNewCommandByte(CODETYPE_1, &buf[len]);
	//数据帧类型
	len += AddNewCommandByte(USERBALANCE_S, &buf[len]);

	len += AddNewCommandBuf(&buf[len],NetFlag.NewPileID,7);//ID:不足7字节 位补 0

	len += AddNewCommandBuf(&buf[len],PileChargeInfo[GunNum].CardID,8);

	len += AddNewCommandByte(Result, &buf[len]);//默认设置成功
// 包长
	AddNewCommandByte(len-2, &buf[1]);//-2是减去命令头+数据长度字节
// CRC校验
	CRCdata = ModbusCRC(&buf[2],len-2);
	len += AddNewCommandInt16(CRCdata, &buf[len]);

//命令打印
	NET_CmdDump(buf,len);

// 发送
	NetSendData(buf,len);
}



/*****************************************************************************
* Function: PileSendQRCodeInfo
* Description: 充电桩回复二维码信息
* Input:	
* Output		:
* Note(s)		: 
* Contributor	: 2024年12月29日
*****************************************************************************/
void PileSendQRCodeInfo(u8 Result)
{
	u8 buf[50]={0},len=0;
	u16 CRCdata=0;

	//包头
	len += AddNewCommandByte(HeadCmd, &buf[len]);
	// 包长
	len += 1;
	//序列号域
	len += AddNewCommandInt16_LOW(0x0000, &buf[len]);
	//加密标志
	len += AddNewCommandByte(CODETYPE_1, &buf[len]);
	//数据帧类型
	len += AddNewCommandByte(PILEQRCODE_S, &buf[len]);

	len += AddNewCommandBuf(&buf[len],NetFlag.NewPileID,7);//ID:不足7字节 位补 0

	len += AddNewCommandByte(Result, &buf[len]);//默认设置成功
// 包长
	AddNewCommandByte(len-2, &buf[1]);//-2是减去命令头+数据长度字节
// CRC校验
	CRCdata = ModbusCRC(&buf[2],len-2);
	len += AddNewCommandInt16(CRCdata, &buf[len]);

//命令打印
	NET_CmdDump(buf,len);

// 发送
	NetSendData(buf,len);
}




/*****************************************************************************
* Function: PileSendLockPile
* Description: 充电桩回复锁定桩
* Input:	
* Output		:
* Note(s)		: 
* Contributor	: 2024年12月29日
*****************************************************************************/
void PileSendLockPile(u8 Result)
{
	u8 buf[50]={0},len=0;
	u16 CRCdata=0;

	//包头
	len += AddNewCommandByte(HeadCmd, &buf[len]);
	// 包长
	len += 1;
	//序列号域
	len += AddNewCommandInt16_LOW(0x0000, &buf[len]);
	//加密标志
	len += AddNewCommandByte(CODETYPE_1, &buf[len]);
	//数据帧类型
	len += AddNewCommandByte(PILEWORKSET_S, &buf[len]);

	len += AddNewCommandBuf(&buf[len],NetFlag.NewPileID,7);//ID:不足7字节 位补 0

	len += AddNewCommandByte(Result, &buf[len]);//默认设置成功
// 包长
	AddNewCommandByte(len-2, &buf[1]);//-2是减去命令头+数据长度字节
// CRC校验
	CRCdata = ModbusCRC(&buf[2],len-2);
	len += AddNewCommandInt16(CRCdata, &buf[len]);

//命令打印
	NET_CmdDump(buf,len);

// 发送
	NetSendData(buf,len);
}



/*****************************************************************************
* Function: PileSendRestartPile
* Description: 充电桩回复重启桩
* Input:	
* Output		:
* Note(s)		: 
* Contributor	: 2024年12月29日
*****************************************************************************/
void PileSendRestartPile(u8 Result)
{
	u8 buf[50]={0},len=0;
	u16 CRCdata=0;

	//包头
	len += AddNewCommandByte(HeadCmd, &buf[len]);
	// 包长
	len += 1;
	//序列号域
	len += AddNewCommandInt16_LOW(0x0000, &buf[len]);
	//加密标志
	len += AddNewCommandByte(CODETYPE_1, &buf[len]);
	//数据帧类型
	len += AddNewCommandByte(REMOTORESTART_S, &buf[len]);

	len += AddNewCommandBuf(&buf[len],NetFlag.NewPileID,7);//ID:不足7字节 位补 0

	len += AddNewCommandByte(Result, &buf[len]);//默认设置成功
// 包长
	AddNewCommandByte(len-2, &buf[1]);//-2是减去命令头+数据长度字节
// CRC校验
	CRCdata = ModbusCRC(&buf[2],len-2);
	len += AddNewCommandInt16(CRCdata, &buf[len]);

//命令打印
	NET_CmdDump(buf,len);

// 发送
	NetSendData(buf,len);
}





/*****************************************************************************
* Function: Receive_RataInfo
* Description: 计费模式接收函数
* Input:
* Output		:
* Note(s)		:
* Contributor	: 2024年02月21日
*****************************************************************************/
static u8 Receive_RataInfo(u8 *rbuf)
{
    u8 i=0,index=0;
    INT8U temp[256]={0};


	index = 13;//从桩编号开始获取数据
	PileRateInfo.RateType = ReadReceiveCommandInt16(rbuf+index);//计费模型编号
	index+=2;
	//尖峰平谷费率获取
	for(i=0;i<4;i++)
	{
		PileRateInfo.ElecRate[i] = ReadReceiveCommandInt32(rbuf+index);
		index+=4;
		PileRateInfo.ServRate[i] = ReadReceiveCommandInt32(rbuf+index);
		index+=4;
	}
	//计损比例获取
	PileRateInfo.LossPercent = ReadReceiveCommandChar8(rbuf+index);
	index+=1;
	//24小时具体执行费率
	for(i=0;i<48;i++)
	{
		PileRateInfo.TimeSet[i] = ReadReceiveCommandChar8(rbuf+index);
		index+=1;
	}
	//将接收到的费率保存到EEP
	memset(temp,0x00,sizeof(temp));
	memcpy(&temp[0],(INT8U*)&PileRateInfo.RateType,index);
	Write_EEP_data(RATAPAGE,0,temp,index);	 // 数据写入存储器

    sy_adp_trace(SYADP_TRACE_INFO,"Price Receive Info Ok\r\n");
	NetFlag.RateType = PileRateInfo.RateType;
    return TRUE;
}


/*****************************************************************************
* Function: Receive_PileStartChargeInfo
* Description: 运营平台确认启动充电
* Input:
* Output		:
* Note(s)		:
* Contributor	: 2024年02月21日
*****************************************************************************/
static u8 Receive_PileStartChargeInfo(u8 *rbuf)
{
    u8 index=0;
	u8 GunNum = 0;

	index = 6;//从桩编号开始获取数据
	//桩编号错误
	if(memcmp((const char *)NetFlag.NewPileID,(const char *)&rbuf[22],7)!=0)
	{
		sy_adp_trace(SYADP_TRACE_ERROR,"Rcv_DataAnaly PileID err\r\n");
		return FALSE;
	}

	GunNum = ReadReceiveCommandChar8(rbuf+29);//先获取枪编号
	GunNum = GunNum - 1;
	memcpy(PileChargeInfo[GunNum].OrderNumber,rbuf+index,16);//交易流水号
	
	index=30;
	memcpy(PileChargeInfo[GunNum].LogicCardNum,rbuf+index,8);//逻辑卡号
	
	index+=8;
	PileChargeInfo[GunNum].UserBalance = ReadReceiveCommandInt32(rbuf+index);

	index+=4;
	PileChargeInfo[GunNum].CardCheckOK = ReadReceiveCommandChar8(rbuf+index);
	//开启刷卡启动
	if(PileChargeInfo[GunNum].CardCheckOK == TRUE)
	{
		if(ControlPara[GunNum].UserInfo.ChargeStartMode == CHARGE_FAST)
		{
			ControlPara[GunNum].UserInfo.VIN_CODE_START = TRUE;
			RecordPack(5,GunNum);	//先保存一次充电记录
		}
		else
			ControlPara[GunNum].UserInfo.ChargeStartMode = CHARGE_CARD;
	}
	else//鉴权失败
	{
		if(ControlPara[GunNum].DisplayState != Warning)
		{
			ControlPara[GunNum].DisplayState = Ending;	//切换到空闲界面
		}
		PreDealForDisplay();	
		if(ControlPara[GunNum].UserInfo.ChargeStartMode == CHARGE_FAST)
		{
			ControlPara[GunNum].UserInfo.VIN_CODE_START = FALSE;
			ControlPara[GunNum].UserInfo.SendVinCodeStart = FALSE;
		}	
	}
	index+=1;
	PileChargeInfo[GunNum].CardCheckFailReason = ReadReceiveCommandChar8(rbuf+index);
    return TRUE;
}



/*****************************************************************************
* Function: Receive_RemotoStartChargeInfo
* Description: 运营平台远程启动启动充电,一般就是扫码
* Input:
* Output		:
* Note(s)		:
* Contributor	: 2024年02月21日
*****************************************************************************/
static u8 Receive_RemotoStartChargeInfo(u8 *rbuf)
{
    u8 index=0;
	u8 GunNum = 0;

	index = 6;//从桩编号开始获取数据
	//桩编号错误
	if(memcmp((const char *)NetFlag.NewPileID,(const char *)&rbuf[22],7)!=0)
	{
		sy_adp_trace(SYADP_TRACE_ERROR,"Rcv_DataAnaly PileID err\r\n");
		PileSendRemotoStartCharge(GunNum,0x01);
		return FALSE;
	}

	GunNum = ReadReceiveCommandChar8(rbuf+29);//先获取枪编号
	GunNum = GunNum -1;
	memcpy(PileChargeInfo[GunNum].OrderNumber,rbuf+index,16);//交易流水号
	
	index=30;
	memcpy(PileChargeInfo[GunNum].LogicCardNum,rbuf+index,8);//逻辑卡号
	
	index+=8;
	memcpy(PileChargeInfo[GunNum].CardID,rbuf+index,8);//物理卡号

	index+=8;
	PileChargeInfo[GunNum].UserBalance = ReadReceiveCommandInt32(rbuf+index);

	if(PileChargeInfo[GunNum].PileGunState == fault)
	{
		PileSendRemotoStartCharge(GunNum,0x03);
	}
	else if(PileChargeInfo[GunNum].PileGunState == chargeing)
	{
		PileSendRemotoStartCharge(GunNum,0x02);
	}
	else
	{
		if(PileChargeInfo[GunNum].IfGunInsert == 0x01)
			ControlPara[GunNum].UserInfo.ChargeStartMode = CHARGE_QRCODE;
		if(PileChargeInfo[GunNum].IfGunInsert == 0x00)
			PileSendRemotoStartCharge(GunNum,0x05);
	}
    return TRUE;
}



/*****************************************************************************
* Function: Receive_RemotoStopChargeInfo
* Description: 运营平台远程停止充电
* Input:
* Output		:
* Note(s)		:
* Contributor	: 2024年02月21日
*****************************************************************************/
static u8 Receive_RemotoStopChargeInfo(u8 *rbuf)
{
    u8 index=0;
	u8 GunNum = 0;

	index = 13;//从桩编号开始获取数据
	GunNum = ReadReceiveCommandChar8(rbuf+index);//先获取枪编号
	GunNum = GunNum - 1;
	//桩编号错误
	if(memcmp((const char *)NetFlag.NewPileID,(const char *)&rbuf[6],7)!=0)
	{
		sy_adp_trace(SYADP_TRACE_ERROR,"Rcv_DataAnaly PileID err\r\n");
		PileChargeInfo[GunNum].RemotoStopFailReason = 0x01;
		PileSendRemotoStopCharge(GunNum,0x00);
		return FALSE;
	}

	if(PileChargeInfo[GunNum].PileGunState != chargeing)
	{
		PileChargeInfo[GunNum].RemotoStopFailReason = 0x02;
		PileSendRemotoStopCharge(GunNum,0x00);
	}
	else
	{
		ControlPara[GunNum].UserInfo.StopByNet = TRUE;
		PileChargeInfo[GunNum].RemotoStopFailReason = 0x00;
	}
    return TRUE;
}



/*****************************************************************************
* Function: Receive_RecordInfo
* Description: 运营平台确认充电订单回复
* Input:
* Output		:
* Note(s)		:
* Contributor	: 2022年02月21日
*****************************************************************************/
static u8 Receive_RecordInfo(u8 *rbuf)
{
    u8 index=0;
	u8 Result = 0;
	u8 OrderNumber[16];
	INT8U temp[173]={0};
	int i=0;
	int GunNum = 0;

	index = 6;//从桩编号开始获取数据

	memcpy(OrderNumber,rbuf+index,16);//获取订单流水号
	index+=16;
	Result = ReadReceiveCommandChar8(rbuf+index);//先获取枪编号
	if(Result == 0x00)//确认成功、更新Flash的订单信息
	{
		memset(temp,0x00,sizeof(temp));
		if(RReSend.CheckFlag == TRUE)//正在进行记录检查
		{		
			GunNum = RReSend.ChargeFind.RecodeBuf[29];
			memcpy(&temp[1],RReSend.ChargeFind.RecodeBuf,RReSend.ChargeFind.RecodeLen);
			WriteRecoderData(RReSend.ChargeFind.RecordSection,0,temp,(RReSend.ChargeFind.RecodeLen+1));
			RReSend.IsStartFlag = 1;//继续检查
			NetTimeOutClear(GunNum, PAST_RECORD_RESEND);	//清楚重发通道
		}
		else
		{
			for(i=0;i<CHARGEPILE_GUN_NUM;i++)
			{
				Read_EEP_data((REALDATASAVE+2*i),0,temp,173);
				memcpy(&ChargeR[i],&temp[1],172);//除去第一个字节
				if(memcmp(&ChargeR[i].RecodeBuf[6],OrderNumber,16) == 0)
				{
					GunNum = (ChargeR[i].RecodeBuf[29]-1);
					memset(temp,0x00,173);
					memcpy(&temp[1],ChargeR[i].RecodeBuf,ChargeR[i].RecodeLen);
					WriteRecoderData(ChargeR[i].RecordSection,0,temp,(ChargeR[i].RecodeLen+1));
					ChargeDataClear(ChargeR[i].ReocrdGunNum);
					NetTimeOutClear(GunNum, REAL_RECORD_RESEND);//清楚重发通道	
				}
			}
		}
	}
	else//非法订单
	{

	}
    return TRUE;
}



/*****************************************************************************
* Function: Receive_RecordInfo
* Description: 运营平台确认充电订单回复
* Input:
* Output		:
* Note(s)		:
* Contributor	: 2022年02月21日
*****************************************************************************/
static u8 Receive_RenewMoneyInfo(u8 *rbuf)
{
    u8 index=0;
	u8 PileID[7];
	u8 GunNum = 0;
	u8 CardId[8];

	index = 6;//从桩编号开始获取数据
	memcpy(PileID,rbuf+index,7);//获取桩编号
	index+=7;
	GunNum = ReadReceiveCommandChar8(rbuf+index);
	GunNum = GunNum - 1;
	index+=1;	
	if(memcmp(&PileID[0],NetFlag.NewPileID,7) == 0)
	{
		memcpy(CardId,rbuf+index,8);//获取卡号
		if(memcmp(&CardId[0],PileChargeInfo[GunNum].CardID,8) == 0)
		{
			//更新余额
			index+=8;
			PileChargeInfo[GunNum].UserBalance = ReadReceiveCommandInt32(rbuf+index);
			//发送回复
			PileSendRenewBalance(GunNum,0x00);
		}
		else
		{
			PileSendRenewBalance(GunNum,0x02);
			sy_adp_trace(SYADP_TRACE_ERROR,"Receive_RenewMoneyInfo CardID err\r\n");
			return FALSE;
		}
	}
	else
	{
		PileSendRenewBalance(GunNum,0x01);
		sy_adp_trace(SYADP_TRACE_ERROR,"Receive_RenewMoneyInfo PileID err\r\n");
		return FALSE;
	}
    return TRUE;
}



/*****************************************************************************
* Function: Receive_TimeCheckInfo
* Description: 运营平台校时
* Input:
* Output		:
* Note(s)		:
* Contributor	: 2022年02月21日
*****************************************************************************/
u8 Receive_TimeCheckInfo(u8 *rbuf)
{
	STDATETIME Rtime;
	u8 DiwSetTime[6] = {0};//向显示屏设置的时间

	Rtime.ucYear = (rbuf[19] & 0x7F);
	Rtime.ucMon = (rbuf[18] & 0x0F);
	Rtime.ucDay = (rbuf[17] & 0x1F);
	Rtime.ucWeek = (rbuf[17] & 0xE0);
	Rtime.ucHour = (rbuf[16] & 0x1F);
	Rtime.ucMin = (rbuf[15] & 0x3F);
	Rtime.ucSec = (((rbuf[14]<<8)|rbuf[13])/1000);

/*
68
12
0100
00
56				5位
99031210001030	12位
69de	13-14位
29		15位
14		16位
76		17位
01		18位
19		19位
5a5e


98B7
0E
11
10
03
14（当前时间：2020-03-16 17:14:47）
*/
	//设置显示屏时间
	DiwSetTime[0] = Rtime.ucYear;
	DiwSetTime[1] = Rtime.ucMon;
	DiwSetTime[2] = Rtime.ucDay;
	DiwSetTime[3] = Rtime.ucHour;
	DiwSetTime[4] = Rtime.ucMin;
	DiwSetTime[5] = Rtime.ucSec;
	DwinSendTime(&DiwSetTime[0]);
	//设置系统时间	
	Rtime.ucWeek = 1;
	if(BSP_Set_RTC_DateTime(Rtime)==TRUE)	//设置单片机时间
	{
		sy_adp_trace(SYADP_TRACE_INFO,"Time Set Ok\r\n");
		return TRUE;
	}
	else
	{
		sy_adp_trace(SYADP_TRACE_ERROR,"Time Set Fail\r\n");
		return FALSE;
	}
    return TRUE;
}



/*****************************************************************************
* Function: Receive_TimeCheckInfo
* Description: 运营平台校时
* Input:
* Output		:
* Note(s)		:
* Contributor	: 2024年02月21日
*****************************************************************************/
static u8 Receive_RateSetInfo(u8 *rbuf)
{
    u8 i=0,index=0;
    INT8U temp[256]={0};


	index = 13;//从桩编号开始获取数据
	PileRateInfo.RateType = ReadReceiveCommandInt16(rbuf+index);//计费模型编号
	index+=2;
	//尖峰平谷费率获取
	for(i=0;i<4;i++)
	{
		PileRateInfo.ElecRate[i] = ReadReceiveCommandInt32(rbuf+index);
		index+=4;
		PileRateInfo.ServRate[i] = ReadReceiveCommandInt32(rbuf+index);
		index+=4;
	}
	//计损比例获取
	PileRateInfo.LossPercent = ReadReceiveCommandChar8(rbuf+index);
	index+=1;
	//24小时具体执行费率
	for(i=0;i<48;i++)
	{
		PileRateInfo.TimeSet[i] = ReadReceiveCommandChar8(rbuf+index);
		index+=1;
	}
	//将接收到的费率保存到EEP
	memset(temp,0x00,sizeof(temp));
	memcpy(&temp[0],(INT8U*)&PileRateInfo.RateType,index);
	Write_EEP_data(RATAPAGE,0,temp,index);	 // 数据写入存储器

    sy_adp_trace(SYADP_TRACE_INFO,"Price Set Info Ok\r\n");
	NetFlag.RateType = PileRateInfo.RateType;
    return TRUE;
}


/*****************************************************************************
* Function: Receive_QRCodeInfo
* Description: 运营平台下发二维码数据
* Input:
* Output		:
* Note(s)		:
* Contributor	: 2024年02月21日
*****************************************************************************/
static void Receive_QRCodeInfo(u8 *rbuf)
{
    u8 index=0;
    
	index = 6;//从桩编号开始获取数据
	//桩编号错误
	if(memcmp((const char *)NetFlag.NewPileID,(const char *)&rbuf[index],7)!=0)
	{
		sy_adp_trace(SYADP_TRACE_ERROR,"QrCode Rcv_DataAnaly PileID err\r\n");
		PileSendQRCodeInfo(0x01);
		return;
	}	
	index += 7;
	QRCodeInfo.QRCodeType = ReadReceiveCommandChar8(rbuf+index);

	index += 1;
	QRCodeInfo.PreFixLen = ReadReceiveCommandChar8(rbuf+index);
	if(QRCodeInfo.PreFixLen > 200)
	{
		sy_adp_trace(SYADP_TRACE_ERROR,"QrCode Rcv_DataAnaly PreFix Len[%d] err\r\n",QRCodeInfo.PreFixLen);
		QRCodeInfo.QRCodeType = 0;
		QRCodeInfo.PreFixLen = 0;
		PileSendQRCodeInfo(0x01);
		return;
	}
	index += 1;
	memcpy(&QRCodeInfo.PreFixData[0],&rbuf[index],QRCodeInfo.PreFixLen);
	PileSendQRCodeInfo(0x00);//返回成功
	QRCodeInfo.QRCodeFlag = TRUE;
}


/*****************************************************************************
* Function: Receive_LockPile
* Description: 运营平台下发锁定充电桩不允许充电
* Input:
* Output		:
* Note(s)		:
* Contributor	: 2024年02月21日
*****************************************************************************/
static u8 Receive_LockPile(u8 *rbuf)
{
    u8 index=0;
    
	index = 6;//从桩编号开始获取数据
	//桩编号错误
	if(memcmp((const char *)NetFlag.NewPileID,(const char *)&rbuf[index],7)!=0)
	{
		sy_adp_trace(SYADP_TRACE_ERROR,"QrCode Rcv_DataAnaly PileID err\r\n");
		PileSendQRCodeInfo(0x01);
		return 0;
	}	
	index += 7;
	NetFlag.LockPileState = ReadReceiveCommandChar8(rbuf+index);
	return 1;
}


/*****************************************************************************
* Function: Receive_RestartPile
* Description: 运营平台下发重启充电桩
* Input:
* Output		:
* Note(s)		:
* Contributor	: 2024年02月21日
*****************************************************************************/
static u8 Receive_RestartPile(u8 *rbuf)
{
    u8 index=0;
	
    
	index = 6;//从桩编号开始获取数据
	//桩编号错误
	if(memcmp((const char *)NetFlag.NewPileID,(const char *)&rbuf[index],7)!=0)
	{
		sy_adp_trace(SYADP_TRACE_ERROR,"QrCode Rcv_DataAnaly PileID err\r\n");
		PileSendQRCodeInfo(0x01);
		return 0;
	}	
	index += 7;
	NetFlag.RestartFlag = ReadReceiveCommandChar8(rbuf+index);
	if(NetFlag.RestartFlag == 0x01)//立即执行
	{
		__set_FAULTMASK(1); //关闭所有中断
		NVIC_SystemReset(); //复位
	}
	else if(NetFlag.RestartFlag == 0x02)//空闲执行
	{
		if(ControlPara[GUN_A].ChargedState != TRUE && ControlPara[GUN_B].ChargedState != TRUE)
		{
			__set_FAULTMASK(1); //关闭所有中断
			NVIC_SystemReset(); //复位
		}
	}
	return 1;
}



/***************************************************************************************************************

											云快充协议结束

***************************************************************************************************************/





/*****************************************************************************
* Function: Net_init_send_data
* Description: 网络初始化处理函数
* Input:
* Output		:
* Note(s)		: 请求顺序: 校时->二维码->费率->状态
                  除"状态"外，其余请求10秒超时重发
* Contributor	: 2024年02月15日
*****************************************************************************/
static void Net_init_send_data(u8 GunNum)
{
	u32 tmr=0;
	int i = 0;
	long nowtime = 0;

    WORKCACL(tmr, NetFlag.RequestedTime, nowtime) ;

	switch(NetFlag.Init_step)
	{
		case NET_LOGIN_STEP://请求校时
            if (NetFlag.RequestedFlag == FALSE //未请求
                || tmr > SYS_DELAY_10s) //超时后再次发送请求
            {
                PileSendServeiceStart();
                NetFlag.RequestedFlag = TRUE;//设置为:已请求
                NetFlag.RequestedTime = xTaskGetTickCount();//更新时间
                sy_adp_trace(SYADP_TRACE_DEBUG,"Pile Send Load Req\r\n");
            }
            //清除网络重发---add by zlh 20240624
            for(i=0;i<CHARGEPILE_GUN_NUM;i++)
            {
            	NetTimeOutClear(i, PAST_RECORD_RESEND);
            	NetTimeOutClear(i, AUTHORITY_RESEND);
            	NetTimeOutClear(i, REAL_RECORD_RESEND);
            }
			break;
		case NET_SEND_HEART://发送心跳包
            if (NetFlag.RequestedFlag == FALSE //未请求
                || tmr > SYS_DELAY_10s) //超时后再次发送请求
            {
                PileSendHeart(GUN_A);
                NetFlag.RequestedFlag = TRUE;//设置为:已请求
                NetFlag.RequestedTime = xTaskGetTickCount();//更新时间
                sy_adp_trace(SYADP_TRACE_DEBUG,"Pile Send Heart\r\n");
            }			
			break;
		case NET_INIT_FNISH:
			NetFlag.NetSuccessFlag = TRUE;     //初始化完成
			NetFlag.GunSendHeartTime = xTaskGetTickCount();//更新时间
			sy_adp_trace(SYADP_TRACE_INFO,"Pile NET Init Success!\r\n");

			//A枪判断
			if(ControlPara[GUN_A].ChargedState == TRUE)
			{
				PileChargeInfo[GUN_A].PileGunState = chargeing;			
			}
			else
			{
				//需要判断桩是否有故障
				if(ControlPara[GUN_A].DisplayState == Warning)
					PileChargeInfo[GUN_A].PileGunState = fault;
				else
					PileChargeInfo[GUN_A].PileGunState = idle;
			}
			PileChargeInfo[GUN_A].PileGunStateOld = PileChargeInfo[GUN_A].PileGunState;	
			PileChargeInfo[GUN_A].GunReturnState = 0x02;//枪是否归位设置为未知
			//B枪判断
			if(ControlPara[GUN_B].ChargedState == TRUE)
			{
				PileChargeInfo[GUN_B].PileGunState = chargeing;			
			}
			else
			{
				//需要判断桩是否有故障
				if(ControlPara[GUN_B].DisplayState == Warning)
					PileChargeInfo[GUN_B].PileGunState = fault;
				else
					PileChargeInfo[GUN_B].PileGunState = idle;
			}
			PileChargeInfo[GUN_B].PileGunStateOld = PileChargeInfo[GUN_B].PileGunState;		
			PileChargeInfo[GUN_B].GunReturnState = 0x02;//枪是否归位设置为未知
		    break;
        default:
            sy_adp_trace(SYADP_TRACE_ERROR,"Net_init_send_data, err Init_step(%d)\r\n", NetFlag.Init_step);
            break;
	}
}

/*****************************************************************************
* Function: JDataSend
* Description: 平台数据处理
* Input:
* Output		:
* Note(s)		:
* Contributor	: 2024年02月15日
*****************************************************************************/
void Net_BusinessData_Send(u8 GunNum)
{
	if(NetFlag.RecoderUpFlag == TRUE)    // 发送充电记录
	{
		//NET_SendRecordeUp(TRUE, GunNum) ;		//需要重发
		NetFlag.RecoderUpFlag = FALSE ;
	}
}

/*****************************************************************************
* Function: Net_StateHardData_Send
* Description: 发送充电桩心跳到平台
* Input:
* Output		:
* Note(s)		:
* Contributor	: 2024年02月15日
*****************************************************************************/
void Net_StateHardData_Send(void)
{
	u32 nowtime=0,tmr=0;
	u8 i = 0;
	static u8 j = 0;
	static u8 k = 0;
	u8 Result = 0;
	u8 GunNum = 0;

	//状态发生改变立即上传
	for(GunNum=0;GunNum<CHARGEPILE_GUN_NUM;GunNum++)
	{
		Result = GetStateChange(GunNum);	
		if(Result == TRUE)
		{
			PileSendRealTimeInfo(GunNum);
			NetFlag.GunSendHeartTime = xTaskGetTickCount();
		}
	}

	//周期发送心跳包1
	WORKCACL(tmr,NetFlag.GunSendHeartTime,nowtime) ;
	if(tmr > (SYS_DELAY_15s)) // 定时上报
	{
		PileSendRealTimeInfo(k);
		NetFlag.GunSendHeartTime = xTaskGetTickCount();
		sy_adp_trace(SYADP_TRACE_DEBUG,"Gun[%d] Send Heartbeat\r\n",k);
		k++;
		if(k>=CHARGEPILE_GUN_NUM)
			k=0;
	}


	//周期发送心跳包2	
	WORKCACL(tmr,NetFlag.RequestedTime,nowtime) ;
	if(tmr > (s_ReportingCycle)) // 定时上报
	{
		PileSendHeart(j);
		NetFlag.RequestedTime = xTaskGetTickCount();
		sy_adp_trace(SYADP_TRACE_DEBUG,"Gun[%d] Pile Send Heartbeat\r\n",j);
		j++;
		if(j>=CHARGEPILE_GUN_NUM)
			j=0;
	}

	//充电过程中发送BMS需求和BMS状态到平台	
	for(i=0;i<CHARGEPILE_GUN_NUM;i++)
	{
		if(PileChargeInfo[i].PileGunState == chargeing)
		{
			WORKCACL(tmr,ControlPara[i].BMSDataSendTick,nowtime);
			if(tmr > SYS_DELAY_15s)
			{
				PileSendCharge1Info(i);
				PileSendCharge2Info(i);
				ControlPara[i].BMSDataSendTick = xTaskGetTickCount();
			}
		}
	}

	
 }

static void NetTimeOutInit(void)
{
    int  i = 0, j = 0;
    for (i = 0; i < CHARGEPILE_GUN_NUM; i++)
    {
        for (j = 0; j < NETTIMEOUTMAX; j++)
        {
            memset(&(s_TimeOut[i][j]), 0, sizeof(Time_Out));
        }
    }
}

void NetTimeOutSet(u8 GunNum, u8 item, u8* buf, int len, int repeat)
{
    if (item < 0 || item >= NETTIMEOUTMAX)
        return;
    if (buf == NULL || len == 0 || len >= TIMEOUT_BUFFER)
        return;

    memcpy(s_TimeOut[GunNum][item].ptr, buf, len);
    s_TimeOut[GunNum][item].Len = len;
    s_TimeOut[GunNum][item].TimeData = xTaskGetTickCount();
    s_TimeOut[GunNum][item].RepeatCycle = repeat;
}

void NetTimeOutClear(u8 GunNum, int item)
{
    if (item < 0 || item >= NETTIMEOUTMAX)
        return;

    memset(&(s_TimeOut[GunNum][item]), 0, sizeof(Time_Out));
}
/*****************************************************************************
* Function: DeleteConnectForGun
* Description: 删除连接
* Input:
* Output		:
* Note(s)		:
* Contributor	:
*****************************************************************************/
static err_t DeleteConnectForGun(void)
{
    err_t err = ERR_OK;

    if  (tcp_clientconn == NULL)
    {
        sy_adp_trace(SYADP_TRACE_INFO,"NetDeal_DeleteConnect already deleted\r\n");
        return ERR_OK;
    }

	netconn_close(tcp_clientconn);
    err = netconn_delete(tcp_clientconn);
    if (err != ERR_OK)
    {
	    sy_adp_trace(SYADP_TRACE_INFO,"netconn_delete err(%d)...\r\n",err);
    }
    tcp_clientconn = NULL;

	return err;
}
/**********
函数名:void CheckTimeOut(void)
参数:
返回值:
函数说明:报文超时重发函数
*********/
static void CheckTimeOut(u8 GunNum)
{
	u8 i=0;
	u32 tmr=0,nowtime=0;
    Time_Out *temp = &(s_TimeOut[GunNum][0]);
    if(NetFlag.NetSuccessFlag != TRUE)
    {//网络初始化未完成
        return;
    }
	//轮询检查有没有超时
	for(i=0; i<NETTIMEOUTMAX; i++)
	{
		if(temp[i].Len != 0)
		{
			WORKCACL(tmr,temp[i].TimeData,nowtime) ;
			if(tmr > temp[i].RepeatCycle)                         // 超时时间到
			{
			    //连续五次没有成功退出
				if(temp[i].Count > TIMEOUT_RETRY_MAX)   // 发送五次 就认为超时
				{
					NetTimeOutClear(GunNum, i);
				}
				else //发送记录
				{
					NetSendData(temp[i].ptr, temp[i].Len);
					sy_adp_trace(SYADP_TRACE_INFO,"Gun[%d] Channel[%d] Resend Data to Serverce...%d--%d\r\n",GunNum,i,temp[i].TimeData,nowtime);
					temp[i].TimeData = xTaskGetTickCount();
					temp[i].Count++;
				}
			}
		}
	}
	if(NetFlag.NetFailTimes > 15)
	{
		NetFlag.NetSuccessFlag = FALSE;
		NetFlag.SocketCreateOk = FALSE;
		NetFlag.Init_step = NET_LOGIN_STEP;
		NetFlag.RequestedFlag = FALSE;
		DeleteConnectForGun();
		NetFlag.NetFailTimes = 0;
		sy_adp_trace(SYADP_TRACE_INFO,"Net Time Out...\r\n");
	}
}

/*****************************************************************************
* Function: JDataSend
* Description: 发送循环函数
* Input:
* Output		:
* Note(s)		:
* Contributor	: 2024年02月15日
*****************************************************************************/
static void JDataSend(u8 GunNum)
{
	if(NetFlag.NetSuccessFlag == FALSE)//未联网   开始网络初始化  进行对时等工作
	{
		Net_init_send_data(GunNum);
	}
	else	//网络连接成功了    开始发送业务数据
	{
//		Net_BusinessData_Send(GunNum);
		Net_StateHardData_Send();
	}
	//数据重发
	CheckTimeOut(GunNum);
}

/*****************************************************************************
* Function: Syn_Paramater_EveryDay
* Description: 每天早上8点开始网络校时
* Input:
* Output		:
* Note(s)		:
* Contributor	: 2024年02月15日
*****************************************************************************/
#define CCT (+8)    //中国时区
#define TIME_FOR_UPDATE  8 //8点校时
static void Syn_Paramater_EveryDay (u8 GunNum)
{
	time_t timep;
	struct tm *p;
    int hour = 0;
	static u8 UpdataTimeFlag[CHARGEPILE_GUN_NUM] = {0};

	time(&timep);
	p=gmtime(&timep);
    hour = (p->tm_hour+CCT)%24;

	if(hour == TIME_FOR_UPDATE && UpdataTimeFlag[GunNum] == 0)
	{
		UpdataTimeFlag[GunNum] = 1;
		NET_SendCheckTime(GunNum);	//开始网络对时
	}
	if(hour == ((TIME_FOR_UPDATE-1)%24) && UpdataTimeFlag[GunNum] == 1)//初始化  准备在8点的时候更新时间
	{
		UpdataTimeFlag[GunNum] = 0;
	}
}

/*****************************************************************************
* Function: Rcv_DataAnaly
* Description: 处理从平台接收的数据
* Input:
* Output		:
* Note(s)		:
* Contributor	: 2024年02月15日
*****************************************************************************/
static void Rcv_DataAnaly(u8* rbuf)
{
    u16 CMDData=0;
	u16 LenData = 0;
	u32 PileID = 0;

	LenData = rbuf[1];    //长度
	CMDData = rbuf[5];    //命令

	if(CMDData == PILELEND_R || CMDData == PILEHEART_R || CMDData == PILERATECHECK_R || CMDData == RADEDATAREALTIME
		|| CMDData == REMOTESTOP_R)
	{
		//桩编号错误
		if(memcmp((const char *)NetFlag.NewPileID,(const char *)&rbuf[6],7)!=0)
		{
			sy_adp_trace(SYADP_TRACE_ERROR,"Rcv_DataAnaly PileID(%ld) err\r\n", PileID);
			return;
		}
	}


	NetFlag.NetFailTimes = 0;
	sy_adp_trace(SYADP_TRACE_DEBUG,"RecCmd = 0x%02x,LenData = 0x%02x\r\n",CMDData,LenData);
   	switch(CMDData)
    {
		case PILELEND_R:     //登录应答
			NetFlag.RequestedFlag = FALSE;//清除请求标志
			if(rbuf[13] == 0x01)//注册失败、重新注册
			{
				NetFlag.Init_step = NET_LOGIN_STEP;
			}
			break;
		case PILEHEART_R:	//心跳包应答
			if(NetFlag.Init_step != NET_INIT_FNISH)
			{
				NetFlag.RequestedFlag = FALSE;//清除请求标志
				NetFlag.Init_step = NET_INIT_FNISH;
			}
			break;
		case PILERATECHECK_R://校对计费模型
			if(rbuf[15] != 0x00)
			{
				//校对失败、需要向平台请求新的计费模型
				NetFlag.NeedReqNewRateType = TRUE;
			}
			break;
		case PILERATEASK_R://平台返回计费模型
			Receive_RataInfo(&rbuf[0]);
			break;
		case RADEDATAREALTIME://平台读取实时数据
			if(rbuf[13] > 0)
				PileSendRealTimeInfo((rbuf[13]-1));//获取枪实时数据
			break;
		case PILENEEDSTARTCHARGE_R:
			Receive_PileStartChargeInfo(&rbuf[0]);
			break;
		case REMOTECHARGE_R:
			Receive_RemotoStartChargeInfo(&rbuf[0]);
			break;
		case REMOTESTOP_R:
			Receive_RemotoStopChargeInfo(&rbuf[0]);
			break;
		case CHARGEBILL_R:
			Receive_RecordInfo(&rbuf[0]);
			break;
		case USERBALANCE_R:
			Receive_RenewMoneyInfo(&rbuf[0]);
			break;
		case TIMECHECK_R:
			Receive_TimeCheckInfo(&rbuf[0]);
			PileSendTimeCheck();//回复应答
			break;
		case RATESET_R:
			Receive_RateSetInfo(&rbuf[0]);
			PileSendRateSet();
			//桩发送登录时、平台会发送：登录应答+校时+设置费率
			NetFlag.Init_step = NET_SEND_HEART;
			break;
		case PILEQRCODE_R:
			Receive_QRCodeInfo(&rbuf[0]);
			break;
		case PILEWORKSET_R:
			if(Receive_LockPile(&rbuf[0]) == 1)
			{
				PileSendLockPile(0x01);
			}
			else
			{
				PileSendLockPile(0x00);
			}
			break;
		case REMOTORESTART_R:
			if(Receive_RestartPile(&rbuf[0]) == 1)
			{
				PileSendRestartPile(0x01);
			}
			else
			{
				PileSendRestartPile(0x00);
			}
			break;
		#if 0
	    case 0x0004:           // 配置计费模型
			if(NET_RataUpdata(&rbuf[0],1) == TRUE)
			{
				NET_SendRateBack(1,GunNum);  // 成功
			}
			else
			{
				NET_SendRateBack(2,GunNum);   // 失败
			}
			break;
		case 0x0005:    // 请求IC卡状态
			NET_ReceiveCardState(&rbuf[0]);
			break;
		case 0x0001:          // 充电桩远程充电
			NET_ReceiveRemoteCharge(&rbuf[0]);    // 解析
			break;
		case 0x0006:          // 充电账单下行
			JNET_ReceiveRecordDown(&rbuf[0]);
			break;
        case 0x2012:    //配置状态上报周期
            NET_ReceiveReportingCycle(rbuf);
            break;
        case 0x0007:
        	NET_ReceiveCardStartOk(rbuf);
        	break;
		#endif
        default:
        	sy_adp_trace(SYADP_TRACE_ERROR,"RecCmd(0x%04x) unknow!\r\n", CMDData);
            break;
    }
}

/*****************************************************************************
* Function: Rcv_DataAnaly
* Description: 处理从平台接收的数据
* Input:
* Output		:
* Note(s)		:
* Contributor	: 2024年02月15日
*****************************************************************************/
static void Rcvbuf_Deal(u8 *rcvbuf,u16 bufsize)
{
	u8 *pstr;
	u8 *pReceive;
	int i;

	u16 CMD_head=0,buflen=0,tlen=0,CRCdata=0;
	buflen=bufsize;
	pstr=rcvbuf;

	sy_adp_trace(SYADP_TRACE_INFO,"recv ");
	for(i=0;i<buflen;i++)
   	{
		printf("%02x",pstr[i]);
   	}
	printf("\n");

	CMD_head=pstr[0];
	if(CMD_head==HeadCmd)
	{
		tlen=pstr[1]+4;
		sy_adp_trace(SYADP_TRACE_DEBUG,"buflen=%d, tlen=%d\r\n", buflen, tlen);
		if(buflen==tlen)
		{
			CRCdata=ModbusCRC(&pstr[2],buflen-4);
			if(CRCdata==(pstr[tlen-1] + pstr[tlen - 2]*256))//校验正确
			{
				sy_adp_trace(SYADP_TRACE_DEBUG,"data right:\r\n");
				 Rcv_DataAnaly(pstr);
			}
			else
			{
				sy_adp_trace(SYADP_TRACE_ERROR,"data CRC error\r\n");
			}
		}
		else
		{
			//处理粘包问题
			if(buflen > tlen)//多个包
			{
				//处理第一包
				CRCdata=ModbusCRC(&pstr[2],tlen-4);
				if(CRCdata==(pstr[tlen-1] + pstr[tlen - 2]*256))//校验正确
				{
					sy_adp_trace(SYADP_TRACE_DEBUG,"data right\r\n");
					Rcv_DataAnaly(pstr);
				}
				else
				{
					sy_adp_trace(SYADP_TRACE_ERROR,"data CRC error\r\n");
				}
				//下一包数据开始
				pReceive=&pstr[tlen];
				Rcvbuf_Deal(pReceive,bufsize-tlen);
			}
            else
            {
            	sy_adp_trace(SYADP_TRACE_ERROR,"data length error\r\n");
            }
		}
	}
	else
	{
		sy_adp_trace(SYADP_TRACE_ERROR,"data head error\r\n");
	}

}
/*****************************************************************************
* Function: CreateConnectForGun
* Description: 为充电枪创建网络连接
* Input:
* Output		:
* Note(s)		:
* Contributor	: 2024年04月03日
*****************************************************************************/
err_t  DNS_Deal(void)
{
	err_t err = ERR_OK;

	err  = dns_gethostbyname((char*)(PSet.DNSBuf), &(Link_Server_Ipaddr),NULL,NULL);
	if( err == ERR_OK)
	{
		sy_adp_trace(SYADP_TRACE_INFO,"netconn_gethostbyname ERR_OK.\r\n");
	}
	else
	{
		sy_adp_trace(SYADP_TRACE_ERROR,"netconn_gethostbyname err:%d.\r\n",err);
	}
	return err;
}


/*****************************************************************************
* Function: CreateConnectForGun
* Description: 为充电枪创建网络连接
* Input:
* Output		:
* Note(s)		:
* Contributor	: 2024年04月03日
*****************************************************************************/
static err_t CreateConnectForGun(int GunNum)
{
	err_t err = ERR_OK;


    if  (tcp_clientconn != NULL)
    {
        netconn_close(tcp_clientconn);
        netconn_delete(tcp_clientconn);
        tcp_clientconn = NULL;
    }

    tcp_clientconn = netconn_new(NETCONN_TCP);  //Create a Tcp connect
	if (tcp_clientconn == NULL)
	{
      	sy_adp_trace(SYADP_TRACE_ERROR,"netconn_new gun(%d) err!\r\n", GunNum);
        return ERR_MEM;
	}

    if (ipaddr_aton((char*)PSet.IPAddress, &Link_Server_Ipaddr) == 0)
    {
        sy_adp_trace(SYADP_TRACE_ERROR,"ipaddr_aton err!\r\n");
        return ERR_VAL;
    }
	err = netconn_connect(tcp_clientconn,&Link_Server_Ipaddr,PSet.IPPort_IP);//连接服务器
	if(err != ERR_OK)
	{//conncet失败
	    sy_adp_trace(SYADP_TRACE_INFO,"netconn_connect gun(%d) err(%d)...\r\n", GunNum,err);
	    netconn_close(tcp_clientconn);	//add by zhulh 20240530
        netconn_delete(tcp_clientconn);
        tcp_clientconn = NULL;
        return err;
	}
    else
    {//connect成功
        tcp_clientconn->recv_timeout = 100;
        sy_adp_trace(SYADP_TRACE_INFO,"netconn_connect gun(%d) OK...\r\n", GunNum);
    }
	return err;
}

/*****************************************************************************
* Function: NetDeal_Recv
* Description: 接收并分析处理网络数据
* Input:
* Output		:
* Note(s)		:
* Contributor	:
*****************************************************************************/
#define NET_RECV_BUF_LEN (1024)
static u8 s_recvbuf[NET_RECV_BUF_LEN] = {0};
static void NetDeal_Recv(u8 GunNum)
{
	err_t recv_err = ERR_OK;
	struct netbuf *recvbuf = NULL;
    int pos = 0;

	
    while((recv_err = netconn_recv(tcp_clientconn,&recvbuf)) == ERR_OK)
    {//从底层循环接收，直至没有新数据(前提是netconn_recv()必须立即返回)
        netbuf_copy(recvbuf, &(s_recvbuf[pos]), NET_RECV_BUF_LEN - pos);
        pos += netbuf_len(recvbuf);
        netbuf_delete(recvbuf);
//        sy_adp_trace(SYADP_TRACE_INFO,"Lwip Recv ok!\r\n");
        if (pos >= NET_RECV_BUF_LEN)
        {//buf已满
            sy_adp_trace(SYADP_TRACE_ERROR,"NetDeal_Recv Buffer is full !\r\n");
            break;
        }
    }

    //分析数据
    if (pos > 0)
    {
        Rcvbuf_Deal(s_recvbuf, pos);
    }

    //除ERR_TIMEOUT外，其他错误需要重新连接
    if (recv_err != ERR_TIMEOUT)
    {
        NetFlag.SocketCreateOk = FALSE;
	    sy_adp_trace(SYADP_TRACE_INFO,"Gun[%d] Err=%d...\r\n",GunNum,recv_err);
    }
}


/*****************************************************************************
* Function: NetDeal_SendRecv
* Description: 收发数据
* Input:
* Output		:
* Note(s)		:
* Contributor	:
*****************************************************************************/
static void NetDeal_SendRecv(u8 GunNum)
{
    err_t err = ERR_OK;

    if (NetFlag.SocketCreateOk == FALSE)//socket未连接
    {
    	SoftFeedWdg_Unactive(SOFTWDG_TASKID_NetDeal);//关闭看门狗监测
        err = CreateConnectForGun(GunNum);
        if (err == ERR_OK)
        {
        	NetFlag.NetFailTimes = 0;
        	SoftFeedWdg_Active(SOFTWDG_TASKID_NetDeal);//打开看门狗监测
            NetFlag.SocketCreateOk = TRUE;//socket连接成功
            sy_adp_trace(SYADP_TRACE_INFO,"Gun[%d] Create Connect OK...\r\n", GunNum);
        }
    }
    else
    {
        JDataSend(GunNum);
        //同步时间(每天一次)
        //Syn_Paramater_EveryDay(GunNum);
        //接收数据
        NetDeal_Recv(GunNum);
        //如接受数据时发现连接已断开，则删除连接，清除状态
        if (NetFlag.SocketCreateOk == FALSE)
        {
            DeleteConnectForGun();
        }
    }
}
/*****************************************************************************
* Function: Deal_WhenCableDown
* Description: 联网后发现网线断开后的处理
* Input:
* Output		:
* Note(s)		:
* Contributor	:
*****************************************************************************/
static void Deal_WhenCableDown(void)
{

	//如接受数据时发现连接已断开 则删除连接 清除状态
	NetFlag.NetSuccessFlag = FALSE;
	NetFlag.SocketCreateOk = FALSE;
	NetFlag.Init_step = NET_LOGIN_STEP;
	NetFlag.RequestedFlag = FALSE;
	DeleteConnectForGun();
	sy_adp_trace(SYADP_TRACE_INFO,"Cable Down...\r\n");
}
void NetDealTask(void *pvParameters)
{
	const portTickType xDelay10ms = 10 / portTICK_RATE_MS;
//    u8 link_status = 0;//0-网线未连接；1-网线已连接
    NET_CON_STATUS net_status = NET_STATUS_IDLE;
    SoftFeedWdg_Active(SOFTWDG_TASKID_NetDeal);
    while(1)
    {
    	net_status = get_net_status();
        switch(net_status)
        {
			case NET_STATUS_GOT_IP:
                //双枪
                NetDeal_SendRecv(GUN_A);
				break;
			case  NET_STATUS_LINK_DOWN:
				if(NetFlag.NetSuccessFlag != FALSE || NetFlag.SocketCreateOk != FALSE)
					Deal_WhenCableDown();
				break;
			default:break;
        }
        SoftFeedWdg(SOFTWDG_TASKID_NetDeal);//喂狗
        vTaskDelay( xDelay10ms );
    }
}

/*****************************************************************************
* Function: NET_DataInit
* Description: 网络数据结构体初始化
* Input:
* Output		:
* Note(s)		:
* Contributor	: 2024年02月15日
*****************************************************************************/
void NET_DataInit(void)
{
    int i = 0;

	NetTimeOutInit();//超时检测数据初始化

	for (i = 0; i < CHARGEPILE_GUN_NUM; i++)
	{
        //初始化网络状态标志
        memset(&NetFlag, 0, sizeof(stNetConFlag));
	}
	JianWeiConFlag.ConFlag = NO_CON;
	#if 0
	//for Test
	NetFlag.NewPileID[0] = 0x99;
	NetFlag.NewPileID[1] = 0x03;
	NetFlag.NewPileID[2] = 0x12;
	NetFlag.NewPileID[3] = 0x10;
	NetFlag.NewPileID[4] = 0x00;
	NetFlag.NewPileID[5] = 0x10;
	NetFlag.NewPileID[6] = 0x30;
	#endif
	//初始化枪的状态为未插枪
	PileChargeInfo[GUN_A].IfGunInsert = 0x00;
	PileChargeInfo[GUN_B].IfGunInsert = 0x00;
}
/*****************************************************************************
* Function: SendBy4GModule
* Description: 4G模块发送和接收函数
* Input:
* Output		:
* Note(s)		: 任何一个枪通道的网络初始化完成认为网络连接成功
                (包括完成校时、二维码和费率)
* Contributor	: 2024年02月15日
*****************************************************************************/
void Send_Rec_By4GModule(INT8U GunNum)
{
	INT16U uclen;
	unsigned char ucbuf[512];
    //发送数据
    JDataSend(GunNum);

    //同步时间(每天一次)
    Syn_Paramater_EveryDay(GunNum);
    //接收处理
	uclen = Get_4GModule_Rec_Num(GunNum);
	if( uclen>= 10)                //一帧数据的最小长度是10
	{
		BSP_NET_Read_RecBuf(ucbuf,uclen,GunNum);
		Rcvbuf_Deal(ucbuf, uclen);
	}
}
/*****************************************************************************
* Function: Net_ConnectedFlag
* Description: 网络连接标志
* Input:
* Output		:
* Note(s)		: 任何一个枪通道的网络初始化完成认为网络连接成功
                (包括完成校时、二维码和费率)
* Contributor	: 2024年02月15日
*****************************************************************************/
u8 Net_ConnectedFlag(void)
{
	if (NetFlag.NetSuccessFlag == TRUE)
	{
		return TRUE;
	}

    return FALSE;
}
