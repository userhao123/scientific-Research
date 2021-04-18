package com.hao.scientificresearch.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hao.scientificresearch.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统管理员信息表
 * </p>
 *
 * @author hao
 * @since 2021-03-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("b_administrator")
public class Administrator extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String number;

    /**
     * 管理员名
     */
    private String name;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别
     */
    private String gender;


}
