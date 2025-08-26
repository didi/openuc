#ifndef __NET_DEAL_H
#define __NET_DEAL_H


/***************************************************************************************************************

											协议开始

***************************************************************************************************************/

extern char VERSION[8];

#define  CHARGEPILE_GUN_NUM    2	//枪的个数

#define  HeadCmd		0x68


//加密类型
#define		CODETYPE_1		0x00		//不加密
#define		CODETYPE_2		0x01		//3DES加密方式

//桩类型
#define		DCPILE			0x00	//0 表示直流桩，1 表示交流桩
#define		ACPILE			0x01	


//网络类型
#define		SIMCARD 	0x00 	//SIM 卡
#define		NET			0x01 	//LAN
#define		WIFI		0x02	//WAN
#define		OTHER		0x03 	//其他



//帧类型
#define		PILELEND_S			0x01	//充电桩登录认证 充电桩->运营平台
#define 	PILELEND_R			0x02	//登录认证应答 运营平台->充电桩
#define 	PILEHEART_S			0x03	//充电桩心跳包 充电桩->运营平台
#define     PILEHEART_R			0x04	//心跳包应答 运营平台->充电桩
#define		PILERATECHECK_S		0x05	//计费模型验证请求 充电桩->运营平台
#define     PILERATECHECK_R		0x06	//计费模型验证请求应答 运营平台->充电桩
#define		PILERATEASK_S		0x09	//充电桩计费模型请求 充电桩->运营平台
#define		PILERATEASK_R		0x0A	//计费模型请求应答 运营平台->充电桩

#define		RADEDATAREALTIME	0x12	//读取实时监测数据 运营平台->充电桩
#define		OFFLINEDATA			0x13	//离线监测数据 充电桩->运营平台
#define		CHARGESTART			0x15	//充电握手 充电桩->运营平台
#define   	CHARGEPARASET		0x17	//参数配置 充电桩->运营平台
#define   	CHARGESTOP			0x19	//充电结束 充电桩->运营平台
#define		ERRORDATA			0x1B	//错误报文 充电桩->运营平台
#define		CHARGESTOP_BMS		0x1D	//充电阶段 BMS 中止 充电桩->运营平台
#define		CHARGESTOP_PILE		0x21	//充电阶段充电机中止 充电桩->运营平台
#define		CHARGE_BMSREQ		0x23	//充电过程 BMS 需求、充电机输出     			充电桩->运营平台
#define		CHARGE_BMSINFO		0x25	//充电过程 BMS 信息 充电桩->运营平台
#define    	PILENEEDSTARTCHARGE_S		0x31	//充电桩主动申请启动充电 充电桩->运营平台
#define     PILENEEDSTARTCHARGE_R		0x32	//运营平台确认启动充电 运营平台->充电桩
#define		REMOTECHARGE_S		0x33	//远程启机命令回复 充电桩->运营平台
#define		REMOTECHARGE_R		0x34	//运营平台远程控制启机 运营平台->充电桩
#define		REMOTESTOP_S		0x35	//远程停机命令回复 充电桩->运营平台
#define  	REMOTESTOP_R		0x36	//运营平台远程停机 运营平台->充电桩
#define		CHARGEBILL_S		0x3B	//交易记录 充电桩->运营平台
#define     CHARGEBILL_R		0x40	//交易记录确认 运营平台->充电桩
#define		USERBALANCE_S		0x41	//余额更新应答 充电桩->运营平台
#define		USERBALANCE_R		0x42	//远程账户余额更新 运营平台->充电桩
#define 	CARDINFO_S			0x43	//卡数据同步应答 充电桩->运营平台
#define		CARDINFO_R			0x44	//离线卡数据同步 运营平台->充电桩
#define		CARDINFOCLEAR_S		0x45	//离线卡数据清除应答 充电桩->运营平台
#define		CARDINFOCLEAR_R		0x46	//离线卡数据清除 运营平台->充电桩
#define		CARDSEARCH_S		0x47	//离线卡数据查询应答 充电桩->运营平台
#define		CARDSEARCH_R		0x48	//离线卡数据查询 运营平台->充电桩
#define		PILEWORKSET_S		0x51	//充电桩工作参数设置应答 充电桩->运营平台
#define		PILEWORKSET_R		0x52	//充电桩工作参数设置 运营平台->充电桩
#define		TIMECHECK_S			0x55	//对时设置应答 充电桩->运营平台
#define		TIMECHECK_R			0x56	//对时设置 运营平台->充电桩
#define		RATESET_S			0x57	//计费模型应答 充电桩->运营平台
#define		RATESET_R			0x58	//计费模型设置 运营平台->充电桩
#define		GROUNDLOCK_S		0x61	//地锁数据上送（充电桩上送） 充电桩->运营平台
#define		GROUNDLOCK_R		0x62	//遥控地锁升锁与降锁命令（下行）  				运营平台->充电桩
#define		PILERETURNDATA_S	0x63	//充电桩返回数据（上行） 充电桩->运营平台

