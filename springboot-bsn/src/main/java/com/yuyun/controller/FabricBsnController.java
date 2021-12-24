package com.yuyun.controller;

import com.yuyun.dto.BsnHistoryDTO;
import com.yuyun.dto.CompanyDTO;
import com.yuyun.service.FabricBsnService;
import com.yuyun.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @author hyh
 */
@RestController
@RequestMapping("fabricBsn")
@Api(tags = "区块链")
public class FabricBsnController {

    @Autowired
    private FabricBsnService fabricBsnService;

    @GetMapping("history/{key}")
    @ApiOperation("历史记录")
    public Result<List<BsnHistoryDTO>> history(@PathVariable("key") String key) throws IOException {
        List<BsnHistoryDTO> bsnHistoryDTOList = fabricBsnService.getHistory(key);

        return new Result<List<BsnHistoryDTO>>().success(bsnHistoryDTOList);
    }

    @GetMapping("query/{key}")
    @ApiOperation("信息")
    public Result<CompanyDTO> get(@PathVariable("key") String key) {

        CompanyDTO data = fabricBsnService.query(key);

        return new Result<CompanyDTO>().success(data);
    }

    @DeleteMapping("{key}")
    @ApiOperation("删除")
    public Result<Object> delete(@PathVariable("key") String key) {

        return new Result<>().ok(fabricBsnService.delete(key));
    }
}
