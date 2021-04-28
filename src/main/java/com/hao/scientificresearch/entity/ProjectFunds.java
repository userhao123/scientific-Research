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
 * 经费支付表
 * </p>
 *
 * @author hao
 * @since 2021-04-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("b_project_funds")
public class ProjectFunds extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 项目名
     */
    private String projectName;

    /**
     * 项目id
     */
    private Integer projectId;

    /**
     * 交易状态
     */
    private String payStatus;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;

    /**
     * 交易金额
     */
    private String totalAmount;

    /**
     * 收款账号
     */
    private String sellerId;

    /**
     * 订单号
     */
    private String orderNumber;

    /**
     * 支付宝交易号
     */
    private String payNumber;


}
