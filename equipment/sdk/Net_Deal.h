#ifndef __NET_DEAL_H
#define __NET_DEAL_H


/***************************************************************************************************************

											Э�鿪ʼ

***************************************************************************************************************/

extern char VERSION[8];

#define  CHARGEPILE_GUN_NUM    2	//ǹ�ĸ���

#define  HeadCmd		0x68


//��������
#define		CODETYPE_1		0x00		//������
#define		CODETYPE_2		0x01		//3DES���ܷ�ʽ

//׮����
#define		DCPILE			0x00	//0 ��ʾֱ��׮��1 ��ʾ����׮
#define		ACPILE			0x01	


//��������
#define		SIMCARD 	0x00 	//SIM ��
#define		NET			0x01 	//LAN
#define		WIFI		0x02	//WAN
#define		OTHER		0x03 	//����



//֡����
#define		PILELEND_S			0x01	//���׮��¼��֤ ���׮->��Ӫƽ̨
#define 	PILELEND_R			0x02	//��¼��֤Ӧ�� ��Ӫƽ̨->���׮
#define 	PILEHEART_S			0x03	//���׮������ ���׮->��Ӫƽ̨
#define     PILEHEART_R			0x04	//������Ӧ�� ��Ӫƽ̨->���׮
#define		PILERATECHECK_S		0x05	//�Ʒ�ģ����֤���� ���׮->��Ӫƽ̨
#define     PILERATECHECK_R		0x06	//�Ʒ�ģ����֤����Ӧ�� ��Ӫƽ̨->���׮
#define		PILERATEASK_S		0x09	//���׮�Ʒ�ģ������ ���׮->��Ӫƽ̨
#define		PILERATEASK_R		0x0A	//�Ʒ�ģ������Ӧ�� ��Ӫƽ̨->���׮

#define		RADEDATAREALTIME	0x12	//��ȡʵʱ������� ��Ӫƽ̨->���׮
#define		OFFLINEDATA			0x13	//���߼������ ���׮->��Ӫƽ̨
#define		CHARGESTART			0x15	//������� ���׮->��Ӫƽ̨
#define   	CHARGEPARASET		0x17	//�������� ���׮->��Ӫƽ̨
#define   	CHARGESTOP			0x19	//������ ���׮->��Ӫƽ̨
#define		ERRORDATA			0x1B	//������ ���׮->��Ӫƽ̨
#define		CHARGESTOP_BMS		0x1D	//���׶� BMS ��ֹ ���׮->��Ӫƽ̨
#define		CHARGESTOP_PILE		0x21	//���׶γ�����ֹ ���׮->��Ӫƽ̨
#define		CHARGE_BMSREQ		0x23	//������ BMS ���󡢳������     			���׮->��Ӫƽ̨
#define		CHARGE_BMSINFO		0x25	//������ BMS ��Ϣ ���׮->��Ӫƽ̨
#define    	PILENEEDSTARTCHARGE_S		0x31	//���׮��������������� ���׮->��Ӫƽ̨
#define     PILENEEDSTARTCHARGE_R		0x32	//��Ӫƽ̨ȷ��������� ��Ӫƽ̨->���׮
#define		REMOTECHARGE_S		0x33	//Զ����������ظ� ���׮->��Ӫƽ̨
#define		REMOTECHARGE_R		0x34	//��Ӫƽ̨Զ�̿������� ��Ӫƽ̨->���׮
#define		REMOTESTOP_S		0x35	//Զ��ͣ������ظ� ���׮->��Ӫƽ̨
#define  	REMOTESTOP_R		0x36	//��Ӫƽ̨Զ��ͣ�� ��Ӫƽ̨->���׮
#define		CHARGEBILL_S		0x3B	//���׼�¼ ���׮->��Ӫƽ̨
#define     CHARGEBILL_R		0x40	//���׼�¼ȷ�� ��Ӫƽ̨->���׮
#define		USERBALANCE_S		0x41	//������Ӧ�� ���׮->��Ӫƽ̨
#define		USERBALANCE_R		0x42	//Զ���˻������� ��Ӫƽ̨->���׮
#define 	CARDINFO_S			0x43	//������ͬ��Ӧ�� ���׮->��Ӫƽ̨
#define		CARDINFO_R			0x44	//���߿�����ͬ�� ��Ӫƽ̨->���׮
#define		CARDINFOCLEAR_S		0x45	//���߿��������Ӧ�� ���׮->��Ӫƽ̨
#define		CARDINFOCLEAR_R		0x46	//���߿�������� ��Ӫƽ̨->���׮
#define		CARDSEARCH_S		0x47	//���߿����ݲ�ѯӦ�� ���׮->��Ӫƽ̨
#define		CARDSEARCH_R		0x48	//���߿����ݲ�ѯ ��Ӫƽ̨->���׮
#define		PILEWORKSET_S		0x51	//���׮������������Ӧ�� ���׮->��Ӫƽ̨
#define		PILEWORKSET_R		0x52	//���׮������������ ��Ӫƽ̨->���׮
#define		TIMECHECK_S			0x55	//��ʱ����Ӧ�� ���׮->��Ӫƽ̨
#define		TIMECHECK_R			0x56	//��ʱ���� ��Ӫƽ̨->���׮
#define		RATESET_S			0x57	//�Ʒ�ģ��Ӧ�� ���׮->��Ӫƽ̨
#define		RATESET_R			0x58	//�Ʒ�ģ������ ��Ӫƽ̨->���׮
#define		GROUNDLOCK_S		0x61	//�����������ͣ����׮���ͣ� ���׮->��Ӫƽ̨
#define		GROUNDLOCK_R		0x62	//ң�ص��������뽵��������У�  				��Ӫƽ̨->���׮
#define		PILERETURNDATA_S	0x63	//���׮�������ݣ����У� ���׮->��Ӫƽ̨

