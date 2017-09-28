package com.liusong.micro.gzhPay.result;

/**
 * 基本code枚举
 *
 * @author liusong
 */
public enum SystemCode {

    success(0, "成功"),
    systemErr(500, "系统错误"),
    validateFail(401, "验证不通过"),

    illegal_paramter(406, "请求参数有误");

    private int code;
    private String message;

    private SystemCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

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


}
