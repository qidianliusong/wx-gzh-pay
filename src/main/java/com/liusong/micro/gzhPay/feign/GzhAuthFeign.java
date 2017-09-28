package com.liusong.micro.gzhPay.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 微信小程序支付接口
 */
@FeignClient(name = "gzh-auth", url = "https://api.weixin.qq.com/sns")
public interface GzhAuthFeign {

    @RequestMapping(value = "oauth2/access_token", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    String accessToken(@RequestParam(value = "appid", required = true) String appid, @RequestParam(value = "secret", required = true) String secret, @RequestParam(value = "code", required = true) String code, @RequestParam(value = "grant_type", required = true) String grantType);

}
