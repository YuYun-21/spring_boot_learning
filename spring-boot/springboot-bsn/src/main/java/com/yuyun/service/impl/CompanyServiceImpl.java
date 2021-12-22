package com.yuyun.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuyun.dto.CompanyDTO;
import com.yuyun.mapper.CompanyMapper;
import com.yuyun.service.CompanyService;
import com.yuyun.service.FabricBsnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 企业信息表
 *
 * @author
 * @since 1.0.0 2021-12-17
 */
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, CompanyDTO> implements CompanyService {

    @Autowired
    private FabricBsnService fabricBsnService;

    @Override
    public Boolean saveCompany(CompanyDTO companyDTO) {

        return this.save(companyDTO) && fabricBsnService.save("company_" + companyDTO.getId(), JSON.toJSONString(companyDTO));
    }

    @Override
    public Boolean updateCompany(CompanyDTO companyDTO) {

        return this.updateById(companyDTO) && fabricBsnService.save("company_" + companyDTO.getId(), JSON.toJSONString(companyDTO));
    }

    @Override
    public Boolean deleteCompanyById(Long id) {
        return this.removeById(id) && fabricBsnService.delete("company_" + id);
    }
}