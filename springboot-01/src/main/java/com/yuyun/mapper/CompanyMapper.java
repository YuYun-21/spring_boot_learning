package com.yuyun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuyun.dto.CompanyDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 企业信息表
 *
 * @author  yuyun
 * @since 1.0.0 2021-12-17
 */
@Mapper
public interface CompanyMapper extends BaseMapper<CompanyDTO> {

}