package com.liusong.micro.gzhPay.feign;

import com.liusong.micro.gzhPay.config.feign.WxFeignClientConfig;
import com.liusong.micro.gzhPay.dto.WxUnifiedorderResponseDTO;
import com.liusong.micro.gzhPay.dto.WxUnifiedorderRequestDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.liusong.micro.gzhPay.dto.WxPayQueryRequestDTO;
import com.liusong.micro.gzhPay.dto.WxPayQueryResponseDTO;

/**
 * 微信小程序支付接口
 *
 */
@FeignClient(name = "gzh-pay", url = "https://api.mch.weixin.qq.com/pay",configuration = WxFeignClientConfig.class)
public interface GzhPayFeign {

	@RequestMapping(value = "unifiedorder", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    WxUnifiedorderResponseDTO unifiedorder(WxUnifiedorderRequestDTO wxUnifiedorderDTO);

	@RequestMapping(value = "orderquery", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	WxPayQueryResponseDTO orderQuery(WxPayQueryRequestDTO requestDTO);

}
