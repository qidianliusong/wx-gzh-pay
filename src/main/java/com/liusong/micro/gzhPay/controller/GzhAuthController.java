package com.liusong.micro.gzhPay.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.liusong.micro.gzhPay.constant.Constant;
import com.liusong.micro.gzhPay.constant.GzhPayErr;
import com.liusong.micro.gzhPay.dto.WxAuthDTO;
import com.liusong.micro.gzhPay.feign.GzhAuthFeign;
import com.liusong.micro.gzhPay.result.BaseResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

/**
 * 公众号验证接口
 */
@RestController
@RequestMapping("/gzh/auth")
public class GzhAuthController extends CommonBaseController{

    private static Logger logger = LoggerFactory.getLogger(GzhAuthController.class);

    private static String GRANT_TYPE = "authorization_code";

    private static Gson gson = new GsonBuilder().create();

    @Resource
    private GzhAuthFeign gzhAuthFeign;

    @ApiOperation(value = "获取openid", notes = "获取openid")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "换取access_token的票据", name = "code", required = true, dataType = "String", paramType = "query") })
    @RequestMapping(value = "getOpenId", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResult<WxAuthDTO> getOpenId(String code){
        BaseResult<WxAuthDTO> baseResult = new BaseResult<>();
        String accessToken = gzhAuthFeign.accessToken(Constant.APP_ID,Constant.APP_SECRET,code,GRANT_TYPE);
        try {
            accessToken = new String(accessToken.getBytes("iso8859-1"),"utf8");
        } catch (UnsupportedEncodingException e) {
            logger.error("转换字符串编码出错");
        }

        WxAuthDTO wxAuthDTO = gson.fromJson(accessToken,WxAuthDTO.class);

        if(wxAuthDTO.getErrcode() != null){
            baseResult.setCode(GzhPayErr.wx_access_token_err.getCode());
            baseResult.setMessage(GzhPayErr.wx_access_token_err.getMessage()+": "+wxAuthDTO.getErrmsg());
            return baseResult;
        }
        if(wxAuthDTO.getOpenid() == null){
            baseResult.setCode(GzhPayErr.wx_access_token_err.getCode());
            baseResult.setMessage(GzhPayErr.wx_access_token_err.getMessage()+":微信返回openid为空");
            return baseResult;
        }
        baseResult.setData(wxAuthDTO);
        return baseResult;
    }


}
