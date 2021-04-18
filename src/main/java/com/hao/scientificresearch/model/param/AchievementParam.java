package com.hao.scientificresearch.model.param;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * <p>
 * 科研成果表
 * </p>
 *
 * @author hao
 *
 */
@Data
@EqualsAndHashCode
@TableName("b_achievement")
public class AchievementParam {

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
     * 成果名
     */
    private String name;


    /**
     * 成果类别#0基础理论成果，应用技术成果，软科学成果
     */
    private Integer kind;

    /**
     * 成果形式
     */
    private String form;

    /**
     * 取得时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private LocalDate getTime;

    /**
     * 成果描述
     */
    private String described;


}
