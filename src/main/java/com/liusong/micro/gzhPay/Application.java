package com.liusong.micro.gzhPay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * 微信小程序支付启动类
 *
 */
@SpringBootApplication(scanBasePackages={"com.liusong.micro.gzhPay"})
@EnableEurekaClient
@EnableFeignClients
public class Application 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(Application.class, args);
    }
}