#define		REMOTORESTART_S		0x91	//远程重启应答 充电桩->运营平台
#define		REMOTORESTART_R		0x92	//远程重启 运营平台->充电桩
#define		REMOTORENEW_S		0x93	//远程更新应答 充电桩->运营平台
#define		REMOTORENEW_R		0x94	//远程更新 运营平台->充电桩

#define		PILENEEDCHARGETWO_S	0xA1	//充电桩主动申请并充充电 充电桩->运营平台
#define		PILENEEDCHARGETWO_R	0xA2	//运营平台确认并充启动充电 运营平台->充电桩
#define		NEEDCHARGETWO_S		0xA3	//远程并充启机命令回复 运营平台->充电桩
#define		NEEDCHARGETWO_R		0xA4	//运营平台远程控制并充启机 充电桩->运营平台
#define  	PILEQRCODE_S		0xF1	//充电桩发送二维码信息回复
#define 	PILEQRCODE_R		0xF0	//运营平台下发二维码信息


#define   NET_LOGIN_STEP			0
#define	  NET_SEND_HEART			1
#define   NET_INIT_FNISH			2





enum {
    REAL_RECORD_RESEND = 0,//实时的充电订单超时重发
    PAST_RECORD_RESEND,    //补推的充电订单超时重发
    AUTHORITY_RESEND,      //刷卡鉴权超时重发
    NETTIMEOUTMAX
};


