package com.yuyun.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.merge.OnceAbsoluteMergeStrategy;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.row.SimpleRowHeightStyleStrategy;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 写
 *
 * @author hyh
 * @since 2024-02-21
 */
public class WriteTest {

    @Test
    public void write() {
        MergeCellHandler2 mergeCellHandler2 = new MergeCellHandler2(5, 7, 2);
        String fileName = TestFileUtil.getPath() + "write" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, ComplexHeadData.class).sheet("模板")
                .registerWriteHandler(mergeCellHandler2)
                .doWrite(data());
    }

    /**
     * 使用table去写入
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>
     * 2. 然后写入table即可
     */
    @Test
    public void tableWrite() {
        String fileName = TestFileUtil.getPath() + "tableWrite" + System.currentTimeMillis() + ".xlsx";
        List<List<String>> head2 = head();
        // 合并单元格
        OnceAbsoluteMergeStrategy onceAbsoluteMergeStrategy = new OnceAbsoluteMergeStrategy(1, 1, 0, head2.size() - 2);
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                new HorizontalCellStyleStrategy(new WriteCellStyle(), contentWriteCellStyle);
        // 方法1 这里直接写多个table的案例了，如果只有一个 也可以直一行代码搞定，参照其他案
        // 这里 需要指定写用哪个class去写
        try (ExcelWriter excelWriter = EasyExcel.write(fileName)
                .registerWriteHandler(onceAbsoluteMergeStrategy)

                .build()) {

            List<List<String>> head0 = new ArrayList<>();
            List<String> list = new ArrayList<>();
            list.add("中国移动云南公司2022年至2025年全省物业服务框架采购项目采购合同\nxx年xx月物业服务费明细结算表");
            for (int i = 0; i < head2.size(); i++) {
                head0.add(list);
            }

            // 把sheet设置为不需要头 不然会输出sheet的头 这样看起来第一个table 就有2个头了
            WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();

            // 这里必须指定需要头，table 会继承sheet的配置，sheet配置了不需要，table 默认也是不需要
            WriteTable writeTable0 = EasyExcel.writerTable(0).registerWriteHandler(new SimpleRowHeightStyleStrategy((short) 60, null)).head(head0).build();
            // 这里必须指定需要头，table 会继承sheet的配置，sheet配置了不需要，table 默认也是不需要
            WriteTable writeTable1 = EasyExcel.writerTable(1).head(head1(head2.size())).build();

            WriteTable writeTable2 = EasyExcel.writerTable(2).head(head2).build();
            // 第一次写入会创建头
            excelWriter.write(new ArrayList<>(), writeSheet, writeTable0);
            excelWriter.write(new ArrayList<>(), writeSheet, writeTable1);
            // 第二次写如也会创建头，然后在第一次的后面写入数据
            excelWriter.write(data(), writeSheet, writeTable2);
        }
    }

    /**
     * 复杂头写入
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link ComplexHeadData}
     * <p>
     * 2. 使用{@link ExcelProperty}注解指定复杂的头
     * <p>
     * 3. 直接写即可
     */
    @Test
    public void complexHeadWrite() {
        String fileName = TestFileUtil.getPath() + "complexHeadWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, ComplexHeadData.class).sheet("模板").doWrite(data());
    }

    @Test
    public void complexHeadWrite1() {

        String fileName = TestFileUtil.getPath() + "complexHeadWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName).head(statisticsInfoHead()).sheet("模板").doWrite(data());
    }

    private List<List<String>> statisticsInfoHead() {
        List<List<String>> list = ListUtils.newArrayList();

        List<String> head0 = ListUtils.newArrayList();
        head0.add("单位");
        list.add(head0);

        List<String> quarters = Arrays.asList("截止一季度", "截止二季度", "截止三季度", "截止四季度");

        String basic = "基础得分";
        String basicRating = "基础评分";
        String lineScore = "条线评分";
        String safetyInspection = "安全检查";
        String basicTotalScore = "基础得分小计";
        String bonusPoints = "加分";
        String deductionPoints = "扣分";
        String totalScore = "合计";
        String ranking = "排名";

        List<String> headTitles1 = Arrays.asList("基础评分", "条线评分", "安全检查", "基础得分小计");
        List<String> headTitles2 = Arrays.asList("加分", "扣分", "合计", "排名");

        for (String quarter : quarters) {
            for (String headTitle : headTitles1) {
                List<String> head = ListUtils.newArrayList();
                head.add(quarter);
                head.add(basic);
                head.add(headTitle);
                list.add(head);
            }
            for (String headTitle : headTitles2) {
                List<String> head = ListUtils.newArrayList();
                head.add(quarter);
                head.add(headTitle);
                list.add(head);
            }
        }

        return list;
    }

    private List<List<String>> head1(int size) {
        List<List<String>> list = ListUtils.newArrayList();
        List<String> head0 = ListUtils.newArrayList();
        head0.add("单位：全省物业服务框架采购项目采购合同");
        list.add(head0);

        for (int i = 0; i < size - 2; i++) {
            List<String> head1 = ListUtils.newArrayList();
            head1.add("");
            list.add(head1);
        }

        List<String> head2 = ListUtils.newArrayList();
        head2.add("单位：（元）");
        list.add(head2);

        return list;
    }

    private List<List<String>> head() {
        List<List<String>> list = ListUtils.newArrayList();
        List<String> head0 = ListUtils.newArrayList();
        head0.add("项目");
        List<String> head1 = ListUtils.newArrayList();
        head1.add("内容");
        List<String> head2 = ListUtils.newArrayList();
        head2.add("单位");

        List<String> head3 = ListUtils.newArrayList();
        head3.add("总金额");
        List<String> head4 = ListUtils.newArrayList();
        head4.add("备注");

        list.add(head0);
        list.add(head1);
        list.add(head2);
        list.addAll(addDeptHead(Arrays.asList("云南省总公司", "昆明市分公司", "曲靖市分公司", "昭通市分公司")));
        list.add(head3);
        list.add(head4);
        return list;
    }

    private List<List<String>> addDeptHead(List<String> deptNameList) {
        List<List<String>> list = ListUtils.newArrayList();

        for (String deptName : deptNameList) {
            List<String> head1 = ListUtils.newArrayList();
            head1.add(deptName);
            head1.add("单位");
            head1.add("单位1");
            List<String> head2 = ListUtils.newArrayList();
            head2.add(deptName);
            head2.add("数量");
            head2.add("数量1");
            List<String> head3 = ListUtils.newArrayList();
            head3.add(deptName);
            head3.add("金额");
            head3.add("金额1");
            list.add(head1);
            list.add(head2);
            list.add(head3);
        }

        return list;
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
