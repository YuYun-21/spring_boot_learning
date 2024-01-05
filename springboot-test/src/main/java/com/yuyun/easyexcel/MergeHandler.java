package com.yuyun.easyexcel;

import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.handler.context.RowWriteHandlerContext;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;

/**
 * 合并行相同值单元格处理程序
 *
 * @author hyh
 * @date 2024/01/05
 * @since 2024-01-04
 */
public class MergeHandler implements RowWriteHandler {
    /**
     * 列索引
     */
    private final int columnIndex;
    /**
     * 起始行
     */
    private int rowIndex;
    /**
     * 每次需要合并的行数
     */
    private final List<Integer> rowList;

    public MergeHandler(int columnIndex, List<Integer> rowList) {
        if (columnIndex < 0) {
            throw new IllegalArgumentException("ColumnIndex must be greater than 0");
        }
        this.columnIndex = columnIndex;
        this.rowList = rowList;
        this.rowIndex = 0;
    }

    @Override
    public void afterRowDispose(RowWriteHandlerContext context) {
        // context.getRelativeRowIndex()    相对数据索引
        // context.getRowIndex()            Excel index
        if (!context.getHead() && context.getRelativeRowIndex() != null) {
            if (context.getRelativeRowIndex() == this.rowIndex) {
                this.rowIndex = this.rowIndex + rowList.get(0);
                CellRangeAddress cellRangeAddress = new CellRangeAddress(context.getRowIndex(), context.getRowIndex() -1+ rowList.get(0), this.columnIndex, this.columnIndex);
                context.getWriteSheetHolder().getSheet().addMergedRegionUnsafe(cellRangeAddress);
                rowList.remove(0);
            }
        }
    }
}
