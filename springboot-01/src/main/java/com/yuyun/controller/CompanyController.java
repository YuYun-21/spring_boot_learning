package com.yuyun.controller;

import com.yuyun.dto.CompanyDTO;
import com.yuyun.service.CompanyService;
import com.yuyun.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 企业信息表
 *
 * @author
 * @since 1.0.0 2021-12-17
 */
@RestController
@RequestMapping("company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @GetMapping("list")
    public Result<List<CompanyDTO>> list() {
        List<CompanyDTO> companyList = companyService.list();

        return new Result<List<CompanyDTO>>().success(companyList);
    }

    @GetMapping("{id}")
    public Result<CompanyDTO> get(@PathVariable("id") Long id) {

        CompanyDTO data = companyService.getById(id);

        return new Result<CompanyDTO>().success(data);
    }

    @PostMapping
    public Result<Object> save(@RequestBody CompanyDTO dto) {

        return new Result<>().ok(companyService.save(dto));
    }

    @PutMapping
    public Result<Object> update(@RequestBody CompanyDTO dto) {

        return new Result<>().ok(companyService.updateById(dto));
    }

    @DeleteMapping
    public Result<Object> delete(@RequestBody Long[] ids) {

        return new Result<>().ok(companyService.removeById(ids));
    }

}