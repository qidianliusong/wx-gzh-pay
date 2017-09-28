package com.liusong.micro.gzhPay.dto.wx;

public interface IWxSignDTO {

	void setNonce_str(String nonce_str);
	String getSign();
	
}