#define		REMOTORESTART_S		0x91	//Զ������Ӧ�� ���׮->��Ӫƽ̨
#define		REMOTORESTART_R		0x92	//Զ������ ��Ӫƽ̨->���׮
#define		REMOTORENEW_S		0x93	//Զ�̸���Ӧ�� ���׮->��Ӫƽ̨
#define		REMOTORENEW_R		0x94	//Զ�̸��� ��Ӫƽ̨->���׮

#define		PILENEEDCHARGETWO_S	0xA1	//���׮�������벢���� ���׮->��Ӫƽ̨
#define		PILENEEDCHARGETWO_R	0xA2	//��Ӫƽ̨ȷ�ϲ���������� ��Ӫƽ̨->���׮
#define		NEEDCHARGETWO_S		0xA3	//Զ�̲�����������ظ� ��Ӫƽ̨->���׮
#define		NEEDCHARGETWO_R		0xA4	//��Ӫƽ̨Զ�̿��Ʋ������� ���׮->��Ӫƽ̨
#define  	PILEQRCODE_S		0xF1	//���׮���Ͷ�ά����Ϣ�ظ�
#define 	PILEQRCODE_R		0xF0	//��Ӫƽ̨�·���ά����Ϣ


#define   NET_LOGIN_STEP			0
#define	  NET_SEND_HEART			1
#define   NET_INIT_FNISH			2





enum {
    REAL_RECORD_RESEND = 0,//ʵʱ�ĳ�綩����ʱ�ط�
    PAST_RECORD_RESEND,    //���Ƶĳ�綩����ʱ�ط�
    AUTHORITY_RESEND,      //ˢ����Ȩ��ʱ�ط�
    NETTIMEOUTMAX
};


