package com.hao.scientificresearch.model.resp;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hao.scientificresearch.entity.BaseEntity;
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
@EqualsAndHashCode(callSuper = true)
@TableName("b_achievement")
public class AchievementResp extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 项目id
     */
    private Integer projectId;

    /**
     * 项目名
     */
    private String projectName;

    /**
     * 成果名
     */
    private String name;

    /**
     * 编号
     */
    private String number;

    /**
     * 成果类别#0基础理论成果，应用技术成果，软科学成果
     */
    private String kind;

    /**
     * 成果形式
     */
    private String form;

    /**
     * 取得时间
     */
    private LocalDate getTime;

    /**
     * 成果描述
     */
    private String described;


}
