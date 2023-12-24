package com.yuyun.easypoi.handler;

import cn.afterturn.easypoi.handler.inter.IExcelDictHandler;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 抽象Handler
 *
 * @author hyh
 */
public abstract class AbstractExcelDictHandler implements IExcelDictHandler {

    /**
     * 获取字典map
     * 用户将值翻译成用于下拉列表的名称
     * 使用的时候重写该方法即可
     *
     * @param dic
     * @return
     */
    public abstract Map<String, String> getMap(String dic);

    /**
     * 将map的key和value互换
     * 用于将下拉选择的名称翻译成值
     *
     * @param dic
     * @return
     */
    private Map<String, String> getReversalMap(String dic) {
        return getMap(dic).entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getValue,
                Map.Entry::getKey
        ));
    }

    @Override
    public List<Map> getList(String dict) {

        if (StringUtils.isNotBlank(dict)) {
            Map<String, String> dicMap = getMap(dict);
            if (Objects.isNull(dicMap) || dicMap.isEmpty()) {
                throw new RuntimeException("必须指定字典值！");
            }
            return dicMap.entrySet().stream().map(entry -> {
                Map<String, String> stringMap = new HashMap<>(2);
                stringMap.put("dictKey", entry.getKey());
                stringMap.put("dictValue", entry.getValue());
                return stringMap;
            }).collect(Collectors.toList());
        }

        return new ArrayList<>(0);
    }

    @Override
    public String toName(String dict, Object obj, String name, Object value) {
        if (Objects.nonNull(value)) {
            return getMap(dict).get(value.toString());
        }
        return null;
    }

    @Override
    public String toValue(String dict, Object obj, String name, Object value) {
        if (Objects.nonNull(value)) {
            return getReversalMap(dict).get(value.toString());
        }
        return null;
    }
}