typedef enum
{
	APP_STOP = 0x40,					//������磬APP Զ��ֹͣ
	SOC_STOP,							//������磬SOC �ﵽ 100%
	ELEC_STOP,							//������磬�����������趨����
	MONEY_STOP,							//������磬����������趨����
	TIME_STOP,							//������磬���ʱ�������趨����
	MANUAL_STOP,						//������磬�ֶ�ֹͣ���
	BMS_REACH_ALLVOL,					//������磬�����ѹ�ﵽ

	SYSTEM_FAIL_START = 0x4A,			//�������ʧ�ܣ����׮����ϵͳ����(��Ҫ�������Զ��ָ�)
	LINK_FAIL_START,					//�������ʧ�ܣ����Ƶ����Ͽ�
	DUANLUQI_FAIL_START,				//�������ʧ�ܣ���·����λ
	METER_FAIL_START,					//�������ʧ�ܣ����ͨ���ж�
	MONEY_FAIL_START,					//�������ʧ�ܣ�����
	MODULE_FAIL_START,					//�������ʧ�ܣ����ģ�����
	EM_FAIL_START,						//�������ʧ�ܣ���ͣ����
	FANGLEI_FAIL_START,					//�������ʧ�ܣ��������쳣
	BMS_NOT_READY,						//�������ʧ�ܣ�BMS δ����
	TEMP_ERROR_START,					//�������ʧ�ܣ��¶��쳣
	BATTERY_FANJIE_START,				//�������ʧ�ܣ���ط��ӹ���
	ELEC_LOCK_START,					//�������ʧ�ܣ��������쳣
	PUSH_AC_FAIL_START,					//�������ʧ�ܣ���բʧ��
	JUEYUAN_ERROR_START,				//�������ʧ�ܣ���Ե�쳣

	BHM_OVERTIME_START = 0x59,			//�������ʧ�ܣ����� BMS ���ֱ��� BHM ��ʱ
	BRM_OVERTIME_START,					//�������ʧ�ܣ����� BMS �ͳ����ı�ʶ���ĳ�ʱ BRM
	BCP_OVERTIME_START,					//�������ʧ�ܣ����յ�س��������ĳ�ʱ BCP
	BROAA_OVERTIME_START,				//�������ʧ�ܣ����� BMS ��ɳ��׼�����ĳ�ʱ BRO AA
	BCS_OVERTIME_START,					//�������ʧ�ܣ����յ�س����״̬���ĳ�ʱ BCS
	BCL_OVERTIME_START,					//�������ʧ�ܣ����յ�س��Ҫ���ĳ�ʱ BCL
	BSM_OVERTIME_START,					//�������ʧ�ܣ����յ��״̬��Ϣ���ĳ�ʱ BSM
	VOL_OVER_LIMIT,						//�������ʧ�ܣ�GB2015 �����BHM �׶��е�ѹ��������
	VOL_OVER_RANGE,						//�������ʧ�ܣ�GB2015 ��ʶ�׶��� BRO_AA ʱ����ʵ�ʵ�ѹ�� BCP ���ĵ�ص�ѹ������ 5%
	BRO_ERROR_START,					//�������ʧ�ܣ�B2015 ������Ԥ���׶δ� BRO_AA ��� BRO_00 ״̬
	CONFIG_OVERTIME,					//�������ʧ�ܣ������������ñ��ĳ�ʱ
	PILE_NO_READY,						//�������ʧ�ܣ�����δ׼������,����û�л� CRO AA����Ӧ�Ϲ���
	BRO00_OVERTIME_START,				//�������ʧ�ܣ�����BRO00���ĳ�ʱ


	SYSTEM_ERR_STOP = 0x6A,				//����쳣��ֹ��ϵͳ����---�Ž������쳣
	LINK_ERR_STOP,						//����쳣��ֹ�������Ͽ�
	DUANLUQI_ERR_STOP,					//����쳣��ֹ����·����λ
	METER_COM_ERR_STOP,					//����쳣��ֹ�����ͨ���ж�
	NO_MONEY_ERR_STOP,					//����쳣��ֹ������
	AC_PRO_ERR_STOP,					//����쳣��ֹ��������������
	DC_PRO_ERR_STOP,					//����쳣��ֹ��ֱ����������
	MODULE_ERR_STOP,					//����쳣��ֹ�����ģ�����
	EM_ERR_STOP,						//����쳣��ֹ����ͣ����
	FANGLEI_ERR_STOP,					//����쳣��ֹ���������쳣
	TEMP_ERR_STOP,						//����쳣��ֹ���¶��쳣
	OUTPUT_ERR_STOP,					//����쳣��ֹ������쳣
	CURRENT_ERR_STOP,					//����쳣��ֹ���������
	ELEC_LOCK_ERR_STOP,					//����쳣��ֹ���������쳣
	BMS_TEST_JUEYUAN_ERROR,				//����쳣��ֹ��BMS��⵽��Ե����
	ALL_VOL_ERR_STOP = 0x79,			//����쳣��ֹ���ܳ���ѹ�쳣
	ALL_CUR_ERR_STOP,					//����쳣��ֹ���ܳ������쳣
	SINGLE_VOL_ERR_STOP,				//����쳣��ֹ���������ѹ�쳣
	BAT_TEMP_ERR_STOP,					//����쳣��ֹ����������
	MAX_SINGLE_VOL_ERR_STOP,			//����쳣��ֹ����ߵ������ѹ�쳣
	MAX_BAT_TEMP_ERR_STOP,				//����쳣��ֹ����ߵ�������
	BMV_VOL_ERR_STOP,					//����쳣��ֹ��BMV �������ѹ�쳣
	BMT_TEMP_ERR_STOP,					//����쳣��ֹ��BMT ��������
	BAT_STATE_ERR_STOP,					//����쳣��ֹ�����״̬�쳣ֹͣ���
	ZERO_CUR_ERR_STOP,					//����쳣��ֹ�����������Ľ�ֹ���
	PILE_SHUTDOWN_STOP,					//����쳣��ֹ�����׮�ϵ�
	BCS_OVERTIME_STOP,					//����쳣��ֹ�����յ�س����״̬���ĳ�ʱ
	BCL_OVERTIME_STOP,					//����쳣��ֹ�����յ�س��Ҫ���ĳ�ʱ
	BCS1_OVERTIME_STOP,					//����쳣��ֹ�����յ��״̬��Ϣ���ĳ�ʱ
	BST_OVERTIME_STOP,					//����쳣��ֹ������ BMS ��ֹ��籨�ĳ�ʱ
	BSD_OVERTIME_STOP,					//����쳣��ֹ������ BMS ���ͳ�Ʊ��ĳ�ʱ
	CCS_OVERTIME_STOP,					//����쳣��ֹ�����նԲ� CCS ���ĳ�ʱ
	BMS_LINK_TEMP_ERROR,				//����쳣��ֹ��BMS��⵽���������¹���
	BMS_ELEMENT_TEMP_ERROR,				//����쳣��ֹ��BMSԪ�������¹���
	BMS_TEST_LINK_ERROR,				//����쳣��ֹ��BMS��⵽���ӹ���
	BMS_CC2_VOL_ERROR,					//����쳣��ֹ��BMS��⵽CC2��ѹ�쳣
	BMS_TSET_OTHER_ERROR,				//����쳣��ֹ��BMS��⵽��������
	BMS_SEND_BEM_ERROR,					//����쳣��ֹ��BMS��⵽���׮���ͱ��ĳ�ʱ
	NULL_REASON = 0x90,					//δ֪ԭ��ֹͣ
}CHARGE_STOP_REASON;

