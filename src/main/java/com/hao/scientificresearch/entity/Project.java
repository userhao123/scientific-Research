package com.hao.scientificresearch.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 科研项目信息表
 * </p>
 *
 * @author hao
 * @since 2021-03-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("b_project")
public class Project extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 项目名
     */
    private String name;

    /**
     * 编号
     */
    private String number;

    /**
     * 立项时间
     */
    @JsonFormat(pattern = "yyyy-mm-dd HH-MM-ss",timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 结题时间
     */
    @JsonFormat(pattern = "yyyy-mm-dd HH-MM-ss",timezone = "GMT+8")
    private LocalDateTime finishTime;

    /**
     * 所处阶段#0新建1立项2中期检查3结题4延期
     */
    private Integer state;

    /**
     * 负责人id
     */
    private Integer leaderId;

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

    /**
     * 审核状态
     */
    private String audit;

    /**
     * 项目经费
     */
    private BigDecimal funds;

    /**
     * 是否已支付
     */
    private Boolean isPay;


}
