package com.hao.scientificresearch.model.resp;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode
public class ProjectResp {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Integer id;

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
    private LocalDateTime createTime;

    /**
     * 结题时间
     */
    private LocalDateTime finishTime;

    /**
     * 所处阶段#0立项1中期检查2结题3延期
     */
    private String state;

    /**
     * 负责人id
     */
    private Integer leaderId;

    /**
     * 负责人名
     */
    private String leaderName;

    /**
     * 研究类别0基础研究1应用研究2实验与发展
     */
    private String category;

    /**
     * 级别#0校级1市级2省级3国家级
     */
    private String level;

    /**
     * 项目描述
     */
    private String describe;

    /**
     * 审核状态
     */
    private String audit;

    /**
     * 审核备注
     */
    private String remark;

}