typedef struct
{
    INT8U  PileID[7];            	// ׮���			������
	INT8U  IPAddress[15];        // IP��ַ
	INT32U  IPPort_IP;			//�˿�
	INT8U  Use4GModule;			//�Ƿ�ʹ��������
}__attribute__((packed)) ParamSet;
extern ParamSet PSet;//�������ýṹ�塢������EEPROM

typedef struct
{
	INT8U SocketCreateOk;				//TCP�����Ӵ����ɹ���־
	INT8U NetSuccessFlag;				//��ƽ̨ע�����ݽ������
	INT8U RecoderInSending;				//�˵����ڷ��͡���һ�������˵���Ҫ�ȴ�
	INT8U NetFailTimes;					//���ʹ����ۼƣ��������3�η���δ����     ��Ϊ�Ѿ�����   ��Ҫ��������----20220906 modify
	INT8U RecoderUpFlag;				//�ϴ�����¼��ƽ̨��־
    INT8U Init_step;        //�����ʼ���׶ε�״̬��־
	INT8U RequestedFlag;    //�Ƿ�����ƽ̨���������״̬��־
    INT32U  RequestedTime;  //��ƽ̨��������ʱ��ʱ���
    INT32U GunSendHeartTime;//ǹ��ƽ̨���͵�����ʱ��
    INT32U GunBSendHeartTime;//Bǹ��ƽ̨���͵�����ʱ��
	INT8U	NeedReqNewRateType;	//��Ҫ��ƽ̨��Ҫ�µļƷ�ģ��
	INT16U	RateType;		//�Ʒ�ģ�͡��ϵ���״�����ƽ̨��Ϊ0
	INT8U	NewPileID[7];		//��׮��š������ƿ��Э����7�ֽڵ�����
	INT8U	LockPileState;	//�������׮
	INT8U  RestartFlag;		//Զ��������־0x01������ִ��0x02������ִ��
}stNetConFlag;
extern stNetConFlag NetFlag;



