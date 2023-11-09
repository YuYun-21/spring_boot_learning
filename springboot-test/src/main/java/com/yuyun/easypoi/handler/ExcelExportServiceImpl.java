package com.yuyun.easypoi.handler;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.excel.export.ExcelExportService;
import cn.afterturn.easypoi.excel.export.styler.IExcelExportStyler;
import cn.afterturn.easypoi.exception.excel.ExcelExportException;
import cn.afterturn.easypoi.exception.excel.enums.ExcelExportEnum;
import cn.afterturn.easypoi.util.PoiExcelGraphDataUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.util.*;

/**
 * @author hyh
 * @since 2023-11-07
 */
public class ExcelExportServiceImpl extends ExcelExportService{

    private static int MAX_NUM = 60000;

    @Override
    protected void insertDataToSheet(Workbook workbook, ExportParams entity, List<ExcelExportEntity> entityList, Collection<?> dataSet, Sheet sheet) {
        try {
            dataHandler = entity.getDataHandler();
            if (dataHandler != null && dataHandler.getNeedHandlerFields() != null) {
                needHandlerList = Arrays.asList(dataHandler.getNeedHandlerFields());
            }
            dictHandler = entity.getDictHandler();
            commentHandler = entity.getCommentHandler();
            // 创建表格样式
            setExcelExportStyler((IExcelExportStyler) entity.getStyle()
                    .getConstructor(Workbook.class).newInstance(workbook));
            Drawing patriarch   = PoiExcelGraphDataUtil.getDrawingPatriarch(sheet);
            List<ExcelExportEntity> excelParams = new ArrayList<ExcelExportEntity>();
            if (entity.isAddIndex()) {
                excelParams.add(indexExcelEntity(entity));
            }
            excelParams.addAll(entityList);
            sortAllParams(excelParams);
            int index = entity.isCreateHeadRows()
                    ? createHeaderAndTitle(entity, sheet, workbook, excelParams) : 0;
            int titleHeight = index;
            setCellWith(excelParams, sheet);
            setColumnHidden(excelParams, sheet);
            short rowHeight = entity.getHeight() != 0 ? entity.getHeight() : getRowHeight(excelParams);
            setCurrentIndex(1);
            createAddressList(sheet, index, excelParams, 0);
            Iterator<?> its      = dataSet.iterator();
            List<Object> tempList = new ArrayList<Object>();
            while (its.hasNext()) {
                Object t = its.next();
                index += createCells(patriarch, index, t, excelParams, sheet, workbook, rowHeight, 0)[0];
                tempList.add(t);
                if (index >= MAX_NUM) {
                    break;
                }
            }
            if (entity.getFreezeCol() != 0) {
                sheet.createFreezePane(entity.getFreezeCol(), 0, entity.getFreezeCol(), 0);
            }

            mergeCells(sheet, excelParams, titleHeight);

            its = dataSet.iterator();
            for (int i = 0, le = tempList.size(); i < le; i++) {
                its.next();
                its.remove();
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("List data more than max ,data size is {}",
                        dataSet.size());
            }
            // 发现还有剩余list 继续循环创建Sheet
            if (dataSet.size() > 0) {
                createSheetForMap(workbook, entity, entityList, dataSet);
            } else {
                // 创建合计信息
                addStatisticsRow(getExcelExportStyler().getStyles(true, null), sheet);
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ExcelExportException(ExcelExportEnum.EXPORT_ERROR, e);
        }
    }

    private int createAddressList(Sheet sheet, int index, List<ExcelExportEntity> excelParams, int cellIndex) {
        for (int i = 0; i < excelParams.size(); i++) {
            if (excelParams.get(i).isAddressList()) {
                ExcelExportEntity entity = excelParams.get(i);
                CellRangeAddressList regions = new CellRangeAddressList(index,
                        this.type.equals(ExcelType.XSSF) ? 1000000 : 65535, cellIndex, cellIndex);
                DataValidation validation = sheet.getDataValidationHelper().createValidation(sheet.getDataValidationHelper().createExplicitListConstraint(getAddressListValues(entity)), regions);

                // 只能选择下拉框中的值
                validation.setSuppressDropDownArrow(true);
                // 设置输入错误提示信息
                validation.setShowErrorBox(true);
                validation.createErrorBox("错误提示", "只能下拉选择合适的值");

                sheet.addValidationData(validation);
            }
            if (excelParams.get(i).getList() != null) {
                cellIndex = createAddressList(sheet, index, excelParams.get(i).getList(), cellIndex);
            } else {
                cellIndex++;
            }
        }
        return cellIndex;
    }

    private String[] getAddressListValues(ExcelExportEntity entity) {
        if (StringUtils.isNotEmpty(entity.getDict())) {
            List<Map> list = this.dictHandler.getList(entity.getDict());
            String[] arr = new String[list.size()];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = list.get(i).get("dictValue").toString();
            }
            return arr;
        } else {
            String[] replace = entity.getReplace();
            if (replace != null && replace.length > 0) {
                String[] arr = new String[replace.length];
                for (int i = 0; i < arr.length; i++) {
                    arr[i] = replace[i].split("_")[0];
                }
                return arr;
            }
        }
        throw new ExcelExportException(entity.getName() + "没有可以创建下来的数据,请addressList不要设置为true");
    }

}
