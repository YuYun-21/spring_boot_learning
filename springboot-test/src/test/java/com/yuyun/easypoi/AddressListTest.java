package com.yuyun.easypoi;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.alibaba.fastjson.JSON;
import com.yuyun.easypoi.entity.PeopleEntity;
import com.yuyun.easypoi.entity.PeopleImportEntity;
import com.yuyun.easypoi.entity.ViliGroupOne;
import com.yuyun.easypoi.handler.ExcelDicDeptAddressListHandlerImpl;
import com.yuyun.easypoi.handler.ExcelDicMapHandlerImpl;
import com.yuyun.easypoi.handler.ExcelDiceAddressListHandlerImpl;
import com.yuyun.easypoi.handler.ExcelExportUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 下拉测试
 *
 * @author hyh
 */
@Slf4j
public class AddressListTest {

    @Test
    public void dropDownTest() throws Exception {

        List<PeopleEntity> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            PeopleEntity client = new PeopleEntity();
            client.setName("小明" + i);
            client.setAge(18 + i);
            client.setStatus(i % 3 + "");
            client.setRule(i % 3);
            list.add(client);
        }
        Date start = new Date();
        ExportParams params = new ExportParams("下拉测试", "测试", ExcelType.XSSF);
        params.setDictHandler(new ExcelDiceAddressListHandlerImpl());
        Workbook workbook = ExcelExportUtils.exportExcel(params, PeopleEntity.class, list);

