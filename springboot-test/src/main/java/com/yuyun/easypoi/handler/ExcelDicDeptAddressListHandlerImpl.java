package com.yuyun.easypoi.handler;


import cn.afterturn.easypoi.handler.inter.IExcelDictHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模拟使用,生产请用真实字典
 *
 * @author jueyue on 20-4-26.
 */
public class ExcelDicDeptAddressListHandlerImpl implements IExcelDictHandler {

    /**
     * 返回字典所有值
     * key: dictKey
     *
     * @param dict 字典Key
     * @return
     */
    @Override
    public List<Map> getList(String dict) {
        if ("dept_dic".equals(dict)) {
            List<Map> list = new ArrayList<>();
            Map<String, String> dictMap = new HashMap<>(2);
            dictMap.put("dictKey", "001");
            dictMap.put("dictValue", "小学");
            list.add(dictMap);
            dictMap = new HashMap<>(2);
            dictMap.put("dictKey", "002");
            dictMap.put("dictValue", "初中");
            list.add(dictMap);
            dictMap = new HashMap<>(2);
            dictMap.put("dictKey", "003");
            dictMap.put("dictValue", "高中");
            list.add(dictMap);
            return list;
        }

        if ("level".equals(dict)) {
            List<Map> list = new ArrayList<>();
            Map<String, String> dictMap = new HashMap<>(2);
            dictMap.put("dictKey", "0");
            dictMap.put("dictValue", "校长");
            list.add(dictMap);
            dictMap = new HashMap<>(2);
            dictMap.put("dictKey", "1");
            dictMap.put("dictValue", "老师");
            list.add(dictMap);
            dictMap = new HashMap<>(2);
            dictMap.put("dictKey", "2");
            dictMap.put("dictValue", "学生");
            list.add(dictMap);
            return list;
        }

        return new ArrayList<>(0);
    }

    @Override
    public String toName(String dict, Object obj, String name, Object value) {
        if ("dept_dic".equals(dict)) {
            switch (value.toString()) {
                case "001":
                    return "小学";
                case "002":
                    return "初中";
                case "003":
                    return "高中";
            }
        }
        if ("level".equals(dict)) {
            int level = Integer.parseInt(value.toString());
            switch (level) {
                case 1:
                    return "老师";
                case 0:
                    return "校长";
                case 2:
                    return "学生";
            }
        }
        return null;
    }

    @Override
    public String toValue(String dict, Object obj, String name, Object value) {
        if ("dept_dic".equals(dict)) {
            switch (value.toString()) {
                case "小学":
                    return "001";
                case "初中":
                    return "002";
                case "高中":
                    return "003";
            }
        }
        if ("level".equals(dict)) {
            switch (value.toString()) {
                case "校长":
                    return "0";
                case "老师":
                    return "1";
                case "学生":
                    return "2";
            }
        }
        return null;
    }
}
