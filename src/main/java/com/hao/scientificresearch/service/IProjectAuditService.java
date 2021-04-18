package com.hao.scientificresearch.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hao.scientificresearch.entity.ProjectAudit;
import com.hao.scientificresearch.model.param.AuditParam;

/**
 * <p>
 * 项目审核表 服务类
 * </p>
 *
 * @author hao
 * @since 2021-04-05
 */
public interface IProjectAuditService extends IService<ProjectAudit> {

    boolean audit(AuditParam param);



}
