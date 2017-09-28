package com.liusong.micro.gzhPay.feign;

import com.liusong.micro.gzhPay.config.feign.CompanyPayFeignConfig;
import com.liusong.micro.gzhPay.config.feign.WxFeignClientConfig;
import com.liusong.micro.gzhPay.dto.CompanyPayQueryRpDTO;
import com.liusong.micro.gzhPay.dto.CompanyPayQueryRqDTO;
import com.liusong.micro.gzhPay.dto.CompanyPayRequestDTO;
import com.liusong.micro.gzhPay.dto.CompanyPayResponseDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 企业付款接口
 */
@FeignClient(name = "company-pay", url = "https://api.mch.weixin.qq.com/mmpaymkttransfers", configuration = {CompanyPayFeignConfig.class, WxFeignClientConfig.class})
public interface CompanyPayFeign {

    @RequestMapping(value = "promotion/transfers", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE)
    CompanyPayResponseDTO pay(CompanyPayRequestDTO commpanyPayRequestDTO);
    @RequestMapping(value = "gettransferinfo",method = RequestMethod.POST,consumes = MediaType.APPLICATION_XML_VALUE)
    CompanyPayQueryRpDTO query(CompanyPayQueryRqDTO companyPayQueryRqDTO);

}