        System.out.println(new Date().getTime() - start.getTime());
        FileOutputStream fos = new FileOutputStream("/Users/wizdom-lcr/Downloads/AddressListTest.dropDownTest.xlsx");
        workbook.write(fos);
        fos.close();
    }

    @Test
    public void dropDownEmptyTest() throws Exception {

        List<PeopleEntity> list = new ArrayList<>();

        Date start = new Date();
        ExportParams params = new ExportParams("下拉测试", "空值测试", ExcelType.XSSF);
        params.setDictHandler(new ExcelDiceAddressListHandlerImpl());
        Workbook workbook = ExcelExportUtils.exportExcel(params, PeopleEntity.class, list);

        System.out.println(new Date().getTime() - start.getTime());
        FileOutputStream fos = new FileOutputStream("/Users/yuyun/Downloads/AddressListTest.dropDownTest.xlsx");
        workbook.write(fos);
        fos.close();
    }

    @Test
    public void dropDownXlsTest() throws Exception {

        List<PeopleEntity> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            PeopleEntity client = new PeopleEntity();
            client.setName("小明" + i);
            client.setAge(18 + i);
            client.setStatus(i % 3 + "");
            client.setRule(i % 3);
            list.add(client);
        }
        Date start = new Date();
        ExportParams params = new ExportParams("下拉测试", "测试", ExcelType.HSSF);
        params.setDictHandler(new ExcelDiceAddressListHandlerImpl());
        Workbook workbook = ExcelExportUtils.exportExcel(params, PeopleEntity.class, list);
        System.out.println(new Date().getTime() - start.getTime());
        FileOutputStream fos = new FileOutputStream("/Users/yuyun/Downloads/AddressListTest.dropDownXlsTest.xls");
        workbook.write(fos);
        fos.close();
    }

    @Test
    public void dropDownImplTest() throws Exception {

        List<PeopleEntity> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            PeopleEntity client = new PeopleEntity();
            client.setName("小明" + i);
            client.setAge(18 + i);
            client.setStatus(i % 3 + "");
            client.setRule(i % 3);
            list.add(client);
        }
        Date start = new Date();
        ExportParams params = new ExportParams("下拉测试", "测试", ExcelType.XSSF);
        params.setDictHandler(new ExcelDiceAddressListHandlerImpl());
        Workbook workbook = ExcelExportUtils.exportExcelImpl(params, PeopleEntity.class, list);

        System.out.println(new Date().getTime() - start.getTime());
        FileOutputStream fos = new FileOutputStream("/Users/yuyun/Downloads/AddressListTest.dropDownImplTest.xlsx");
        workbook.write(fos);
        fos.close();
    }

    /**
     * 导入测试
     */
    @Test
    public void importExcelTest() {

        List<PeopleImportEntity> failList = new ArrayList<>();

        try {
            ImportParams params = new ImportParams();
            params.setNeedVerify(true);
            // 表格标题行数,默认0
            params.setTitleRows(1);
            // 数据校验，只校验分组为ViliGroupOne的
            // 比如 @Size(max = 3, message = "长度不能超过3", groups = {ViliGroupOne.class})
            params.setVerifyGroup(new Class[]{ViliGroupOne.class});
            params.setDictHandler(new ExcelDiceAddressListHandlerImpl());

            ExcelImportResult<PeopleImportEntity> result = ExcelImportUtil.importExcelMore(
                    new FileInputStream("/Users/yuyun/Downloads/AddressListTest.dropDownDeptEmptyTest.xlsx"),
                    PeopleImportEntity.class,
                    params
            );

            // 失败的数据
            System.out.println("result.getFailList() = " + JSON.toJSONString(result.getFailList()));
            if (result.getFailList().size() > 0) {
                StringBuilder sb = new StringBuilder();
                for (PeopleImportEntity user : result.getFailList()) {
                    sb.append(String.format("第%d行：%s；", user.getRowNum(), user.getErrorMsg()));
                    failList.add(user);
                }
                log.error(sb.toString());
            }

            System.out.println("failList = " + JSON.toJSONString(failList));
            System.out.println("result.getFailList() = " + JSON.toJSONString(result.getFailList()));
            System.out.println("result.getList() = " + JSON.toJSONString(result.getList()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Test
    public void dropDownDeptEmptyTest() throws Exception {

        List<PeopleEntity> list = new ArrayList<>();

        Date start = new Date();
        ExportParams params = new ExportParams("下拉测试", "空值测试", ExcelType.XSSF);
        params.setDictHandler(new ExcelDicDeptAddressListHandlerImpl());
        Workbook workbook = ExcelExportUtils.exportExcel(params, PeopleEntity.class, list);

        System.out.println(new Date().getTime() - start.getTime());
        FileOutputStream fos = new FileOutputStream("/Users/yuyun/Downloads/AddressListTest.dropDownDeptEmptyTest.xlsx");
        workbook.write(fos);
        fos.close();
    }

    @Test
    public void dropDownDeptTest() throws Exception {

        List<PeopleEntity> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            PeopleEntity client = new PeopleEntity();
            client.setName("小明" + i);
            client.setAge(18 + i);
            client.setStatus(i % 3 + "");
            client.setRule(i % 3);
            client.setDept("002");
            list.add(client);
        }
        Date start = new Date();
        ExportParams params = new ExportParams("下拉测试", "空值测试", ExcelType.XSSF);
        params.setDictHandler(new ExcelDicDeptAddressListHandlerImpl());
        Workbook workbook = ExcelExportUtils.exportExcelImpl(params, PeopleEntity.class, list);

        System.out.println(new Date().getTime() - start.getTime());
        FileOutputStream fos = new FileOutputStream("/Users/yuyun/Downloads/AddressListTest.dropDownDeptEmptyTest.xlsx");
        workbook.write(fos);
        fos.close();
    }

    @Test
    public void dropDownDeptHandleTest() throws Exception {

        List<PeopleEntity> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            PeopleEntity client = new PeopleEntity();
            client.setName("小明" + i);
            client.setAge(18 + i);
            client.setStatus(i % 3 + "");
            client.setRule(i % 3);
            client.setDept("002");
            list.add(client);
        }
        Date start = new Date();
        ExportParams params = new ExportParams("下拉测试", "空值测试", ExcelType.XSSF);
        params.setDictHandler(new ExcelDicMapHandlerImpl());
        Workbook workbook = ExcelExportUtils.exportExcelImpl(params, PeopleEntity.class, list);

        System.out.println(new Date().getTime() - start.getTime());
        FileOutputStream fos = new FileOutputStream("/Users/yuyun/Downloads/AddressListTest.dropDownDeptHandleTest.xlsx");
        workbook.write(fos);
        fos.close();
    }
}
