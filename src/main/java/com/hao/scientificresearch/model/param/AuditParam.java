package com.hao.scientificresearch.model.param;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 项目审核表
 * </p>
 *
 * @author hao
 *
 */
@Data
@EqualsAndHashCode
public class AuditParam {

    private static final long serialVersionUID = 1L;

//    /**
//     * 主键ID
//     */
//    private Integer id;

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
    private String  auditState;

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
