package com.hao.scientificresearch.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 登录日志表
 * </p>
 *
 * @author hao
 * @since 2021-04-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("b_operation_log")
public class OperationLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 操作用户id
     */
    private Integer userId;

    /**
     * 操作用户名
     */
    private String userName;

    /**
     * 操作时间
     */
    private LocalDateTime operateTime;

    /**
     * 执行的操作
     */
    private String operation;


}
