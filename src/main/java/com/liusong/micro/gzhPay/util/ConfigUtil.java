package com.liusong.micro.gzhPay.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 配置文件获取工具类
 *
 */
@Component
public class ConfigUtil {
	
	private static Logger logger = LoggerFactory.getLogger(ConfigUtil.class);

	@Resource
	private Environment env;
	
	private static ConfigUtil configUtil;
	
	@PostConstruct
	private void init(){
		configUtil = this;
		logger.info("configUtil初始化成功");
	}
	
	public static String getProperty(String key){
		return configUtil.env.getProperty(key);
	}
}
