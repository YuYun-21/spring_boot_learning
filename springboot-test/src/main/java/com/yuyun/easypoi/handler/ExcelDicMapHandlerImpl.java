package com.yuyun.easypoi.handler;


import cn.afterturn.easypoi.handler.inter.IExcelDictHandler;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 模拟使用,生产请用真实字典
 *
 * @author jueyue on 20-4-26.
 */
public class ExcelDicMapHandlerImpl implements IExcelDictHandler {

    public static void main(String[] args) {
        ExcelDicMapHandlerImpl excelDicMapHandler = new ExcelDicMapHandlerImpl();
        Map<String, String> map = excelDicMapHandler.getMap("level");
        Map<String, String> reversalMap = excelDicMapHandler.getReversalMap("level");

        System.out.println("JSON.toJSONString(map) = " + JSON.toJSONString(map));
        System.out.println("JSON.toJSONString(reversalMap) = " + JSON.toJSONString(reversalMap));
    }

    public Map<String, String> getMap(String dic) {

        Map<String, String> stringMap = new HashMap<>();
        switch (dic) {
            case "level":
                stringMap.put("0", "校长");
                stringMap.put("1", "学生");
                stringMap.put("2", "老师");
                return stringMap;
            case "dept_dic":
                stringMap.put("001", "小学");
                stringMap.put("002", "初中");
                stringMap.put("003", "高中");
                return stringMap;
            default:
                return stringMap;
        }
    }

    private Map<String, String> getReversalMap(String dic) {
        return getMap(dic).entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getValue,
                Map.Entry::getKey
        ));
    }

    /**
     * 返回字典所有值
     * key: dictKey
     *
     * @param dict 字典Key
     * @return
     */
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
