package com.liusong.micro.gzhPay.config.feign;

import com.liusong.micro.gzhPay.config.restTemplate.TextPlainJackson2XmlHttpMessageConverter;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WxFeignClientConfig {

    @Bean
    public HttpMessageConverters getHttpMessageConverters() {
        return new HttpMessageConverters(new TextPlainJackson2XmlHttpMessageConverter());
    }

}
