package com.sky.test;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;


/**
 * 列表左侧数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SuperviseBusinessHeaderDto implements Serializable {



    private String sbHeaderId;

    private String levelColor;

    private String supLevel;

    private String businessDate;

    private String businessModule;

    private String firstTitle;

    private String secondTitle;

    private String brief;

    private String status;

    List<SupervisePointDto> pointList;
}