typedef struct
{
	INT16U	RateType;		//�Ʒ�ģ�͡��ϵ���״�����ƽ̨��Ϊ0
	INT32U  ElecRate[4];	//���ƽ�ȵ��   		����ֵ=ʵ��ֵ*100000  Ҳ������λС��
	INT32U 	ServRate[4];	//���ƽ�ȷ����			����ֵ=ʵ��ֵ*10000  0Ҳ������λС��
	INT8U 	LossPercent;	//�������
	INT8U	TimeSet[48];	//ÿСʱ��һ���Ʒ����͡�0x00������� 0x01������� 0x02��ƽ���� 0x03���ȷ���
}stRateInfo;
extern stRateInfo	PileRateInfo;


typedef struct
{
	stRateInfo	ChargeRateInfo;	//��ʼ����ļƷ�ģ�ͣ�ÿ�ο�ʼ����ʱ���ϵͳ�л�ȡ���������в��ı�
	INT32U	ChargeMeterData[4];	//���ƽ���õ���		4λС��
	INT32U	ChargeLossData[4];	//���ƽ�ȼ������4λС��
	INT32U	ChargeMoney[4];		//���ƽ�����ѽ��4λС��
	INT8U	StartMeterHigh;		//�������ֵStartMeterHigh<<4|StartMeterLow
	INT32U  StartMeterLow;		//4λС��
	INT8U	StopMeterHigh;		//�����ֵֹStopMeterHigh<<4|StopMeterLow
	INT32U  StopMeterLow;
}stChargeMoneyInfo;

typedef struct
{
	/*
	0����ά��ǰ׺+14 λ׮���
		�磺www.baidu.com��No=34220001000233
	1����ά��ǰ׺+14 λ׮���+2 λǹ���
		�磺��A ǹ��www.baidu.com��No=3422000100023301
		    ��B ǹ��www.baidu.com��No=3422000100023302
	����ǵ�ǹ���׮��ʹ�� A ǹ��ά�롣
	*/
	INT8U QRCodeFlag;
	INT8U QRCodeType;		//��ά���·���ʽ
	INT8U PreFixLen;		//ǰ׺����
	INT8U PreFixData[200];	//ǰ׺���ݣ������ò���200������
}stPileQRCodeInfo;
extern stPileQRCodeInfo QRCodeInfo;


