package com.yuyun.easypoi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.yuyun.easypoi.entity.PeopleEntity;
import com.yuyun.easypoi.entity.PeopleExcel;
import com.yuyun.easypoi.entity.PeopleImportEntity;
import com.yuyun.easypoi.entity.ViliGroupOne;
import com.yuyun.easypoi.handler.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Excel数据下拉选择测试
 *
 * @author hyh
 */
@Slf4j
@SpringBootTest
public class PeopleExcelTest {

    @Autowired
    private ExcelDictHandler excelDictHandler;

    /**
     * 导出测试
     *
     * @throws Exception
     */
    @Test
    public void exportExcelTest() throws Exception {

        Date start = new Date();
        ExportParams params = new ExportParams("下拉测试", "空值测试", ExcelType.XSSF);
        params.setDictHandler(excelDictHandler);
        params.setTitleHeight((short) 20);
        Workbook workbook = ExcelExportUtils.exportExcel(params, PeopleExcel.class, new ArrayList<>());

        System.out.println(new Date().getTime() - start.getTime());
        FileOutputStream fos = new FileOutputStream("/Users/wizdom-lcr/Downloads/PeopleExcelTest.exportExcelTest.xlsx");
        workbook.write(fos);
        fos.close();
    }

    /**
     * 导入测试
     */
    @Test
    public void importExcelMoreTest() {

        List<PeopleExcel> failList = new ArrayList<>();

        try {
            ImportParams params = new ImportParams();
            // 是否需要校验上传的Excel，默认为false
            params.setNeedVerify(true);
            // 表格标题行数,默认0
            params.setTitleRows(1);
            // 数据校验，只校验分组为ViliGroupOne的
            // 比如 @Size(max = 3, message = "长度不能超过3", groups = {ViliGroupOne.class})
            //params.setVerifyGroup(new Class[]{ViliGroupOne.class});
            params.setDictHandler(excelDictHandler);

            ExcelImportResult<PeopleExcel> result = ExcelImportUtil.importExcelMore(
                    new FileInputStream("/Users/wizdom-lcr/Downloads/PeopleExcelTest.exportExcelTest.xlsx"),
                    PeopleExcel.class,
                    params
            );

            //失败的数据
            List<String> errorMsgList = result.getFailList().stream()
                    .map(excel -> StrUtil.format("第{}行：{}", excel.getRowNum() + 1, excel.getErrorMsg()))
                    .collect(Collectors.toList());

            System.out.println("failList = " + JSON.toJSONString(failList));
            System.out.println("result.getFailList() = " + JSON.toJSONString(result.getFailList()));
            System.out.println("result.getList() = " + JSON.toJSONString(result.getList()));
            System.out.println("errorMsgList = " + JSON.toJSONString(errorMsgList));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void export(HttpServletResponse response, HttpServletRequest request, ModelMap modelMap) {
        try {
            String fileName = "批量导出.zip";
            response.setContentType("application/octet-stream");
            response.setHeader("Connection", "close"); // 表示不能用浏览器直接打开
            response.setHeader("Accept-Ranges", "bytes");// 告诉客户端允许断点续传多线程连接下载
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(fileName.getBytes("GB2312"), "ISO8859-1"));
            response.setCharacterEncoding("UTF-8");

            OutputStream out = response.getOutputStream();
            ZipOutputStream zos = new ZipOutputStream(out);

            //读取路径下的模板
            TemplateExportParams params = new TemplateExportParams("导出模板.xlsx");
            //获取业务数据逻辑
            List<HashMap<String, Object>> exportList = new ArrayList<>();

            //循环导出
            for (HashMap<String, Object> map : exportList) {
                //导出的文件名称
                String entryName = (String) map.get("title") + ".xlsx";
                ZipEntry entry = new ZipEntry(entryName);
                //easypoi 导出excel文件
                Workbook workbook = ExcelExportUtil.exportExcel(params, map);
                //编写新的zip条目，并将流定位到条目数据的开头
                zos.putNextEntry(entry);
                //workBook.write会指定关闭数据流，直接用workbook.write(zos)，下次就会抛出zos已被关闭的异常，所以用ByteArrayOutputStream来拷贝一下。
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                //workbook写入bos
                workbook.write(bos);
                //bos写入zos
                bos.writeTo(zos);
                zos.closeEntry();
            }
            if(zos != null) {
                zos.flush();
                zos.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
