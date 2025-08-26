#ifndef  __DATADEAL_H
#define  __DATADEAL_H



INT8U  ReadReceiveCommandChar8(const INT8U* buffer);
INT16U ReadReceiveCommandInt16(const INT8U* buffer);
INT32U ReadReceiveCommandInt24(const INT8U* buffer);
INT32U ReadReceiveCommandInt32(const INT8U* buffer);
INT16U ReadReceiveCommandInt16_Low(const INT8U* buffer);
INT32U ReadReceiveCommandInt24_Low(const INT8U* buffer);
INT32U ReadReceiveCommandInt32_Low(const INT8U* buffer);
INT16U AddNewCommandByte(INT8U data, INT8U* buffer);
INT16U AddNewCommandInt16(INT16U data, INT8U* buffer);
INT16U AddNewCommandInt24(INT32U data, INT8U* buffer);
INT16U AddNewCommandInt32(INT32U data, INT8U* buffer);
INT16U AddNewCommandBuf(INT8U* mubiao, INT8U* buffer,INT16U len);
INT16U AddNewCommandByte_LOW(INT8U data, INT8U* buffer);
INT16U AddNewCommandInt16_LOW(INT16U data, INT8U* buffer);
INT16U AddNewCommandInt24_LOW(INT32U data, INT8U* buffer);
INT16U AddNewCommandInt32_LOW(INT32U data, INT8U* buffer);
unsigned int Modbus_Crc16(unsigned char *buff, unsigned int len);
u32 Dwin_4CharToINT32U(u8 *buf);
u8 Dwin_INT32UToBcd(u32 data,u8 len,u8 *buf);
INT8U bcd2todec(INT8U data)	;
u32 BCD_to_DEC(u32 Data);
INT16U dectobcd(INT16U data16);
INT16U CRC16_MODBUS_Calc(INT8U *txdat,INT16U len);
unsigned int AppCRC_crccheck32(unsigned char* pData, unsigned int uLen);
unsigned int AppCRC_Continue(unsigned int oldcrc,unsigned char* pData, unsigned int uLen);
unsigned int AppCommon_Str2Int(char *pBuf, unsigned char Length, unsigned char DecHexFlag);
INT16U ModbusCRC(INT8U * pData, INT16U len);
void ReverseVinCode(INT8U * InPut,INT8U *OutPut);



#endif
