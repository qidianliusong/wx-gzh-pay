package com.liusong.micro.gzhPay.manager.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.liusong.micro.gzhPay.constant.GzhPayErr;
import com.liusong.micro.gzhPay.dto.wx.IWxSignDTO;
import com.liusong.micro.gzhPay.exception.GzhPayException;
import com.liusong.micro.gzhPay.util.MD5Util;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.util.*;

import static com.liusong.micro.gzhPay.constant.Constant.MCH_SECRET;

abstract public class BaseMngImpl {

    private static Logger logger = LoggerFactory.getLogger(BaseMngImpl.class);

    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(Double.class, (JsonSerializer<Double>) (src, typeOfSrc, context) -> {
        if (src == src.longValue())
            return new JsonPrimitive(src.longValue());
        if (src == src.intValue())
            return new JsonPrimitive(src.intValue());
        return new JsonPrimitive(src);
    }).create();

    /**
     * 验证微信返回的数据签名
     */
    public boolean validateSign(IWxSignDTO result) {
        String json = GSON.toJson(result);
        Map<String, Object> fromJson = GSON.fromJson(json, new TypeToken<Map<String, Object>>() {
        }.getType());
        Map<String, Object> map = new TreeMap<>(String::compareTo);
        map.putAll(fromJson);
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<String, Object>> entrySet = map.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            if (entry == null || StringUtils.isBlank(entry.getKey()) || entry.getValue() == null || StringUtils.isBlank(entry.getValue().toString())) {
                continue;
            }
            if ("sign".equals(entry.getKey()))
                continue;
            if (entry.getValue() instanceof Double) {
                Double d = (Double) entry.getValue();
                if (d == d.longValue()) {
                    sb.append(entry.getKey()).append("=").append(d.longValue()).append("&");
                    continue;
                }
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        sb.append("key=").append(MCH_SECRET);
        String sign = null;
        try {
            sign = MD5Util.getMD5(sb.toString()).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            logger.error("验证微信返回预支付信息时计算md5出错", e);
            return false;
        }
        if (sign.equals(result.getSign()))
            return true;

        return false;
    }

    public String getPaySign(String appid, String mchSecret, String prepayId,String nonceStr,String timeStamp) throws NoSuchAlgorithmException {
        String format = "appId=%s&nonceStr=%s&package=prepay_id=%s&signType=MD5&timeStamp=%s&key=%s";
        String srcStr = String.format(format, appid, nonceStr, prepayId, timeStamp, mchSecret);
        return MD5Util.getMD5(srcStr).toUpperCase();
    }

    /**
     * 签名
     *
     * @throws GzhPayException
     */
    public String sign(IWxSignDTO result) throws GzhPayException {
        result.setNonce_str(UUID.randomUUID().toString().replaceAll("-", ""));
        String json = GSON.toJson(result);
        Map<String, Object> fromJson = GSON.fromJson(json, new TypeToken<Map<String, Object>>() {
        }.getType());
        Map<String, Object> map = new TreeMap<>(String::compareTo);
        map.putAll(fromJson);
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<String, Object>> entrySet = map.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            if (entry == null || StringUtils.isBlank(entry.getKey()) || entry.getValue() == null || StringUtils.isBlank(entry.getValue().toString())) {
                continue;
            }
            if ("sign".equals(entry.getKey()))
                continue;
            if (entry.getValue() instanceof Double) {
                Double d = (Double) entry.getValue();
                if (d == d.longValue()) {
                    sb.append(entry.getKey()).append("=").append(d.longValue()).append("&");
                    continue;
                }
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        sb.append("key=").append(MCH_SECRET);


        try {
            return MD5Util.getMD5(sb.toString()).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            logger.error("计算md5出错", e);
            throw new GzhPayException(GzhPayErr.md5_sign_err.getCode(), GzhPayErr.md5_sign_err.getMessage());
        }
    }

}
