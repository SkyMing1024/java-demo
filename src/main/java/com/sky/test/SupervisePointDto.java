package com.sky.test;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * 列表右侧数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SupervisePointDto {

    private String sbHeaderId;

    private String sbResponseId;

    private String deptName;

    private String responseRole;

    private String dealName;

    private String responseStatus;

    private String responseDate;

    private String buttonDisplay;

}
