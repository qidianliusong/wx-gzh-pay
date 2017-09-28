package com.liusong.micro.gzhPay.manager;

import com.liusong.micro.gzhPay.dto.WxPayQueryResponseDTO;
import com.liusong.micro.gzhPay.dto.WxUnifiedorderResponseDTO;
import com.liusong.micro.gzhPay.dto.MyOrderDTO;
import com.liusong.micro.gzhPay.dto.wx.IWxSignDTO;
import com.liusong.micro.gzhPay.exception.GzhPayException;

import java.security.NoSuchAlgorithmException;

public interface GzhPayMng {

	WxUnifiedorderResponseDTO prePay(MyOrderDTO wzbOrder) throws GzhPayException;

	boolean validateSign(IWxSignDTO dto);

	WxPayQueryResponseDTO queryByOutTradeNo(String outTradeNo) throws GzhPayException;

	String sign(IWxSignDTO result) throws GzhPayException;

	String getPaySign(String appid, String mchSecret, String prepayId,String nonceStr,String timeStamp) throws NoSuchAlgorithmException;

}
