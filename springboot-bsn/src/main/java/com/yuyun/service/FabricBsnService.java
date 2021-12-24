package com.yuyun.service;

import com.yuyun.dto.BsnHistoryDTO;
import com.yuyun.dto.CompanyDTO;

import java.io.IOException;
import java.util.List;

/**
 * @author hyh
 */
public interface FabricBsnService {

    /**
     * 保存（添加or修改）
     *
     * @param key  键值
     * @param body 内容
     * @return 成功 true
     * @return Boolean
     */
    Boolean save(String key, String body);

    /**
     * 查询
     *
     * @param key 键值
     * @return 结果
     */
    CompanyDTO query(String key);

    /**
     * 历史查询
     *
     * @param key 键值
     * @return 解密后的结果
     * @throws IOException 异常
     */
    List<BsnHistoryDTO> getHistory(String key) throws IOException;

    /**
     * 删除
     *
     * @param key 键值
     * @return 成功 true
     * @return Boolean
     */
    Boolean delete(String key);
}
