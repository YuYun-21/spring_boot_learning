package com.yuyun.easyexcel;

import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.handler.context.RowWriteHandlerContext;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;

/**
 * 合并行相同值单元格处理程序
 * <br> 合并某一列多行 从rowIndex开始，每经过rowList中的值合并一次
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
     * 每次需要合并的行数
     */
    private final List<Integer> rowList;
    /**
     * 起始行
     */
    private int rowIndex;
    /**
     * rowList下标idx
     */
    private int rowListIdx;

    public MergeHandler(int columnIndex, List<Integer> rowList) {
        if (columnIndex < 0) {
            throw new IllegalArgumentException("ColumnIndex must be greater than 0");
        }
        this.columnIndex = columnIndex;
        this.rowList = rowList;
        this.rowIndex = 0;
        this.rowListIdx = 0;
    }

    @Override
    public void afterRowDispose(RowWriteHandlerContext context) {
        // context.getRelativeRowIndex()    相对数据索引
        // context.getRowIndex()            Excel index
        if (!context.getHead() && context.getRelativeRowIndex() != null) {
            if (context.getRelativeRowIndex() == this.rowIndex) {
                Integer row = rowList.get(rowListIdx);
                this.rowIndex = this.rowIndex + row;
                // 合并的行数必须大于1，否则报错
                if (row > 1) {
                    CellRangeAddress cellRangeAddress = new CellRangeAddress(context.getRowIndex(), context.getRowIndex() - 1 + rowList.get(rowListIdx), this.columnIndex, this.columnIndex);
                    Sheet sheet = context.getWriteSheetHolder().getSheet();
                    sheet.addMergedRegionUnsafe(cellRangeAddress);
                }
                rowListIdx++;
            }
        }
    }
}
