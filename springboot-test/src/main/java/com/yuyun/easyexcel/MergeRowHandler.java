package com.yuyun.easyexcel;

import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.handler.context.RowWriteHandlerContext;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.Objects;

/**
 * 合并行相同值单元格处理程序
 * <br> 注意这个方法合并的单元格文字没有居中
 *
 * @author hyh
 * @date 2024/01/05
 * @since 2024-01-04
 */
public class MergeRowHandler implements RowWriteHandler {
    /**
     * 需要合并单元格的列
     */
    private final int columnIndex;
    /**
     * 保存上一行的值
     */
    private String lastCellValue;

    public MergeRowHandler(int columnIndex) {
        if (columnIndex < 0) {
            throw new IllegalArgumentException("ColumnIndex must be greater than 0");
        }
        this.columnIndex = columnIndex;
        // 初始化为 null
        this.lastCellValue = null;
    }

    @Override
    public void afterRowDispose(RowWriteHandlerContext context) {
        // context.getRelativeRowIndex()    相对数据索引
        // context.getRowIndex()            Excel index
        if (!context.getHead() && context.getRelativeRowIndex() != null) {
            String currentCellValue = context.getRow().getCell(columnIndex).getStringCellValue();
            if (Objects.equals(currentCellValue, lastCellValue)) {
                // 相邻行相同值，进行合并
                CellRangeAddress cellRangeAddress = new CellRangeAddress(
                        context.getRowIndex() - 1,
                        context.getRowIndex(),
                        columnIndex,
                        columnIndex
                );
                context.getWriteSheetHolder().getSheet().addMergedRegionUnsafe(cellRangeAddress);
            }
            // 更新上一行的值
            lastCellValue = currentCellValue;
        }
    }
}
