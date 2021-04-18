package com.hao.scientificresearch.model.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode
public class ProjectParam {


    private static final long serialVersionUID = 1L;

    /**
     * 项目名
     */
    private String name;

    /**
     * 负责人姓名
     */
    private String leaderName;

    /**
     * 研究类别0基础研究1应用研究2实验与发展
     */
    private Integer category;

    /**
     * 级别#0校级1市级2省级3国家级
     */
    private Integer level;

    /**
     * 项目描述
     */
    private String described;

}