typedef enum
{
	APP_STOP = 0x40,					//结束充电，APP 远程停止
	SOC_STOP,							//结束充电，SOC 达到 100%
	ELEC_STOP,							//结束充电，充电电量满足设定条件
	MONEY_STOP,							//结束充电，充电金额满足设定条件
	TIME_STOP,							//结束充电，充电时间满足设定条件
	MANUAL_STOP,						//结束充电，手动停止充电
	BMS_REACH_ALLVOL,					//结束充电，电池总压达到

	SYSTEM_FAIL_START = 0x4A,			//充电启动失败，充电桩控制系统故障(需要重启或自动恢复)
	LINK_FAIL_START,					//充电启动失败，控制导引断开
	DUANLUQI_FAIL_START,				//充电启动失败，断路器跳位
	METER_FAIL_START,					//充电启动失败，电表通信中断
	MONEY_FAIL_START,					//充电启动失败，余额不足
	MODULE_FAIL_START,					//充电启动失败，充电模块故障
	EM_FAIL_START,						//充电启动失败，急停开入
	FANGLEI_FAIL_START,					//充电启动失败，防雷器异常
	BMS_NOT_READY,						//充电启动失败，BMS 未就绪
	TEMP_ERROR_START,					//充电启动失败，温度异常
	BATTERY_FANJIE_START,				//充电启动失败，电池反接故障
	ELEC_LOCK_START,					//充电启动失败，电子锁异常
	PUSH_AC_FAIL_START,					//充电启动失败，合闸失败
	JUEYUAN_ERROR_START,				//充电启动失败，绝缘异常

	BHM_OVERTIME_START = 0x59,			//充电启动失败，接收 BMS 握手报文 BHM 超时
	BRM_OVERTIME_START,					//充电启动失败，接收 BMS 和车辆的辨识报文超时 BRM
	BCP_OVERTIME_START,					//充电启动失败，接收电池充电参数报文超时 BCP
	BROAA_OVERTIME_START,				//充电启动失败，接收 BMS 完成充电准备报文超时 BRO AA
	BCS_OVERTIME_START,					//充电启动失败，接收电池充电总状态报文超时 BCS
	BCL_OVERTIME_START,					//充电启动失败，接收电池充电要求报文超时 BCL
	BSM_OVERTIME_START,					//充电启动失败，接收电池状态信息报文超时 BSM
	VOL_OVER_LIMIT,						//充电启动失败，GB2015 电池在BHM 阶段有电压不允许充电
	VOL_OVER_RANGE,						//充电启动失败，GB2015 辨识阶段在 BRO_AA 时候电池实际电压与 BCP 报文电池电压差距大于 5%
	BRO_ERROR_START,					//充电启动失败，B2015 充电机在预充电阶段从 BRO_AA 变成 BRO_00 状态
	CONFIG_OVERTIME,					//充电启动失败，接收主机配置报文超时
	PILE_NO_READY,						//充电启动失败，充电机未准备就绪,我们没有回 CRO AA，对应老国标
	BRO00_OVERTIME_START,				//充电启动失败，接收BRO00报文超时


	SYSTEM_ERR_STOP = 0x6A,				//充电异常中止，系统闭锁---门禁开关异常
	LINK_ERR_STOP,						//充电异常中止，导引断开
	DUANLUQI_ERR_STOP,					//充电异常中止，断路器跳位
	METER_COM_ERR_STOP,					//充电异常中止，电表通信中断
	NO_MONEY_ERR_STOP,					//充电异常中止，余额不足
	AC_PRO_ERR_STOP,					//充电异常中止，交流保护动作
	DC_PRO_ERR_STOP,					//充电异常中止，直流保护动作
	MODULE_ERR_STOP,					//充电异常中止，充电模块故障
	EM_ERR_STOP,						//充电异常中止，急停开入
	FANGLEI_ERR_STOP,					//充电异常中止，防雷器异常
	TEMP_ERR_STOP,						//充电异常中止，温度异常
	OUTPUT_ERR_STOP,					//充电异常中止，输出异常
	CURRENT_ERR_STOP,					//充电异常中止，充电无流
	ELEC_LOCK_ERR_STOP,					//充电异常中止，电子锁异常
	BMS_TEST_JUEYUAN_ERROR,				//充电异常终止，BMS检测到绝缘故障
	ALL_VOL_ERR_STOP = 0x79,			//充电异常中止，总充电电压异常
	ALL_CUR_ERR_STOP,					//充电异常中止，总充电电流异常
	SINGLE_VOL_ERR_STOP,				//充电异常中止，单体充电电压异常
	BAT_TEMP_ERR_STOP,					//充电异常中止，电池组过温
	MAX_SINGLE_VOL_ERR_STOP,			//充电异常中止，最高单体充电电压异常
	MAX_BAT_TEMP_ERR_STOP,				//充电异常中止，最高电池组过温
	BMV_VOL_ERR_STOP,					//充电异常中止，BMV 单体充电电压异常
	BMT_TEMP_ERR_STOP,					//充电异常中止，BMT 电池组过温
	BAT_STATE_ERR_STOP,					//充电异常中止，电池状态异常停止充电
	ZERO_CUR_ERR_STOP,					//充电异常中止，车辆发报文禁止充电
	PILE_SHUTDOWN_STOP,					//充电异常中止，充电桩断电
	BCS_OVERTIME_STOP,					//充电异常中止，接收电池充电总状态报文超时
	BCL_OVERTIME_STOP,					//充电异常中止，接收电池充电要求报文超时
	BCS1_OVERTIME_STOP,					//充电异常中止，接收电池状态信息报文超时
	BST_OVERTIME_STOP,					//充电异常中止，接收 BMS 中止充电报文超时
	BSD_OVERTIME_STOP,					//充电异常中止，接收 BMS 充电统计报文超时
	CCS_OVERTIME_STOP,					//充电异常中止，接收对侧 CCS 报文超时
	BMS_LINK_TEMP_ERROR,				//充电异常终止，BMS检测到连接器过温故障
	BMS_ELEMENT_TEMP_ERROR,				//充电异常中止，BMS元器件过温故障
	BMS_TEST_LINK_ERROR,				//充电异常中止，BMS检测到连接故障
	BMS_CC2_VOL_ERROR,					//充电异常中止，BMS检测到CC2电压异常
	BMS_TSET_OTHER_ERROR,				//充电异常中止，BMS检测到其他故障
	BMS_SEND_BEM_ERROR,					//充电异常中止，BMS检测到充电桩发送报文超时
	NULL_REASON = 0x90,					//未知原因停止
}CHARGE_STOP_REASON;

