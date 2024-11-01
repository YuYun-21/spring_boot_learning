package com.yuyun.easyexcel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author hyh
 * @since 2024-01-04
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class FillData {
    private String name;
    private double number;
    private Date date;
    private List<FillData> fillDataList;
}
