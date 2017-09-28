package com.liusong.micro.gzhPay.result;

/**
 * 基础返回值
 *
 * @param <T>
 * @author liusong
 */
public class BaseResult<T> {

    private int code = SystemCode.success.getCode();
    private String message = SystemCode.success.getMessage();
    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