typedef struct
{
    INT8U  PileID[7];            	// 桩编号			不清零
	INT8U  IPAddress[15];        // IP地址
	INT32U  IPPort_IP;			//端口
	INT8U  Use4GModule;			//是否使用无线网
}__attribute__((packed)) ParamSet;
extern ParamSet PSet;//参数设置结构体、保存在EEPROM

typedef struct
{
	INT8U SocketCreateOk;				//TCP长连接创建成功标志
	INT8U NetSuccessFlag;				//和平台注册数据交互完成
	INT8U RecoderInSending;				//账单正在发送、另一个发送账单需要等待
	INT8U NetFailTimes;					//发送次数累计，如果超过3次发送未接受     认为已经断网   需要重新连接----20220906 modify
	INT8U RecoderUpFlag;				//上传充电记录到平台标志
    INT8U Init_step;        //网络初始化阶段的状态标志
	INT8U RequestedFlag;    //是否已向平台发送请求的状态标志
    INT32U  RequestedTime;  //向平台发送请求时的时间点
    INT32U GunSendHeartTime;//枪向平台发送的心跳时间
    INT32U GunBSendHeartTime;//B枪向平台发送的心跳时间
	INT8U	NeedReqNewRateType;	//需要向平台索要新的计费模型
	INT16U	RateType;		//计费模型、上电后首次连接平台置为0
	INT8U	NewPileID[7];		//新桩编号、根据云快充协议是7字节的数据
	INT8U	LockPileState;	//锁定充电桩
	INT8U  RestartFlag;		//远程重启标志0x01：立即执行0x02：空闲执行
}stNetConFlag;
extern stNetConFlag NetFlag;



typedef struct
{
	INT16U	RateType;		//计费模型、上电后首次连接平台置为0
	INT32U  ElecRate[4];	//尖峰平谷电费   		接收值=实际值*100000  也就是五位小数
	INT32U 	ServRate[4];	//尖峰平谷服务费			接收值=实际值*10000  0也就是五位小数
	INT8U 	LossPercent;	//计损比例
	INT8U	TimeSet[48];	//每小时存一个计费类型、0x00：尖费率 0x01：峰费率 0x02：平费率 0x03：谷费率
}stRateInfo;
extern stRateInfo	PileRateInfo;


