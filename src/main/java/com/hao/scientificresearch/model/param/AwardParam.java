package com.hao.scientificresearch.model.param;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * <p>
 * 科研奖励表
 * </p>
 *
 * @author hao
 * @since 2021-03-31
 */
@Data
@EqualsAndHashCode
public class AwardParam {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 项目名
     */
    private String projectName;

    /**
     * 奖励名
     */
    private String name;

    /**
     * 获奖等级#0一等奖1二等奖2三等奖
     */
    private Integer level;

    /**
     * 颁奖单位
     */
    private String awardUnit;

    /**
     * 取得时间
     */
    private LocalDate getTime;


}
