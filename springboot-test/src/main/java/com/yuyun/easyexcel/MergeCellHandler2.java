package com.yuyun.easyexcel;

import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.handler.context.RowWriteHandlerContext;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.Objects;

/**
 * 合并列单元格处理程序
 * <br> 从 起始行 开始，经过多少行结束，合并多少列
 * @author hyh
 * @date 2024/02/21
 */
public class MergeCellHandler2 implements RowWriteHandler {
    /**
     * 起始行
     */
    private int startRowIndex;
    /**
     * 从 起始行 开始，经过多少行结束
     */
    private final int rowSum;
    /**
     * 需要合并的列数
     */
    private final int cellNum;

    public MergeCellHandler2(int startRowIndex, int rowSum, int cellNum) {
        if (startRowIndex >= 0 && rowSum >= 0 && cellNum >= 0) {
            this.startRowIndex = startRowIndex;
            this.rowSum = rowSum;
            this.cellNum = cellNum;
        } else {
            throw new IllegalArgumentException("All parameters must be greater than 0");
        }
    }

    @Override
    public void afterRowDispose(RowWriteHandlerContext context) {
        // context.getRelativeRowIndex()    相对数据索引
        // context.getRowIndex()            Excel index
        if (!context.getHead() && context.getRelativeRowIndex() != null) {
            if (Objects.equals(context.getRelativeRowIndex(), startRowIndex)) {
                startRowIndex = startRowIndex - 1;
                for (int i = 0; i < rowSum; i++) {
                    CellRangeAddress cellRangeAddress = new CellRangeAddress(startRowIndex + i, startRowIndex + i, 0, cellNum - 1);
                    context.getWriteSheetHolder().getSheet().addMergedRegionUnsafe(cellRangeAddress);
                }
            }
        }
    }
}
