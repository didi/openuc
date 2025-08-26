package com.openuc.cloud.sdk.sender;

import com.openuc.cloud.sdk.common.Constants;
import com.openuc.cloud.sdk.pojo.ElectricityPriceDTO;
import com.openuc.cloud.sdk.request.IssueRequest;
import com.openuc.cloud.sdk.util.BCD;
import com.openuc.cloud.sdk.util.SeqNoUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import com.openuc.cloud.sdk.pojo.ElectricityPeriodTimeDTO;

import java.math.BigDecimal;

import static com.openuc.cloud.sdk.common.Constants.CHARGE_BILLING_MODEL_SET_CMD;

/**
 * 发送计费模型设置命令
 * @author zhongxin
 */
public class FeeModelCmdSender extends ProtocolMessagerSender {

    @Override
    public void send(IssueRequest request, Channel channel) {
        // 准备好数据,这里创建个对象做示例
        ElectricityPriceDTO electricityPriceDTO = new ElectricityPriceDTO();
        // 通用协议计费模型
        if (electricityPriceDTO == null || electricityPriceDTO.getPeriodTimeDTOList() == null || electricityPriceDTO.getPeriodTimeDTOList().size() != 48) {
            throw new RuntimeException("通用协议 计费模型要求48时段, size:" + electricityPriceDTO.getPeriodTimeDTOList().size());
        }
        ByteBuf msgBodyBuf = Unpooled.buffer(90);
        fillChargeModel(msgBodyBuf, request.getPileCode(), electricityPriceDTO);
        send(build(msgBodyBuf, SeqNoUtil.getSeqNo(), 0, CHARGE_BILLING_MODEL_SET_CMD), request.getPileCode(), channel);
    }

    private void fillChargeModel(ByteBuf msgBodyBuf, String pileCodeSN, ElectricityPriceDTO electricityPriceInfo) {
        // 桩编码
        msgBodyBuf.writeBytes(BCD.strToBcd(pileCodeSN));
        // 计费模型编码
        msgBodyBuf.writeShortLE(1);
        // 尖费率
        msgBodyBuf.writeIntLE(electricityPriceInfo.getJianElectricityFee().multiply(new BigDecimal(100000)).intValue());
        // 尖服务费率
        msgBodyBuf.writeIntLE(electricityPriceInfo.getJianServiceFee().multiply(new BigDecimal(100000)).intValue());
        // 峰费率
        msgBodyBuf.writeIntLE(electricityPriceInfo.getFengElectricityFee().multiply(new BigDecimal(100000)).intValue());
        // 峰服务费率
        msgBodyBuf.writeIntLE(electricityPriceInfo.getFengServiceFee().multiply(new BigDecimal(100000)).intValue());
        // 平费率
        msgBodyBuf.writeIntLE(electricityPriceInfo.getPingElectricityFee().multiply(new BigDecimal(100000)).intValue());
        // 平服务费费率
        msgBodyBuf.writeIntLE(electricityPriceInfo.getPingServiceFee().multiply(new BigDecimal(100000)).intValue());
        // 谷费率
        msgBodyBuf.writeIntLE(electricityPriceInfo.getGuElectricityFee().multiply(new BigDecimal(100000)).intValue());
        // 谷服务费率
        msgBodyBuf.writeIntLE(electricityPriceInfo.getGuServiceFee().multiply(new BigDecimal(100000)).intValue());
        // 计损比例
        msgBodyBuf.writeByte(0);
        for (ElectricityPeriodTimeDTO feeDTO : electricityPriceInfo.getPeriodTimeDTOList()) {
            msgBodyBuf.writeByte(feeDTO.getFeeMark() - 1);
        }
    }
}
