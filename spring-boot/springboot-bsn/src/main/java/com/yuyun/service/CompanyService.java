package com.yuyun.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuyun.dto.CompanyDTO;

/**
 * 企业信息表
 *
 * @author
 * @since 1.0.0 2021-12-17
 */
public interface CompanyService extends IService<CompanyDTO> {

    /**
     * 保存企业信息
     * @param companyDTO
     * @return
     */
    Boolean saveCompany(CompanyDTO companyDTO);

    /**
     * 修改企业信息
     * @param companyDTO
     * @return
     */
    Boolean updateCompany(CompanyDTO companyDTO);

    /**
     * 根据id删除信息
     * @param id
     * @return
     */
    Boolean deleteCompanyById(Long id);

}