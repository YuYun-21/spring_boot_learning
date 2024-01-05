package com.yuyun.easyexcel;

import cn.hutool.core.io.resource.ResourceUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.enums.WriteDirectionEnum;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.merge.LoopMergeStrategy;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.*;

/**
 * 写的填充写法
 *
 * @author hyh
 * @since 2024-01-04
 */
public class FillTest {

    /**
     * 最简单的写
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>
     * 2. 直接写即可
     */
    @Test
    public void simpleWrite() {
        // 注意 simpleWrite在数据量不大的情况下可以使用（5000以内，具体也要看实际情况），数据量大参照 重复多次写入

        // 写法1 JDK8+
        // since: 3.0.0-beta1
        String fileName = TestFileUtil.getPath() + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, DemoData.class)
                .sheet("模板")
                .doWrite(() -> {
                    // 分页查询数据
                    return demoData();
                });
    }

    /**
     * 填充列表
     *
     * @since 2.1.1
     */
    @Test
    public void listFill() {
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        // 填充list 的时候还要注意 模板中{.} 多了个点 表示list
        // 如果填充list的对象是map,必须包涵所有list的key,哪怕数据为null，必须使用map.put(key,null)
        URL resource = ResourceUtil.getResource("template/list.xlsx");
        //
        String templateFileName = resource.getPath();

        // 方案2 分多次 填充 会使用文件缓存（省内存）
        String fileName = TestFileUtil.getPath() + "listFill" + System.currentTimeMillis() + ".xlsx";
        LoopMergeStrategy loopMergeStrategy = new LoopMergeStrategy(2, 0);
        // 合并单元格 MergeHandler()  可能因为是模版导出 所以将第一行数据的rowIndex视为0
        MergeHandler writeHandler = new MergeHandler(0, new ArrayList<>(Arrays.asList(10, 8)));
        MergeRowHandler mergeRowHandler = new MergeRowHandler(0);
        try (ExcelWriter excelWriter = EasyExcel.write(fileName).registerWriteHandler(mergeRowHandler).withTemplate(templateFileName).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            excelWriter.fill(data(), writeSheet);
        }
    }

    /**
     * 多列表组合填充填充
     *
     * @since 2.2.0-beta1
     */
    @Test
    public void compositeFill() {

        String path = "/Users/yuyun/Downloads/";
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        // {} 代表普通变量 {.} 代表是list的变量 {前缀.} 前缀可以区分不同的list
        URL resource = ResourceUtil.getResource("template/composite.xlsx");
        //
        String templateFileName = resource.getPath();
        ////templateFileName = path + "composite.xlsx";;
        //String fileName = path + "compositeFill" + System.currentTimeMillis() + ".xlsx";
        //templateFileName =
        //        TestFileUtil.getPath() + "demo" + File.separator + "composite.xlsx";

        String fileName = TestFileUtil.getPath() + "compositeFill" + System.currentTimeMillis() + ".xlsx";
        // 方案1
        try (ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            FillConfig fillConfig = FillConfig.builder().direction(WriteDirectionEnum.HORIZONTAL).build();
            // 如果有多个list 模板上必须有{前缀.} 这里的前缀就是 data1，然后多个list必须用 FillWrapper包裹
            excelWriter.fill(new FillWrapper("data1", data()), fillConfig, writeSheet);
            excelWriter.fill(new FillWrapper("data2", data()), writeSheet);
            excelWriter.fill(new FillWrapper("data3", data()), writeSheet);

            Map<String, Object> map = new HashMap<String, Object>();
            //map.put("date", "2019年10月9日13:28:28");
            map.put("date", new Date());
            map.put("list", data());

            excelWriter.fill(map, writeSheet);
        }
    }

    private List<FillData> data() {
        List<FillData> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            FillData fillData = new FillData();
            fillData.setName("张三");
            fillData.setNumber(5.2);
            fillData.setDate(new Date());
            list.add(fillData);
        }
        for (int i = 0; i < 8; i++) {
            FillData fillData = new FillData();
            fillData.setName("张无");
            fillData.setNumber(5.33);
            fillData.setDate(new Date());
            list.add(fillData);
        }

        return list;
    }

    private List<DemoData> demoData() {
        List<DemoData> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }
}

