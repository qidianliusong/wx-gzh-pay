package com.liusong.micro.gzhPay.manager;

import com.liusong.micro.gzhPay.dto.CompanyPayQueryRpDTO;
import com.liusong.micro.gzhPay.dto.CompanyPayQueryRqDTO;
import com.liusong.micro.gzhPay.dto.CompanyPayRequestDTO;
import com.liusong.micro.gzhPay.dto.CompanyPayResponseDTO;
import com.liusong.micro.gzhPay.exception.GzhPayException;

public interface CompanyPayMng {

    CompanyPayResponseDTO commpanyPay(CompanyPayRequestDTO commpanyPayRequestDTO) throws GzhPayException;

    CompanyPayQueryRpDTO query(CompanyPayQueryRqDTO companyPayQueryRqDTO) throws GzhPayException;

}
