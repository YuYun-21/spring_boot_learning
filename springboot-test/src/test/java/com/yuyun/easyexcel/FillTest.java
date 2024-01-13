package com.yuyun.easyexcel;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.ZipUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.enums.WriteDirectionEnum;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.merge.AbstractMergeStrategy;
import com.alibaba.excel.write.merge.LoopMergeStrategy;
import com.alibaba.excel.write.merge.OnceAbsoluteMergeStrategy;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
import com.alibaba.excel.write.style.row.SimpleRowHeightStyleStrategy;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
    public void simpleWriteZip() throws IOException {

        // 使用try-with-resource语法关闭流
        try (FileOutputStream fileOutputStream = new FileOutputStream(TestFileUtil.getPath() + "simpleWriteZip" + System.currentTimeMillis() + ".zip");
        ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream)) {

            for (int i = 0; i < 10; i++) {
                // 写法1 JDK8+
                String fileName = i + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
                // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
                // 如果这里想使用03 则 传入excelType参数即可

                try (ByteOutputStream byteOutputStream = new ByteOutputStream()) {
                    zipOutputStream.putNextEntry(new ZipEntry(fileName));
                    EasyExcel.write(byteOutputStream, DemoData.class)
                            .sheet("模板")
                            // 分页查询数据
                            .doWrite(this::demoData);
                    byteOutputStream.writeTo(zipOutputStream);
                }
                zipOutputStream.closeEntry();
            }
        }
    }

    /**
     * 填充列表 合并列
     *
     * @since 2.1.1
     */
    @Test
    public void listFill1() {
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        // 填充list 的时候还要注意 模板中{.} 多了个点 表示list
        // 如果填充list的对象是map,必须包涵所有list的key,哪怕数据为null，必须使用map.put(key,null)
        URL resource = ResourceUtil.getResource("template/list1.xlsx");
        //
        String templateFileName = resource.getPath();

        // 方案2 分多次 填充 会使用文件缓存（省内存）
        String fileName = TestFileUtil.getPath() + "listFill1" + System.currentTimeMillis() + ".xlsx";

        // 合并单元格
        OnceAbsoluteMergeStrategy onceAbsoluteMergeStrategy = new OnceAbsoluteMergeStrategy(0, 1, 0, 2);
        FillConfig fillConfig = FillConfig.builder().direction(WriteDirectionEnum.HORIZONTAL).build();
        try (ExcelWriter excelWriter = EasyExcel.write(fileName)
                // 自动列宽
                //.registerWriteHandler(new MergeCellHandler1(5, 4, CollUtil.newArrayList(10,8)))
                .registerWriteHandler(new MergeCellHandler(0, 1, CollUtil.newArrayList(10, 8)))
                .withTemplate(templateFileName).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            excelWriter.fill(data(), fillConfig, writeSheet);
        }
    }

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
        // 合并单元格 每两行
        LoopMergeStrategy loopMergeStrategy = new LoopMergeStrategy(2, 0);
        // 合并单元格 MergeHandler()
        MergeHandler writeHandler = new MergeHandler(0, new ArrayList<>(Arrays.asList(10, 8)));
        // 合并单元格
        MergeRowHandler mergeRowHandler = new MergeRowHandler(0);
        // 合并单元格
        OnceAbsoluteMergeStrategy onceAbsoluteMergeStrategy = new OnceAbsoluteMergeStrategy(0, 1, 0, 2);

        try (ExcelWriter excelWriter = EasyExcel.write(fileName)
                .registerWriteHandler(mergeRowHandler)
                // 自动列宽
                .registerWriteHandler(new MatchColumnWidthStyleStrategy())
                .registerWriteHandler(new SimpleRowHeightStyleStrategy(null, (short) 25))
                .withTemplate(templateFileName).build()) {
            FillConfig fillConfig = FillConfig.builder()
                    .direction(WriteDirectionEnum.VERTICAL)
                    // 为true list遍历的新数据 不需要创建新表格 也就是不会覆盖模版中后面几行已有的数据
                    .forceNewRow(true)
                    .build();
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            excelWriter.fill(data(), fillConfig, writeSheet);
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
            FillConfig fillConfig = FillConfig.builder()
                    // 横向遍历
                    .direction(WriteDirectionEnum.HORIZONTAL)
                    // 是否复制样式
                    .autoStyle(false)
                    // 为true list遍历的新数据 不需要创建新表格 也就是不会覆盖模版中后面几行已有的数据
                    // 横向遍历的时候不生效
                    .forceNewRow(true)
                    .build();
            FillConfig fillConfig1 = FillConfig.builder()
                    // 纵向向下遍历
                    .direction(WriteDirectionEnum.VERTICAL)
                    .forceNewRow(true)
                    // 是否复制样式
                    .autoStyle(false)
                    .build();
            // 如果有多个list 模板上必须有{前缀.} 这里的前缀就是 data1，然后多个list必须用 FillWrapper包裹
            excelWriter.fill(new FillWrapper("data1", data()), fillConfig, writeSheet);
            excelWriter.fill(new FillWrapper("data2", data()), fillConfig1, writeSheet);
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

