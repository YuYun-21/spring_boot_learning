package com.yuyun.easypoi.service;

import com.yuyun.easypoi.entity.DicDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 标签
 *
 * @author hyh
 * @since 2023-11-16
 */
@Component
public class SysDicService {

    /**
     * 获取部门列表
     *
     * @return
     */
    public List<DicDTO> listDeptDic() {
        List<DicDTO> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            DicDTO dicDTO = new DicDTO();
            dicDTO.setId(100000L + i);
            dicDTO.setName("人力资源部" + i);
            list.add(dicDTO);
        }

        return list;
    }

    /**
     * 获取标签列表
     *
     * @return
     */
    public List<DicDTO> listTagDic() {
        List<DicDTO> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            DicDTO dicDTO = new DicDTO();
            dicDTO.setId(200000L + i);
            dicDTO.setName("组长" + i);
            list.add(dicDTO);
        }

        return list;
    }
}
