package com.yuyun.easyexcel;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.handler.context.RowWriteHandlerContext;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;
import java.util.Objects;

/**
 * 合并行相同值单元格处理程序
 * 该方法不可用 如果数据列表是横向输出 relativeRowIndex定位不到数据行
 *
 * @author hyh
 * @date 2024/01/05
 * @since 2024-01-04
 */
public class MergeCellHandler implements RowWriteHandler {
    /**
     * 数据相对行数
     */
    private final int relativeRowIndex;
    /**
     * 起始列
     */
    private int firstColIndex;
    /**
     * 每次需要合并的列数
     */
    private final List<Integer> colIndexList;

    public MergeCellHandler(int relativeRowIndex, int startCellIndex, List<Integer> colIndexList) {
        if (relativeRowIndex >= 0 && startCellIndex >= 0 && CollUtil.isNotEmpty(colIndexList)) {
            this.relativeRowIndex = relativeRowIndex;
            this.firstColIndex = startCellIndex;
            this.colIndexList = colIndexList;
        } else {
            throw new IllegalArgumentException("All parameters must be greater than 0");
        }
    }

    @Override
    public void afterRowDispose(RowWriteHandlerContext context) {
        // context.getRelativeRowIndex()    相对数据索引
        // context.getRowIndex()            Excel index
        if (!context.getHead() && context.getRelativeRowIndex() != null && CollUtil.isNotEmpty(colIndexList)) {
            if (Objects.equals(context.getRelativeRowIndex(), relativeRowIndex)) {
                colIndexList.forEach(index -> {
                    int lastColIndex = this.firstColIndex + index;
                    CellRangeAddress cellRangeAddress = new CellRangeAddress(context.getRowIndex(), context.getRowIndex(), this.firstColIndex, lastColIndex - 1);
                    context.getWriteSheetHolder().getSheet().addMergedRegionUnsafe(cellRangeAddress);
                    this.firstColIndex = lastColIndex;
                });
            }
        }
    }
}
