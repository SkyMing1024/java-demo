package com.sky.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SuperviseAllInfo implements Serializable {

    public SuperviseAllInfo(SuperviseBusinessHeaderDto header, SupervisePointDto point) {
        this.sbHeaderId = header.getSbHeaderId();
        this.levelColor = header.getLevelColor();
        this.supLevel = header.getSupLevel();
        this.businessDate = header.getBusinessDate();
        this.businessModule = header.getBusinessModule();
        this.firstTitle = header.getFirstTitle();
        this.secondTitle = header.getSecondTitle();
        this.brief = header.getBrief();
        this.status = header.getStatus();

        this.deptName = point.getDeptName();
        this.responseRole = point.getResponseRole();
        this.dealName = point.getDealName();
        this.responseStatus = point.getResponseStatus();
        this.responseDate = point.getResponseDate();
    }

    public SuperviseAllInfo(SuperviseBusinessHeaderDto header) {
        this.sbHeaderId = header.getSbHeaderId();
        this.levelColor = header.getLevelColor();
        this.supLevel = header.getSupLevel();
        this.businessDate = header.getBusinessDate();
        this.businessModule = header.getBusinessModule();
        this.firstTitle = header.getFirstTitle();
        this.secondTitle = header.getSecondTitle();
        this.brief = header.getBrief();
        this.status = header.getStatus();
    }

    @ExcelColumn( value = "序号", col = 2 )
    private String sbHeaderId;

    @ExcelColumn( value = "警示登记", col = 1 )
    private String levelColor;

    @ExcelColumn( value = "风险等级", col = 4 )
    private String supLevel;

    @ExcelColumn( value = "触发日期", col = 3 )
    private String businessDate;

    @ExcelColumn( value = "归属模块", col = 5 )
    private String businessModule;

    @ExcelColumn( value = "一级标题", col = 6 )
    private String firstTitle;

    @ExcelColumn( value = "二级标题", col = 7 )
    private String secondTitle;

    @ExcelColumn( value = "摘要", col = 8 )
    private String brief;

    @ExcelColumn( value = "处理状态", col = 9 )
    private String status;


    @ExcelColumn( value = "归属部门", col = 10)
    private String deptName;

    private String responseRole;

    @ExcelColumn( value = "处理人", col = 11)
    private String dealName;

    @ExcelColumn( value = "处理结果", col = 12)
    private String responseStatus;

    @ExcelColumn( value = "处理时间", col = 13)
    private String responseDate;
}
