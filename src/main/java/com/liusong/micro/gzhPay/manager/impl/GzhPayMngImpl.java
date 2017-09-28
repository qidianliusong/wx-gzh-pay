package com.liusong.micro.gzhPay.manager.impl;

import com.liusong.micro.gzhPay.constant.GzhPayErr;
import com.liusong.micro.gzhPay.dto.*;
import com.liusong.micro.gzhPay.exception.GzhPayException;
import com.liusong.micro.gzhPay.feign.GzhPayFeign;
import com.liusong.micro.gzhPay.manager.GzhPayMng;
import com.liusong.micro.gzhPay.constant.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.UUID;

@Service
public class GzhPayMngImpl extends BaseMngImpl implements GzhPayMng {

    private static Logger logger = LoggerFactory.getLogger(BaseMngImpl.class);

    private static final String DATE_FORMAT = "yyyyMMddHHmmss";
    /**
     * 订单过期时间10分钟
     */
    private static final Integer ORDER_EXPIRE_TIME = 600;

    private static final String DATE_FORMAT_FOR_RANDOM = "yyyyMMddHHmmssSSS";
    private static final int RANDOM_LEN = 10;

    private static final String WX_CODE_SUCCESS = "SUCCESS";

    @Resource
    private GzhPayFeign gzhPayFeign;

    public WxUnifiedorderResponseDTO prePay(MyOrderDTO wzbOrder) throws GzhPayException {
        WxUnifiedorderRequestDTO wxUnifiedorderDTO = initWxUnifiedorderDTO(wzbOrder);

        WxUnifiedorderResponseDTO result = gzhPayFeign.unifiedorder(wxUnifiedorderDTO);

        if (result == null)
            throw new GzhPayException(GzhPayErr.unifiedorder_err.getCode(), "调用unifiedorder返回值为空");

        if (!WX_CODE_SUCCESS.equals(result.getReturn_code())) {
            throw new GzhPayException(GzhPayErr.unifiedorder_err.getCode(),
                    "调用unifiedorder出错,错误信息:" + result.getReturn_msg());
        }

        if (!WX_CODE_SUCCESS.equals(result.getResult_code())) {
            return result;
        }

        if (!validateSign(result)) {
            throw new GzhPayException(GzhPayErr.validate_sign_err.getCode(), GzhPayErr.validate_sign_err.getMessage());
        }

        return result;
    }

    private WxUnifiedorderRequestDTO initWxUnifiedorderDTO(MyOrderDTO wzbOrder) throws GzhPayException {
        WxUnifiedorderRequestDTO wxUnifiedorderDTO = new WxUnifiedorderRequestDTO();
        String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
        wxUnifiedorderDTO.setAppid(Constant.APP_ID);
        wxUnifiedorderDTO.setAttach(wzbOrder.getAttach());
        wxUnifiedorderDTO.setBody(wzbOrder.getBody());
        wxUnifiedorderDTO.setDetail(wzbOrder.getDetail());
        wxUnifiedorderDTO.setDevice_info(Constant.PC_DEVICE_INFO);
        wxUnifiedorderDTO.setNonce_str(nonceStr);
        wxUnifiedorderDTO.setNotify_url(wzbOrder.getNotifyUrl());
        wxUnifiedorderDTO.setTrade_type(Constant.TRADE_TYPE);
        wxUnifiedorderDTO.setOut_trade_no(wzbOrder.getOutTradeNo());
        wxUnifiedorderDTO.setTotal_fee(wzbOrder.getTotalFee());
        wxUnifiedorderDTO.setSpbill_create_ip(wzbOrder.getSpbillCreateIp());
        wxUnifiedorderDTO.setMch_id(Constant.MCH_ID);
        wxUnifiedorderDTO.setOpenid(wzbOrder.getOpenid());

        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);

        wxUnifiedorderDTO.setTime_start(format.format(wzbOrder.getCreateTime()));
        wxUnifiedorderDTO.setTime_expire(format.format(wzbOrder.getExpireTime()));

        wxUnifiedorderDTO.setSign(this.sign(wxUnifiedorderDTO));

        return wxUnifiedorderDTO;
    }




    @Override
    public WxPayQueryResponseDTO queryByOutTradeNo(String outTradeNo) throws GzhPayException {
        WxPayQueryRequestDTO wxPayQueryRequestDTO = new WxPayQueryRequestDTO();
        wxPayQueryRequestDTO.setAppid(Constant.APP_ID);
        wxPayQueryRequestDTO.setMch_id(Constant.MCH_ID);
        wxPayQueryRequestDTO.setOut_trade_no(outTradeNo);
        wxPayQueryRequestDTO.setSign(sign(wxPayQueryRequestDTO));
        WxPayQueryResponseDTO wxPayQueryResponseDTO = gzhPayFeign.orderQuery(wxPayQueryRequestDTO);
        if (!WX_CODE_SUCCESS.equals(wxPayQueryResponseDTO.getReturn_code())) {
            logger.warn("调用gzhPayFeign.orderQuery失败，失败原因：" + wxPayQueryResponseDTO.getReturn_msg());
            throw new GzhPayException(GzhPayErr.order_query_err.getCode(),
                    "调用gzhPayFeign.orderQuery失败，失败原因：" + wxPayQueryResponseDTO.getReturn_msg());
        }
        if(!WX_CODE_SUCCESS.equals(wxPayQueryResponseDTO.getResult_code())){
            return wxPayQueryResponseDTO;
        }

        if(!validateSign(wxPayQueryResponseDTO)){
            throw new GzhPayException(GzhPayErr.validate_sign_err.getCode(), GzhPayErr.validate_sign_err.getMessage());
        }

        return wxPayQueryResponseDTO;
    }

}
