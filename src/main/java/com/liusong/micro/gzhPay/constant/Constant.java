package com.liusong.micro.gzhPay.constant;

import com.liusong.micro.gzhPay.util.ConfigUtil;

/**
 * 常量
 */
public class Constant {

    public static final String GRANT_TYPE = "authorization_code";
    public static final String APP_ID = ConfigUtil.getProperty("wx.app.id");
    public static final String MCH_SECRET = ConfigUtil.getProperty("wx.mch.secret");
    public static final String APP_SECRET = ConfigUtil.getProperty("wx.app.secret");
    public static final String PC_DEVICE_INFO = "WEB";

    public static final String TRADE_TYPE = "JSAPI";

    public static final String MCH_ID = ConfigUtil.getProperty("wx.mch.id");

    public static final String WX_RETURN_SUCCESS = "SUCCESS";

    public static final String WX_RETURN_FAIL = "FAIL";
}