typedef struct
{
	INT8U OrderNumber[16];			//������ˮ��,��ƽ̨���
    INT8U PileGunStateBig;   	//ǹ״̬ ==0����     		==1 ����
    INT8U PileGunState;			//ǹС״̬0x00������			0x01������		0x02������		0x03�����		��������λ����
    INT8U PileGunStateOld;
    INT8U GunReturnState;			//ǹ�Ƿ��Ѿ���λ0x00:�� 0x01:�� 0x02:δ֪���޷���⵽ǹ�Ƿ���ǹ����δ֪��
    INT8U IfGunInsert;			//ǹ�Ƿ��Ѿ����복��0x00:�� 0x01:��				��������λ����
    INT8U IfGunInsertOld;
    INT16U OutVoltage;			//�����ѹ����ȷ��С�����һλ����������
    INT16U OutCurrent;			//�����������ȷ��С�����һλ����������
    INT8U GunTemp;			//ǹ���¶ȣ�ƫ����-50����������
    INT8U GunSOC;				//SOC:�������㣻����׮����
    INT8U BatHighTemp;			//���������¶�ƫ����-50 oC���������㣻����׮����
    INT16U	GunChargeTime;		//�ۼƳ��ʱ�䵥λ��min����������
    INT16U	RemainTime;			//ʣ��ʱ�䵥λ��min���������㡢����׮����
    INT32U	ChargeMeterData;	//�ѳ�������ȷ��С�������λ����������
    INT32U  LossMeterData;	//�ѳ����������ȷ��С�������λ����������,δ���ü������ʱ���ڳ�����
    INT32U 	ChargeMoney;	//�ѳ����þ�ȷ��С�������λ����������							���㹫ʽ�������+����ѣ�*���������
    INT16U	HardFaultData;	//Ӳ�����ϣ���������
    /*
	Bit λ��ʾ��0 �� 1 �ǣ�����λ����λ˳��
	Bit1����ͣ��ť�������ϣ�
	Bit2���޿�������ģ�飻
	Bit3��������¶ȹ��ߣ�
	Bit4���������׹��ϣ�
	Bit5����ֱ��ģ�� DC20 ͨ���жϣ�
	Bit6����Ե���ģ�� FC08 ͨ���жϣ�
	Bit7����ȱ�ͨ���жϣ�
	Bit8��������ͨ���жϣ�
	Bit9��RC10 ͨ���жϣ�
	Bit10�����ȵ��ٰ���ϣ�
	Bit11��ֱ���۶������ϣ�
	Bit12����ѹ�Ӵ������ϣ�
	Bit13���Ŵ򿪣�
	*/
	//��BMSͨ���౨����Ϣ===>����ֵ0x02ʱ˵���ı��ĳ�ʱ
	u8 Rec_BRM_OverTime;		//׮���� BMS �ͳ����ı�ʶ���ĳ�ʱ
	u8 Rec_BCP_OverTime;		//׮���յ�س��������ĳ�ʱ
	u8 Rec_BRO_OverTime;		//׮���� BMS ��ɳ��׼�����ĳ�ʱ
	u8 Rec_BCS_OverTime;		//׮���յ�س����״̬���ĳ�ʱ
	u8 Rec_BCL_OverTime;		//׮���յ�س��Ҫ���ĳ�ʱ
	u8 Rec_BST_OverTime;		//׮���� BMS ��ֹ��籨�ĳ�ʱ
	u8 Rec_BSD_OverTime;		//׮���� BMS ���ͳ�Ʊ��ĳ�ʱ
	u8 Rec_Other_OverTime;		//׮�����������ĳ�ʱ
	
	//����ֹͣ���ԭ��
	u8 PileStopReason;			//����ֹͣ���ԭ��
	u16 PileStopRrror1; 		//���� ��ֹ������ԭ��
	u8 PileStopRrror2;			//���� ��ֹ������ԭ��
	
	//���׮���ֵ
	u16 PileOutputVol;			//׮�����ѹֵ0.1 V/λ��0 V ƫ����
	u16 PileOutputCur;			//׮�������ֵ0.1 A/λ��-400 A ƫ����
	//׮�����������������Ϣ
	u8 StartChargeMode; 		//0x01:��ʾͨ��ˢ���������				0x02:�ݲ�֧��			0x03:vin���������
	u8 CardID[8];				//������(ˢ���ĺ�ɨ���·���)
	u8 CardCode[16];			//ˢ������,ͨ������
	u8 LogicCardNum[8];			//ƽ̨����---��ʾ����ʾ����
	u8 CardCheckOK;				//ˢ����Ȩ�ɹ�
	u32	UserBalance;			//�û���������λС��
	u8 CardCheckFailReason;		//����Ȩʧ��ԭ��,��ϸԭ������
	/*
	0x01 �˻�������
	0x02 �˻�����
	0x03 �˻�����
	0x04 �ÿ�����δ���˼�¼
	0x05 ׮ͣ��
	0x06 ���˻������ڴ�׮�ϳ��
	0x07 �������
	0x08 ��վ���ݲ���
	0x09 ϵͳ�� vin �벻����
	0x0A ��׮����δ���˼�¼
	0x0B ��׮��֧��ˢ��
	*/
	u8 RemotoStartFailReason;	//ɨ������ʧ��ԭ��
	/*
	0x00 ��
	0x01 �豸��Ų�ƥ��
	0x02 ǹ���ڳ��
	0x03 �豸����
	0x04 �豸����
	0x05 δ��ǹ
	׮���յ����������,��⵽δ��ǹ���� 0x33 ���Ļظ����ʧ�ܡ����� 60 �루���յ� 0x34 ʱ�俪ʼ���㣩�ڼ�⵽ǹ�������ӣ����� 0x33 �ɹ�����;��ʱ���� ���ߵ������쳣��׮�����䡢����
	�� 0x33 ����
	*/
	u8 RemotoStopFailReason;	//ɨ������ʧ��ԭ��
	/*
	0x00 ��
	0x01 �豸��Ų�ƥ��
	0x02 ǹδ���ڳ��״̬
	0x03 ����
	*/
	u8 StartTime[7];		//��ʼ���ʱ�䣬CP56Time2a ��ʽ
	u8 StopTime[7];			//�������ʱ�䣬CP56Time2a ��ʽ
	stChargeMoneyInfo ChargeMoneyInfo;
	INT8U RecordStartMode;		//0x01��app ���� 0x02��������         0x04�����߿�����         0x05: vin ���������
	INT8U StopChargeReason;		//ֹͣԭ��
}stChargeInfo;
extern stChargeInfo PileChargeInfo[CHARGEPILE_GUN_NUM];

