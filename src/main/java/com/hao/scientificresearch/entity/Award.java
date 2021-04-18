package com.hao.scientificresearch.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hao.scientificresearch.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 科研奖励表
 * </p>
 *
 * @author hao
 * @since 2021-03-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("b_award")
public class Award extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 项目id
     */
    private Integer projectId;

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
