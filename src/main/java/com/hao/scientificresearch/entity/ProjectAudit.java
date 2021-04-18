package com.hao.scientificresearch.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hao.scientificresearch.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 项目审核表
 * </p>
 *
 * @author hao
 * @since 2021-04-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("b_project_audit")
public class ProjectAudit extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 项目id
     */
    private Integer projectId;

    /**
     * 审核人id
     */
    private Integer auditorId;

    /**
     * 审核阶段#0新建1立项2中期检查3结题4延期
     */
    private Integer auditState;

    /**
     * 审核状态
     */
    private String auditStatus;

    /**
     * 审核备注
     */
    private String remark;

    /**
     * 审核时间
     */
    private LocalDateTime auditTime;


}
