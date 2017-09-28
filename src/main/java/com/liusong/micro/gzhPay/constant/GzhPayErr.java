package com.liusong.micro.gzhPay.constant;
/**
 * 微信小程序支付报错集锦
 *
 */
public enum GzhPayErr {

	jscode2session_err(8001,"调用微信支付:code换取session_key出错"),
	
	unifiedorder_err(8002,"调用微信支付:生成预支付交易单出错"),
	
	md5_sign_err(8004,"MD5签名出错"),
	validate_sign_err(8005,"验证签名失败"),
	wx_call_back_err(8006,"微信回调失败"),
	wx_access_token_err(8007,"调用微信授权接口出错"),
    order_query_err(8008,"调用微信支付:生成预支付交易单出错"),
	company_pay_query_err(8009,"微信企业支付查询接口返回失败");
	
	private int code;
	private String message;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	private GzhPayErr(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
}
