package com.hao.scientificresearch.model.param;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ResearcherSearchParam {

    /**
     * 姓名
     */
    private String name;

    /**
     * 用户名
     */
    private String username;

    /**
     * 职称
     */
    private String title;

    /**
     * 所在学院
     */
    private String college;

    /**
     * 专业方向
     */
    private String major;

    /**
     * 学历#0本科1硕士2博士3高中
     */
    private String education;


}
