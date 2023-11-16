package com.yuyun.easypoi.handler;

import com.yuyun.easypoi.entity.DicDTO;
import com.yuyun.easypoi.service.SysDicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author hyh
 * @since 2023-11-09
 */
@Component
public class ExcelDictHandler extends AbstractExcelDictHandler {

    @Autowired
    private SysDicService sysDicService;

    @Override
    public Map<String, String> getMap(String dic) {
        switch (dic) {
            case "tag_dic":
                return getTagMap();
            case "dept_dic":
                return getDeptMap();
            default:
                return new HashMap<>(0);
        }
    }

    private Map<String, String> getTagMap() {
        List<DicDTO> staffTagDTOList = sysDicService.listTagDic();

        return staffTagDTOList.stream().collect(Collectors.toMap(
                dto -> dto.getId().toString(),
                DicDTO::getName
        ));
    }

    private Map<String, String> getDeptMap() {
        List<DicDTO> deptDTOList = sysDicService.listDeptDic();
        return deptDTOList.stream().collect(Collectors.toMap(
                dto -> dto.getId().toString(),
                DicDTO::getName
        ));
    }

}
