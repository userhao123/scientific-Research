package com.hao.scientificresearch.model.resp;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ResearchResp {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 姓名
     */
    private String name;

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

    /**
     * 职称
     */
    private String title;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 所在学院
     */
    private String college;

    /**
     * 专业方向
     */
    private String major;

    /**
     * 学历#0本科1硕士2博士3高中
     */
    private String education;

    /**
     * 角色#user/auditor
     */
    private String role;
}
