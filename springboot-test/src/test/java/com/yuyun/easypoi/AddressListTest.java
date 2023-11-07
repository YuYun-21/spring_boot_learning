package com.yuyun.easypoi;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.yuyun.easypoi.entity.PeopleEntity;
import com.yuyun.easypoi.handler.ExcelDiceAddressListHandlerImpl;
import com.yuyun.easypoi.handler.ExcelExportUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 下拉测试
 *
 * @author jueyue on 20-4-26.
 */
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
}