typedef struct
{
	stRateInfo	ChargeRateInfo;	//开始充电后的计费模型，每次开始充电的时候从系统中获取，充电过程中不改变
	INT32U	ChargeMeterData[4];	//尖峰平谷用电量		4位小数
	INT32U	ChargeLossData[4];	//尖峰平谷计损电量4位小数
	INT32U	ChargeMoney[4];		//尖峰平谷消费金额4位小数
	INT8U	StartMeterHigh;		//电表总起值StartMeterHigh<<4|StartMeterLow
	INT32U  StartMeterLow;		//4位小数
	INT8U	StopMeterHigh;		//电表总止值StopMeterHigh<<4|StopMeterLow
	INT32U  StopMeterLow;
}stChargeMoneyInfo;

typedef struct
{
	/*
	0：二维码前缀+14 位桩编号
		如：www.baidu.com？No=34220001000233
	1：二维码前缀+14 位桩编号+2 位枪编号
		如：（A 枪）www.baidu.com？No=3422000100023301
		    （B 枪）www.baidu.com？No=3422000100023302
	如果是单枪充电桩，使用 A 枪二维码。
	*/
	INT8U QRCodeFlag;
	INT8U QRCodeType;		//二维码下发格式
	INT8U PreFixLen;		//前缀长度
	INT8U PreFixData[200];	//前缀内容，可能用不到200个长度
}stPileQRCodeInfo;
extern stPileQRCodeInfo QRCodeInfo;


