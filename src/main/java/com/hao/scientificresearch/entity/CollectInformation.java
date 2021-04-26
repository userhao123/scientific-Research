package com.hao.scientificresearch.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.hao.scientificresearch.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 信息收集表
 * </p>
 *
 * @author hao
 * @since 2021-04-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("b_collect_information")
public class CollectInformation extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 收集信息类型#0科研信息1用户信息
     */
    private Integer informationType;

    /**
     * 字段名字符串，以,分隔
     */
    private String fieldName;

    /**
     * 收集科研信息时的项目名
     */
    private String projectName;

    /**
     * 是否已填写
     */
    private Boolean isWrited;

    /**
     * 收集人
     */
    private String collector;


}
