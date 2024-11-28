package com.yuyun.easyexcel;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;

/**
 * 合并行相同值单元格处理程序
 * <br> 合并某一行多列 从startColIndex开始，每经过cellList中的值合并一次
 *
 * @author hyh
 * @date 2024/01/05
 * @since 2024-01-04
 */
public class MergeCellHandler1 implements SheetWriteHandler {
    /**
     * 需要合并的行号
     */
    private final int rowIndex;
    /**
     * 起始列
     */
    private int startColIndex;
    /**
     * 每次需要合并的列数
     */
    private final List<Integer> colIndexList;

    public MergeCellHandler1(int rowIndex, int startColIndex, List<Integer> colIndexList) {
        if (rowIndex >= 0 && startColIndex >= 0 && CollUtil.isNotEmpty(colIndexList)) {
            this.rowIndex = rowIndex;
            this.startColIndex = startColIndex;
            this.colIndexList = colIndexList;
        } else {
            throw new IllegalArgumentException("All parameters must be greater than 0");
        }
    }

    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        Sheet sheet = writeSheetHolder.getSheet();
        colIndexList.forEach(index -> {
            int lastColIndex = this.startColIndex + index;
            CellRangeAddress cellRangeAddress = new CellRangeAddress(this.rowIndex, this.rowIndex, this.startColIndex, lastColIndex - 1);
            sheet.addMergedRegionUnsafe(cellRangeAddress);
            this.startColIndex = lastColIndex;
        });
    }
}
