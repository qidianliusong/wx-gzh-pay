package com.liusong.micro.gzhPay.controller;

import com.liusong.micro.gzhPay.constant.Constant;
import com.liusong.micro.gzhPay.constant.GzhPayErr;
import com.liusong.micro.gzhPay.dto.*;
import com.liusong.micro.gzhPay.exception.GzhPayException;
import com.liusong.micro.gzhPay.manager.GzhPayMng;
import com.liusong.micro.gzhPay.result.BaseResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;

/**
 * 微信小程序支付接口
 *
 */
@RestController
@RequestMapping("/gzh/pay")
public class GzhPayController extends CommonBaseController {

	private static Logger logger = LoggerFactory.getLogger(GzhPayController.class);

	@Resource
	private GzhPayMng gzhPayMng;

	@ApiOperation(value = "微信公众号预支付", notes = "微信公众号预支付")
	@RequestMapping(value = "/prePay", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResult<PrePayDTO> prePay(@Validated @RequestBody MyOrderDTO wzbOrderDTO,
										BindingResult bindingResult) throws GzhPayException {
		BaseResult<PrePayDTO> baseResult = new BaseResult<>();
		if (!validateArgs(bindingResult, baseResult)) {
			return baseResult;
		}
		WxUnifiedorderResponseDTO responseDTO = gzhPayMng.prePay(wzbOrderDTO);

		String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
        String timeStamp = String.valueOf(new Date().getTime() / 1000);
        String paySign = null;
        try {
            paySign = gzhPayMng.getPaySign(wzbOrderDTO.getAppid(), Constant.MCH_SECRET,responseDTO.getPrepay_id(),nonceStr,timeStamp);
        } catch (NoSuchAlgorithmException e) {
            logger.error("生成支付签名失败");
            baseResult.setCode(GzhPayErr.md5_sign_err.getCode());
            baseResult.setMessage(GzhPayErr.md5_sign_err.getMessage());
            return baseResult;
        }
        PrePayDTO prePayDTO = new PrePayDTO();
		prePayDTO.setPaySign(paySign);
		prePayDTO.setErrCode(responseDTO.getErr_code());
		prePayDTO.setErrCodeDes(responseDTO.getErr_code_des());
		prePayDTO.setPrepayId(responseDTO.getPrepay_id());
		prePayDTO.setNonceStr(nonceStr);
		prePayDTO.setTimeStamp(timeStamp);
		prePayDTO.setAppid(responseDTO.getAppid());
		baseResult.setData(prePayDTO);
		return baseResult;
	}



	@ApiOperation(value = "查询支付结果", notes = "查询支付结果")
	@ApiImplicitParams({
			@ApiImplicitParam(value = "商户订单号", name = "outTradeNo", required = true, dataType = "String", paramType = "query") })
	@RequestMapping(value = "query", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResult<WxPayQueryResponseDTO> query(@RequestParam(required = true) String outTradeNo) throws GzhPayException {
		BaseResult<WxPayQueryResponseDTO> baseResult = new BaseResult<>();
		baseResult.setData(gzhPayMng.queryByOutTradeNo(outTradeNo));
		return baseResult;
	}

	@ApiOperation(value = "处理微信回调", notes = "处理微信回调")
	@RequestMapping(value = "callBack", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResult<Void> callBack(@RequestBody PayCallBackRequestDTO payCallBackRequestDTO){
		BaseResult<Void> baseResult = new BaseResult<>();
		if(!gzhPayMng.validateSign(payCallBackRequestDTO)){
			logger.warn("验证微信回调签名失败");
			baseResult.setMessage(GzhPayErr.validate_sign_err.getMessage());
			baseResult.setCode(GzhPayErr.validate_sign_err.getCode());
			return baseResult;
		}
		return baseResult;
	}

}
