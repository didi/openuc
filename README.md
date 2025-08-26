# OpenUC(开放充电通用协议,open UniCharge)
## 项目说明
本项目提供开放充电通用协议定义文档、提供桩侧的SDK包以及提供云平台协议解析SDK文档。
### 开放充电通用协议
开放充电通用协议（Open UniCharge，简称OpenUC）是一个规定了充电桩与充电运营平台之间数据交互的流程、格式和内容。  <br>
协议整体依据国网104充电桩规约，新增数据部分协议参照 GBT-27930 对充电桩充电过程中与充电运营平台的交互数据进行了补充，本协议适用于交、直流，交流在本协议中部分数据无需上送数据项在下面协议部分均有标注。

## 目录结构
### cloud
此目录下为云端处理SDK，具体目录结构如下：<br>
openuc-java-cloud-sdk/  <br>
├── pom.xml                           # Maven项目配置文件  <br>
└── src/                              # 源代码目录  <br>
&#160;&#160;&#160;├── main/                         # 主源代码  <br>
&#160;&#160;&#160;    │   └── java/                     # Java源代码  <br>
&#160;&#160;&#160;    │       └── com/openuc/cloud/sdk/ # SDK根包  <br>
&#160;&#160;&#160;    │           ├── common/           # 公共常量定义  <br>
&#160;&#160;&#160;    │           ├── decoder/          # 协议解码器  <br>
&#160;&#160;&#160;    │           ├── encoder/          # 协议编码器  <br>
&#160;&#160;&#160;    │           ├── message/          # 消息实体类  <br>
&#160;&#160;&#160;    │           ├── pojo/             # 数据传输对象  <br>
&#160;&#160;&#160;    │           ├── request/          # 请求对象  <br>
&#160;&#160;&#160;    │           ├── sender/           # 命令发送器  <br>
&#160;&#160;&#160;    │           └── util/             # 工具类  <br>
&#160;&#160;&#160;    └── test/                         # 测试代码  <br>
&#160;&#160;&#160;&#160;&#160;&#160;        └── java/                     # Java测试代码  <br>
        
#### 各模块详细说明
- 1. common - 公共模块 <br>
Constants.java: 项目中使用的公共常量定义<br>
- 2. decoder - 解码模块 <br>
协议解码器核心类和命令解码实现 <br>
包含20+个具体命令的解码处理类 <br>
- 3. encoder - 编码模块 <br>
ProtocolEncoder.java: 协议编码器实现 <br>
- 4. message - 消息模块 <br>
各类消息实体类，包括: <br>
充电相关命令消息（开始充电、停止充电等） <br>
设备状态消息（心跳、握手等） <br>
数据查询消息（充电记录、BMS信息等）<br>
控制命令消息（远程重启、OTA升级等）<br>
- 5. pojo - 数据对象模块<br>
ElectricityPeriodTimeDTO.java: 电价时段数据传输对象<br>
ElectricityPriceDTO.java: 电价数据传输对象<br>
- 6. request - 请求模块<br>
各类请求对象，用于向上游系统发送请求<br>
- 7. sender - 发送器模块<br>
各类命令发送器，用于向设备发送指令<br>
包含充电控制、设备管理、状态查询等命令发送器<br>
- 8. util - 工具模块<br>
提供各类工具方法:<br>
AsciiUtil.java: ASCII编码工具<br>
BCD.java & BCDUtil.java: BCD码处理工具<br>
CRC16Util.java: CRC16校验工具<br>
HexStrUtil.java: 十六进制字符串处理工具<br>
时间、序列号、数值处理等工具类<br>
- 9. test - 测试模块<br>
HexStrUtilTest.java: HexStrUtil工具类的单元测试<br>
### equipment
开放充电通用协议设备侧SDK文档集合，SDK是基于C语言开发的充电桩设备通信代码，主要用于实现充电桩与云平台之间的数据通信，包括协议处理、网络通信、数据编解码等功能。<br>
具体目录结构如下：<br>
document/<br>
&#160;&#160;&#160;├── 开放充电通用协议文档-V1.0.pdf # 协议定义文档 <br>
&#160;&#160;&#160;├── 通用协议SDK使用说明.pdf # SDK使用说明文档 <br>
sdk/ <br>
&#160;&#160;&#160;├── DataDeal.c           # 数据处理实现文件 <br>
&#160;&#160;&#160;├── DataDeal.h           # 数据处理头文件 <br>
&#160;&#160;&#160;├── Net_Deal.c           # 网络通信处理实现文件 <br>
&#160;&#160;&#160;└── Net_Deal.h           # 网络通信处理头文件 <br>

其中sdk目录下各模块详细说明：<br>
#### 1. DataDeal 模块
- 主要功能
  - 数据编解码处理
  - 各种数据类型读写操作
  - CRC校验算法实现
  - BCD码转换处理
  - 核心功能函数
- 数据读取函数:
  - ReadReceiveCommandChar8: 读取单字节数据
  - ReadReceiveCommandInt16/Int24/Int32: 读取多字节整型数据(大小端两种格式)
- 数据写入函数:
  - AddNewCommandByte/Int16/Int24/Int32: 写入各种类型数据(大小端两种格式)
  - AddNewCommandBuf: 写入缓冲区数据
- 校验算法:
  - Modbus_Crc16: Modbus CRC16校验
  - CRC16_MODBUS_Calc: MODBUS CRC16计算
  - ModbusCRC: 协议使用的CRC校验
  - AppCRC_crccheck32: CRC32校验
- 编码转换:
  - bcd2todec: BCD转十进制
  - dectobcd: 十进制转BCD
  - BCD_to_DEC: BCD转DEC转换
#### 2. Net_Deal 模块
- 主要功能
  - 网络通信管理
  - 充电桩与平台通信协议实现
  - 充电流程控制
  - 数据上报与命令处理
- 核心数据结构
  - ParamSet: 桩体参数设置结构体
  - stNetConFlag: 网络连接相关结构体
  - stRateInfo: 桩计费模型信息
  - stChargeInfo: 充电信息结构体
  - stBatteryPara: 电池信息结构体
  - stPileQRCodeInfo: 二维码信息结构体
- 核心功能函数
  - 网络通信函数
     - NetSendData: 网络数据发送
     - CreateConnectForGun: 创建网络连接
     - DeleteConnectForGun: 删除网络连接
     - NetDeal_Recv: 网络数据接收处理
     - NetDeal_SendRecv: 网络数据收发处理
  - 数据发送函数
     - PileSendServeiceStart: 充电桩登录平台认证
     - PileSendHeart: 发送心跳包
     - PileSendRealTimeInfo: 发送实时信息
     - PileSendRecordData: 发送订单数据
     - PileSendChargeStep1/2/3Info: 发送充电各阶段信息
  - 数据接收处理函数
     - Rcv_DataAnaly: 平台数据解析处理
     - Receive_RataInfo: 计费模型接收
     - Receive_PileStartChargeInfo: 启动充电命令处理
     - Receive_RemotoStartChargeInfo: 远程启动充电处理
  - 状态管理函数
     - Net_init_send_data: 网络初始化处理
     - Net_StateHardData_Send: 心跳状态发送
     - CheckTimeOut: 超时重发处理
     - NetTimeOutSet/Clear: 超时管理
