package com.openuc.cloud.sdk.sender;

import com.openuc.cloud.sdk.common.Constants;
import com.openuc.cloud.sdk.pojo.ElectricityPeriodTimeDTO;
import com.openuc.cloud.sdk.pojo.ElectricityPriceDTO;
import com.openuc.cloud.sdk.request.IssueRequest;
import com.openuc.cloud.sdk.util.BCD;
import com.openuc.cloud.sdk.util.SeqNoUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import java.math.BigDecimal;

import static com.openuc.cloud.sdk.common.Constants.CHARGE_BILLING_MODEL_ACK_CMD;

/**
 * 发送查询充电模型应答
 * @author zhongxin
 */
public class QueryChargeModelAckSender extends ProtocolMessagerSender {

    @Override
    public void send(IssueRequest request, Channel channel) {
        ByteBuf respBodyBuf = Unpooled.buffer(90);
        ElectricityPriceDTO electricityPriceDTO = new ElectricityPriceDTO(); // 这里应该根据pilcode从数据库或其他服务获取实际的电价信息
        fillChargeModel(respBodyBuf, request.getPileCode(), electricityPriceDTO);

        // 发送查询充电模型应答
        send(build(respBodyBuf, SeqNoUtil.getSeqNo(), 0, CHARGE_BILLING_MODEL_ACK_CMD), request.getPileCode(), channel);
    }

    private void fillChargeModel(ByteBuf msgBodyBuf, String pileCode, ElectricityPriceDTO electricityPriceInfo) {
        // 桩编码
        msgBodyBuf.writeBytes(BCD.strToBcd(pileCode));
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
