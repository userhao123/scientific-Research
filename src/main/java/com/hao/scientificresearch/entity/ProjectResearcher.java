package com.hao.scientificresearch.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hao.scientificresearch.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 项目与参与人关系表（多对多）
 * </p>
 *
 * @author hao
 * @since 2021-03-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("b_project_researcher")
public class ProjectResearcher extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 项目id
     */
    private Integer projectId;

    /**
     * 参与人id
     */
    private Integer researcherId;


}