typedef struct
{
	INT8U OrderNumber[16];			//交易流水号,从平台获得
    INT8U PileGunStateBig;   	//枪状态 ==0正常     		==1 故障
    INT8U PileGunState;			//枪小状态0x00：离线			0x01：故障		0x02：空闲		0x03：充电		需做到变位上送
    INT8U PileGunStateOld;
    INT8U GunReturnState;			//枪是否已经归位0x00:否 0x01:是 0x02:未知（无法检测到枪是否插回枪座即未知）
    INT8U IfGunInsert;			//枪是否已经插入车端0x00:否 0x01:是				需做到变位上送
    INT8U IfGunInsertOld;
    INT16U OutVoltage;			//输出电压：精确到小数点后一位；待机置零
    INT16U OutCurrent;			//输出电流：精确到小数点后一位；待机置零
    INT8U GunTemp;			//枪线温度：偏移量-50；待机置零
    INT8U GunSOC;				//SOC:待机置零；交流桩置零
    INT8U BatHighTemp;			//电池组最高温度偏移量-50 oC；待机置零；交流桩置零
    INT16U	GunChargeTime;		//累计充电时间单位：min；待机置零
    INT16U	RemainTime;			//剩余时间单位：min；待机置零、交流桩置零
    INT32U	ChargeMeterData;	//已充电度数精确到小数点后四位；待机置零
    INT32U  LossMeterData;	//已充电计损度数精确到小数点后四位；待机置零,未设置计损比例时等于充电度数
    INT32U 	ChargeMoney;	//已充电费用精确到小数点后四位；待机置零							计算公式：（电费+服务费）*计损充电度数
    INT16U	HardFaultData;	//硬件故障，解析如下
    /*
	Bit 位表示（0 否 1 是），低位到高位顺序
	Bit1：急停按钮动作故障；
	Bit2：无可用整流模块；
	Bit3：出风口温度过高；
	Bit4：交流防雷故障；
	Bit5：交直流模块 DC20 通信中断；
	Bit6：绝缘检测模块 FC08 通信中断；
	Bit7：电度表通信中断；
	Bit8：读卡器通信中断；
	Bit9：RC10 通信中断；
	Bit10：风扇调速板故障；
	Bit11：直流熔断器故障；
	Bit12：高压接触器故障；
	Bit13：门打开；
	*/
	//和BMS通信类报警信息===>被赋值0x02时说明改报文超时
	u8 Rec_BRM_OverTime;		//桩接收 BMS 和车辆的辨识报文超时
	u8 Rec_BCP_OverTime;		//桩接收电池充电参数报文超时
	u8 Rec_BRO_OverTime;		//桩接收 BMS 完成充电准备报文超时
	u8 Rec_BCS_OverTime;		//桩接收电池充电总状态报文超时
	u8 Rec_BCL_OverTime;		//桩接收电池充电要求报文超时
	u8 Rec_BST_OverTime;		//桩接收 BMS 中止充电报文超时
	u8 Rec_BSD_OverTime;		//桩接收 BMS 充电统计报文超时
	u8 Rec_Other_OverTime;		//桩接收其他报文超时
	
	//充电机停止充电原因
	u8 PileStopReason;			//充电机停止充电原因
	u16 PileStopRrror1; 		//充电机 中止充电故障原因
	u8 PileStopRrror2;			//充电机 中止充电错误原因
	
	//充电桩输出值
	u16 PileOutputVol;			//桩输出电压值0.1 V/位，0 V 偏移量
	u16 PileOutputCur;			//桩输出电流值0.1 A/位，-400 A 偏移量
	//桩主动请求启动充电信息
	u8 StartChargeMode; 		//0x01:表示通过刷卡启动充电				0x02:暂不支持			0x03:vin码启动充电
	u8 CardID[8];				//物理卡号(刷卡的和扫码下发的)
	u8 CardCode[16];			//刷卡密码,通常不用
	u8 LogicCardNum[8];			//平台返回---显示在显示屏上
	u8 CardCheckOK;				//刷卡鉴权成功
	u32	UserBalance;			//用户余额、保留两位小数
	u8 CardCheckFailReason;		//卡鉴权失败原因,详细原因如下
	/*
	0x01 账户不存在
	0x02 账户冻结
	0x03 账户余额不足
	0x04 该卡存在未结账记录
	0x05 桩停用
	0x06 该账户不能在此桩上充电
	0x07 密码错误
	0x08 电站电容不足
	0x09 系统中 vin 码不存在
	0x0A 该桩存在未结账记录
	0x0B 该桩不支持刷卡
	*/
	u8 RemotoStartFailReason;	//扫码启动失败原因
	/*
	0x00 无
	0x01 设备编号不匹配
	0x02 枪已在充电
	0x03 设备故障
	0x04 设备离线
	0x05 未插枪
	桩在收到启充命令后,检测到未插枪则发送 0x33 报文回复充电失败。若在 60 秒（以收到 0x34 时间开始计算）内检测到枪重新连接，则补送 0x33 成功报文;超时或者 离线等其他异常，桩不启充、不补
	发 0x33 报文
	*/
	u8 RemotoStopFailReason;	//扫码启动失败原因
	/*
	0x00 无
	0x01 设备编号不匹配
	0x02 枪未处于充电状态
	0x03 其他
	*/
	u8 StartTime[7];		//开始充电时间，CP56Time2a 格式
	u8 StopTime[7];			//结束充电时间，CP56Time2a 格式
	stChargeMoneyInfo ChargeMoneyInfo;
	INT8U RecordStartMode;		//0x01：app 启动 0x02：卡启动         0x04：离线卡启动         0x05: vin 码启动充电
	INT8U StopChargeReason;		//停止原因
}stChargeInfo;
extern stChargeInfo PileChargeInfo[CHARGEPILE_GUN_NUM];

