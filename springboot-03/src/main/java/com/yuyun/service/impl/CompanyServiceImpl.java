package com.yuyun.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuyun.mapper.CompanyMapper;
import com.yuyun.dto.CompanyDTO;
import com.yuyun.service.CompanyService;
import org.springframework.stereotype.Service;

/**
 * 企业信息表
 *
 * @author
 * @since 1.0.0 2021-12-17
 */
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, CompanyDTO> implements CompanyService {

}