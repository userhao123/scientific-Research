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
@TableName("b_login_log")
public class LoginLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 登录ip
     */
    private String loginIp;

    /**
     * 登录用户id
     */
    private Integer userId;

    /**
     * 登录用户名
     */
    private String userName;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;


}
