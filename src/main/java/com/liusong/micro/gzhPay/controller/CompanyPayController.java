package com.liusong.micro.gzhPay.controller;

import com.liusong.micro.gzhPay.dto.CompanyPayQueryRpDTO;
import com.liusong.micro.gzhPay.dto.CompanyPayQueryRqDTO;
import com.liusong.micro.gzhPay.dto.CompanyPayRequestDTO;
import com.liusong.micro.gzhPay.dto.CompanyPayResponseDTO;
import com.liusong.micro.gzhPay.exception.GzhPayException;
import com.liusong.micro.gzhPay.manager.CompanyPayMng;
import com.liusong.micro.gzhPay.result.BaseResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 微信企业支付接口
 */
@RestController
@RequestMapping("/gzh/company/pay")
public class CompanyPayController extends CommonBaseController {

    private static Logger logger = LoggerFactory.getLogger(CompanyPayController.class);

    @Resource
    private CompanyPayMng companyPayMng;

    @ApiOperation(value = "企业支付", notes = "企业支付")
    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResult<CompanyPayResponseDTO> commpanyPay(@RequestBody CompanyPayRequestDTO commpanyPayRequestDTO) throws GzhPayException {
        BaseResult<CompanyPayResponseDTO> baseResult = new BaseResult<>();
        baseResult.setData(companyPayMng.commpanyPay(commpanyPayRequestDTO));
        return baseResult;
    }

    @ApiOperation(value = "企业支付查询", notes = "企业支付查询")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "商户订单号", name = "partnerTradeNo", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(value = "微信appid", name = "appId", required = true, dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "query", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResult<CompanyPayQueryRpDTO> query(String partnerTradeNo, String appId) throws GzhPayException {
        BaseResult<CompanyPayQueryRpDTO> baseResult = new BaseResult<>();
        CompanyPayQueryRqDTO companyPayQueryRqDTO = new CompanyPayQueryRqDTO();
        companyPayQueryRqDTO.setAppid(appId);
        companyPayQueryRqDTO.setPartner_trade_no(partnerTradeNo);
        baseResult.setData(companyPayMng.query(companyPayQueryRqDTO));
        return baseResult;
    }

}