//BMS�ĵ�ز����ṹ��
typedef struct
{
	//���ֽ׶�
	u16 MaxChargeVol;			//����������ܵ�ѹ��0.1V/λ     0Vƫ����
	u8 ProtocolVer[3];			//BMSͨ��Э��汾��ǰ�汾Ϊ V1.1����ʾΪ��byte3�� byte2��0001H��byte1��01H
	_BATTERY_TYPE  BatteryType;	//�������    eg:sBatteryPara.BatteryType = (_BATTERY_TYPE)LEAD_ACID_BATTERY
	u16 CarPowerCapacity;		//������ض��������λAh  0.1Ah/λ      0ƫ����
	u16 CarVoltageCapacity;		//������ض�ܵ�ѹ   ��λV  0.1V/λ      0ƫ����
	u32 BatteryManufacturer;	//�����������     ��׼ASCII��     ��ѡ��п���û�д�������
	u32 BatteryNumber;			//��������       ��ѡ��п���û�д�������
	u8  BatteryCreateYear;		//�������ʱ��  ��  ƫ����1985  ��ѡ��п���û�д�������
	u8  BatteryCreateMouth;		//�������ʱ��  ��  ƫ����0  ��ѡ��п���û�д�������
	u8  BatteryCreateDay;		//�������ʱ��  ��  ƫ����0  ��ѡ��п���û�д�������
	u32 BatteryChargeTimes;		//��س�����   ƫ����0  ��ѡ��п���û�д�������
	u8  BatteryOwner;			//��ز�Ȩ��ʶ   0������      1��������
	u8 * CarVINCode;			//����VIN��
	//��س�����
	u16 SingleBatteryMaxVol;	//����������������ѹ   0.01V/λ    0Vƫ����       ��Χ0-24V
	u16 SingleBatteryMinVol;	//����������������ѹ
	u16 MaxChargeCurrent;		//������������		 0.1A/λ		-400Aƫ����
	u16 BatteryAllPower;		//��ر��������	0.1kW?h/λ    0ƫ����    ��Χ0--1000
	u16 BatterySOC;				//��غɵ�״̬		0.1%/λ      0ƫ����     ��Χ0--1000
	u16 BatteryTempVol;			//��ص�ǰ��ѹ		0.1V/λ       0ƫ����
	u8  AllowMaxTemp;			//�������ĳ���¶�
	//��س��׶ε�ʵʱ����
	u16 BatteryNeedVol;			//��س�������ѹ     0.1V/λ       0ƫ����
	u16 BatteryNeedCur;			//��س��������� 	0.1A/λ		-400Aƫ����
	u8  BatteryChargeMode;		//���������ģʽ   0x01==>��ѹ���   	 0x02==>�������
	//���׶�
	u16 BatteryMeasureVol;		//������ѹ����ֵ	0.1V/λ       0ƫ����
	u16 BatteryMeasureCur;		//������������ֵ	0.1A/λ		-400Aƫ����
	u16 Battery_Max_VolAndNum;	//BMS ��ߵ��嶯�����ص�ѹ�����
	u16 MaxVol_Num;				//��߳���ѹ�����		1/λ     0ƫ����      ��Χ0--15
	u16 RemainTime;				//������ʣ��ʱ��	1min/λ     0ƫ����      ��Χ0--600
	//�¶���Ϣ
	u8  BatteryMaxTemp;			//����¶�	1��/λ     -50ƫ����     ��Χ-50---+200
	u8  MaxTempNum;				//����¶ȵı��     	1/λ    1ƫ����       ��Χ1--128
	u8  BatteryMinTemp;			//����¶�	1��/λ     -50ƫ����     ��Χ-50---+200
	u8  MinTempNum;				//����¶ȵı��	1/λ    1ƫ����       ��Χ1--128
	//������Ϣ�Լ�������
	u16	BatteryError1;			//BMS ��ֹ������ԭ��
	u8  BatteryError2;			//BMS ��ֹ������ԭ��
	u16 BatteryBSM;				//BSM���ĵ���Ϣ
	u8  BatteryVolErr;			//�����ѹ����    0x00==>����       0x01==>����  	 0x02==>����
	u8  BatterySOCErr;			//�ɵ籨��    0x00==>����       0x01==>����  	 0x02==>����
	u8  BatteryCurErr;			//��������    0x00==>����       0x01==>����  	 0x02==>������״̬
	u8  BatteryTempErr;			//�¶ȱ���    0x00==>����       0x01==>������  	 0x02==>������״̬
	u8  BatteryJueYuanErr;		//��Ե����    0x00==>����       0x01==>������  	 0x02==>������״̬
	u8  BatteryLinkErr;			//���ӱ���    0x00==>����       0x01==>������  	 0x02==>������״̬
	u8  BatteryChargeAllow;		//��籨��    0x00==>����       0x01==>��ֹ
	u8  LinkTempErr;			//���������¹���		0x00==>����       0x01==>����  	 0x02==>������״̬
	u8  BMSElementErr;			//BMSԪ�����¹���		0x00==>����       0x01==>����  	 0x02==>������״̬
	u8  GaoyaRelayErr;			//��ѹ�̵�������		0x00==>����       0x01==>����  	 0x02==>������״̬
	u8  CC2VolErr;				//����2��ѹ����		0x00==>����       0x01==>����  	 0x02==>������״̬
	u8  OtherErr;				//����������Ϣ			0x00==>����       0x01==>����  	 0x02==>������״̬
	//ֹͣ���ԭ��
	u8  ChargeStopReason;		//ֹͣԭ��
	u8  BatterySOCStop;			//�ﵽSOCֹͣ 		0x00==>δ�ﵽ       0x01==>�ﵽ  	 0x02==>������״̬
	u8  BatteryVolStop;			//�ﵽ�ܵ�ѹֹͣ	0x00==>δ�ﵽ       0x01==>�ﵽ  	 0x02==>������״̬
	u8  SingleVolStop;			//�����ѹֹͣ		0x00==>δ�ﵽ       0x01==>�ﵽ  	 0x02==>������״̬
	u8  ChargePileStop;			//��������ֹͣ	0x00==>����           0x01==>����ֹͣ    0x02==>������״̬
	//���Ľ��ճ�ʱ
	u8  RecognizeOverTime_1;	//BMS����0x00�ı�ʶ���ĳ�ʱ     0x00==>����           0x01==>��ʱ    0x02==>������״̬
	u8  RecognizeOverTime_2;	//BMS����0xAA�ı�ʶ���ĳ�ʱ
	u8  MaxOutputOverTime;		//BMS����ʱ��ͬ�����������������ĳ�ʱ
	u8  PileReadyOverTime;		//BMS���ճ���׼�����ĳ�ʱ
	u8  PileStateOverTime;		//BMS���ճ���״̬�ѱ��ĳ�ʱ
	u8  PileStopOverTime;		//BMS���ճ�����ֹ���ĳ�ʱ
	u8  PileStatisOverTime;		//BMS���ճ���ͳ����Ϣ��ʱ
	u8  OtherOverTime;			//BMS�����������ĳ�ʱ
	u8  DealBEMMsgFlag;			//�Ƿ���BEM�쳣����
	u8  MassageOverTime;		//BMS��⵽���ĳ�ʱ��־λ
	u8  LastBEMMsgCode;			//��һ�δ����ĵĴ�����
	u8  RecvErrorTimes;			//������ͬ�Ĵ�����Ĵ���   ����3����ΪͨѶ�쳣
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
