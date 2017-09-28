package com.liusong.micro.gzhPay.manager.impl;

import com.liusong.micro.gzhPay.constant.Constant;
import com.liusong.micro.gzhPay.constant.GzhPayErr;
import com.liusong.micro.gzhPay.dto.CompanyPayQueryRpDTO;
import com.liusong.micro.gzhPay.dto.CompanyPayQueryRqDTO;
import com.liusong.micro.gzhPay.dto.CompanyPayRequestDTO;
import com.liusong.micro.gzhPay.dto.CompanyPayResponseDTO;
import com.liusong.micro.gzhPay.exception.GzhPayException;
import com.liusong.micro.gzhPay.feign.CompanyPayFeign;
import com.liusong.micro.gzhPay.manager.CompanyPayMng;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CompanyPayMngImpl extends BaseMngImpl implements CompanyPayMng {

    @Resource
    private CompanyPayFeign companyPayFeign;

    @Override
    public CompanyPayResponseDTO commpanyPay(CompanyPayRequestDTO commpanyPayRequestDTO) throws GzhPayException {
        commpanyPayRequestDTO.setMch_appid(Constant.APP_ID);
        commpanyPayRequestDTO.setMchid(Constant.MCH_ID);
        commpanyPayRequestDTO.setSign(sign(commpanyPayRequestDTO));
        CompanyPayResponseDTO companyPayResponseDTO = companyPayFeign.pay(commpanyPayRequestDTO);
        return companyPayResponseDTO;
    }

    @Override
    public CompanyPayQueryRpDTO query(CompanyPayQueryRqDTO companyPayQueryRqDTO) throws GzhPayException {

        if(StringUtils.isEmpty(companyPayQueryRqDTO.getMch_id())){
            companyPayQueryRqDTO.setMch_id(Constant.MCH_ID);
        }
        companyPayQueryRqDTO.setSign(sign(companyPayQueryRqDTO));
        CompanyPayQueryRpDTO companyPayQueryRpDTO = companyPayFeign.query(companyPayQueryRqDTO);

        if(!Constant.WX_RETURN_SUCCESS.equals(companyPayQueryRpDTO.getReturn_code())){
            throw new GzhPayException(GzhPayErr.company_pay_query_err.getCode(),companyPayQueryRpDTO.getReturn_msg());
        }
        return companyPayQueryRpDTO;
    }
}
