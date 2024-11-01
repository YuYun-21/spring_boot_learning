package com.yuyun.easypoi;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.util.PoiMergeCellUtil;
import org.junit.jupiter.api.Test;

/**
 * Created by JueYue on 2017/8/25.
 */
public class TemplateFEManyTest {

    @Test
    public void exportCompanyImg() throws Exception {

        File savefile = new File("/Users/wizdom-lcr/Downloads/");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        List<CompanyHasImgModel> list= new ArrayList<CompanyHasImgModel>();
        list.add(new CompanyHasImgModel("百度", "http://y3.ifengimg.com/a/2016_03/6154e935f8a0fc6.jpg", "北京市海淀区西北旺东路10号院百度科技园1号楼"));
        list.add(new CompanyHasImgModel("阿里巴巴", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561861328&di=a515b18b702e027878df86bdbe1d1a3f&imgtype=jpg&er=1&src=http%3A%2F%2Fimg1.gtimg.com%2Fhb%2Fpics%2Fhv1%2F125%2F206%2F1609%2F104677880.jpg", "北京市海淀区西北旺东路10号院百度科技园1号楼"));
        list.add(new CompanyHasImgModel("Lemur", "imgs/company/lemur.png", "亚马逊热带雨林"));
        list.add(new CompanyHasImgModel("一众", "imgs/company/one.png", "山东济宁俺家"));

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), CompanyHasImgModel.class, list);
        FileOutputStream fos  = new FileOutputStream("/Users/wizdom-lcr/Downloads/ExcelExportUrlImgTest.exportCompanyUrlImg.xls");
        workbook.write(fos);
        fos.close();
    }

    @Test
    public void test()  throws Exception {
        List<Map<String, String>> onelist = new ArrayList<Map<String, String>>();
        for (int i = 0; i < 30; i++) {
            onelist.add(getMap("第一组", i));
        }

        List<Map<String, String>> twolist = new ArrayList<Map<String, String>>();
        for (int i = 0; i < 1; i++) {
            twolist.add(getMap("第二组", i));
        }
        List<Map<String, String>> threelist = new ArrayList<Map<String, String>>();
        for (int i = 0; i < 1; i++) {
            threelist.add(getMap("第三组", i));
        }
        List<Map<String, String>> fourlist = new ArrayList<Map<String, String>>();
        for (int i = 0; i < 10; i++) {
            fourlist.add(getMap("第四组", i));
        }
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("onelist", onelist);
        map.put("twolist", twolist);
        map.put("threelist", threelist);
        map.put("fourlist", fourlist);

        TemplateExportParams params = new TemplateExportParams(
                "template/foreach_insert_many.xlsx");
        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
        PoiMergeCellUtil.mergeCells(workbook.getSheetAt(0), 1, 0, 4);
        File savefile = new File("/Users/wizdom-lcr/Downloads/");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream("/Users/wizdom-lcr/Downloads/TemplateFEManyTest_test.xlsx");
        workbook.write(fos);
        fos.close();
    }

    private Map<String, String> getMap(String name, int i) {

        Map<String, String> map = new HashMap<String, String>();
        map.put("name", name + i);
        map.put("sex", "猿人");
        map.put("addr", "中国孔子家附近");
        map.put("email", "jn@email.com");
        return map;
    }
}
