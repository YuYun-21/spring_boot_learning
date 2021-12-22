package com.yuyun.controller;

import com.yuyun.dto.CompanyDTO;
import com.yuyun.service.CompanyService;
import com.yuyun.utils.Result;
import io.swagger.annotations.*;
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
@Api(tags = "企业信息")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @GetMapping("getList")
    @ApiOperation("根据条件获取数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "name", value = "名称", paramType = "query", required = true, dataType = "String")
    })
    public Result<List<CompanyDTO>> getList(@ApiParam(name = "address", value = "地址", required = true)  String address) {
        List<CompanyDTO> companyList = companyService.list();

        return new Result<List<CompanyDTO>>().success(companyList);
    }

    @GetMapping("list")
    @ApiOperation("列表")
    public Result<List<CompanyDTO>> list() {
        List<CompanyDTO> companyList = companyService.list();

        return new Result<List<CompanyDTO>>().success(companyList);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    public Result<CompanyDTO> get(@PathVariable("id") Long id) {

        CompanyDTO data = companyService.getById(id);

        return new Result<CompanyDTO>().success(data);
    }

    @PostMapping
    @ApiOperation("保存")
    public Result<Object> save(@RequestBody CompanyDTO dto) {

        return new Result<>().ok(companyService.saveCompany(dto));
    }

    @PutMapping
    @ApiOperation("修改")
    public Result<Object> update(@RequestBody CompanyDTO dto) {

        return new Result<>().ok(companyService.updateCompany(dto));
    }

    @DeleteMapping("{id}")
    @ApiOperation("删除")
    public Result<Object> delete(@PathVariable("id") Long id) {

        return new Result<>().ok(companyService.deleteCompanyById(id));
    }

}