//BMS的电池参数结构体
typedef struct
{
	//握手阶段
	u16 MaxChargeVol;			//最高允许充电总电压，0.1V/位     0V偏移量
	u8 ProtocolVer[3];			//BMS通信协议版本当前版本为 V1.1，表示为：byte3， byte2—0001H；byte1—01H
	_BATTERY_TYPE  BatteryType;	//电池类型    eg:sBatteryPara.BatteryType = (_BATTERY_TYPE)LEAD_ACID_BATTERY
	u16 CarPowerCapacity;		//整车电池额定容量，单位Ah  0.1Ah/位      0偏移量
	u16 CarVoltageCapacity;		//整车电池额定总电压   单位V  0.1V/位      0偏移量
	u32 BatteryManufacturer;	//电池生产厂家     标准ASCII码     可选项，有可能没有此项内容
	u32 BatteryNumber;			//电池组序号       可选项，有可能没有此项内容
	u8  BatteryCreateYear;		//电池生产时间  年  偏移量1985  可选项，有可能没有此项内容
	u8  BatteryCreateMouth;		//电池生产时间  月  偏移量0  可选项，有可能没有此项内容
	u8  BatteryCreateDay;		//电池生产时间  日  偏移量0  可选项，有可能没有此项内容
	u32 BatteryChargeTimes;		//电池充电次数   偏移量0  可选项，有可能没有此项内容
	u8  BatteryOwner;			//电池产权标识   0：租赁      1：车自有
	u8 * CarVINCode;			//车辆VIN码
	//电池充电参数
	u16 SingleBatteryMaxVol;	//单体电池最高允许充电电压   0.01V/位    0V偏移量       范围0-24V
	u16 SingleBatteryMinVol;	//单体电池最低允许充电电压
	u16 MaxChargeCurrent;		//最高允许充电电流		 0.1A/位		-400A偏移量
	u16 BatteryAllPower;		//电池标称总能量	0.1kW?h/位    0偏移量    范围0--1000
	u16 BatterySOC;				//电池荷电状态		0.1%/位      0偏移量     范围0--1000
	u16 BatteryTempVol;			//电池当前电压		0.1V/位       0偏移量
	u8  AllowMaxTemp;			//最高允许的充电温度
	//电池充电阶段的实时需求
	u16 BatteryNeedVol;			//电池充电需求电压     0.1V/位       0偏移量
	u16 BatteryNeedCur;			//电池充电需求电流 	0.1A/位		-400A偏移量
	u8  BatteryChargeMode;		//车充电需求模式   0x01==>恒压充电   	 0x02==>恒流充电
	//充电阶段
	u16 BatteryMeasureVol;		//车充电电压测量值	0.1V/位       0偏移量
	u16 BatteryMeasureCur;		//车充电电流测量值	0.1A/位		-400A偏移量
	u16 Battery_Max_VolAndNum;	//BMS 最高单体动力蓄电池电压及组号
	u16 MaxVol_Num;				//最高充电电压的组号		1/位     0偏移量      范围0--15
	u16 RemainTime;				//充电估算剩余时间	1min/位     0偏移量      范围0--600
	//温度信息
	u8  BatteryMaxTemp;			//最高温度	1度/位     -50偏移量     范围-50---+200
	u8  MaxTempNum;				//最高温度的编号     	1/位    1偏移量       范围1--128
	u8  BatteryMinTemp;			//最低温度	1度/位     -50偏移量     范围-50---+200
	u8  MinTempNum;				//最低温度的编号	1/位    1偏移量       范围1--128
	//报警信息以及充电故障
	u16	BatteryError1;			//BMS 中止充电故障原因
	u8  BatteryError2;			//BMS 中止充电错误原因
	u16 BatteryBSM;				//BSM报文的信息
	u8  BatteryVolErr;			//单体电压报警    0x00==>正常       0x01==>过高  	 0x02==>过低
	u8  BatterySOCErr;			//荷电报警    0x00==>正常       0x01==>过高  	 0x02==>过低
	u8  BatteryCurErr;			//电流报警    0x00==>正常       0x01==>过高  	 0x02==>不可信状态
	u8  BatteryTempErr;			//温度报警    0x00==>正常       0x01==>不正常  	 0x02==>不可信状态
	u8  BatteryJueYuanErr;		//绝缘报警    0x00==>正常       0x01==>不正常  	 0x02==>不可信状态
	u8  BatteryLinkErr;			//连接报警    0x00==>正常       0x01==>不正常  	 0x02==>不可信状态
	u8  BatteryChargeAllow;		//充电报警    0x00==>允许       0x01==>禁止
	u8  LinkTempErr;			//连接器过温故障		0x00==>正常       0x01==>故障  	 0x02==>不可信状态
	u8  BMSElementErr;			//BMS元件过温故障		0x00==>正常       0x01==>故障  	 0x02==>不可信状态
	u8  GaoyaRelayErr;			//高压继电器故障		0x00==>正常       0x01==>故障  	 0x02==>不可信状态
	u8  CC2VolErr;				//监测点2电压故障		0x00==>正常       0x01==>故障  	 0x02==>不可信状态
	u8  OtherErr;				//其他故障信息			0x00==>正常       0x01==>故障  	 0x02==>不可信状态
	//停止充电原因
	u8  ChargeStopReason;		//停止原因
	u8  BatterySOCStop;			//达到SOC停止 		0x00==>未达到       0x01==>达到  	 0x02==>不可信状态
	u8  BatteryVolStop;			//达到总电压停止	0x00==>未达到       0x01==>达到  	 0x02==>不可信状态
	u8  SingleVolStop;			//单体电压停止		0x00==>未达到       0x01==>达到  	 0x02==>不可信状态
	u8  ChargePileStop;			//充电机主动停止	0x00==>正常           0x01==>主动停止    0x02==>不可信状态
	//报文接收超时
	u8  RecognizeOverTime_1;	//BMS接收0x00的辨识报文超时     0x00==>正常           0x01==>超时    0x02==>不可信状态
	u8  RecognizeOverTime_2;	//BMS接收0xAA的辨识报文超时
	u8  MaxOutputOverTime;		//BMS接收时间同步和最大输出能力报文超时
	u8  PileReadyOverTime;		//BMS接收充电机准备报文超时
	u8  PileStateOverTime;		//BMS接收充电机状态把报文超时
	u8  PileStopOverTime;		//BMS接收充电机中止报文超时
	u8  PileStatisOverTime;		//BMS接收充电机统计信息超时
	u8  OtherOverTime;			//BMS接收其他报文超时
	u8  DealBEMMsgFlag;			//是否处理BEM异常报文
	u8  MassageOverTime;		//BMS检测到报文超时标志位
	u8  LastBEMMsgCode;			//上一次错误报文的错误码
	u8  RecvErrorTimes;			//接收相同的错误码的次数   超过3次认为通讯异常
}stBatteryPara;
extern stBatteryPara BatteryInfo[CHARGEPILE_GUN_NUM];


void PileSendRemotoStartCharge(INT8U GunNum,INT8U Result);
void PileSendStartCharge(INT8U GunNum);
void PileSendServeiceStart(void);
void PileSendHeart(u8 GunNum);
void PileSendCheckRate(void);
void PileSendRequestRate(void);
void PileSendRealTimeInfo(INT8U GunNum);
void PileSendChargeStep1Info(INT8U GunNum);
void PileSendChargeStep2Info(INT8U GunNum);
void PileSendChargeStep3Info(INT8U GunNum);
void PileSendChargeErrorInfo(INT8U GunNum);
void PileSendStop1Info(INT8U GunNum);
void PileSendStop2Info(INT8U GunNum);
void PileSendCharge1Info(INT8U GunNum);
void PileSendCharge2Info(INT8U GunNum);
void PileSendStartCharge(INT8U GunNum);
void PileSendRemotoStartCharge(INT8U GunNum,INT8U Result);
void PileSendRemotoStopCharge(INT8U GunNum,INT8U Result);
void PileSendRecordData(INT8U GunNum);
void PileSendTimeCheck(void);
void PileSendRateSet(void);
u8 Receive_TimeCheckInfo(u8 *rbuf);
err_t NetSendData(unsigned char *pucBuf,unsigned short usLen);
void NET_CmdDump(u8 *buf, int len);
void NetTimeOutSet(u8 GunNum, u8 item, u8* buf, int len, int repeat);
void NetTimeOutClear(u8 GunNum, int item);
void NetDealTask(void *pvParameters);
void NET_DataInit(void);
u8 Net_ConnectedFlag(void);
void Send_Rec_By4GModule(u8 GunNum);
#endif
