package com.hao.scientificresearch.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hao.scientificresearch.entity.ProjectAudit;
import com.hao.scientificresearch.model.param.AuditParam;
import com.hao.scientificresearch.model.resp.ProjectAuditResp;
import com.hao.scientificresearch.model.resp.ProjectFileResp;

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

    Page<ProjectAuditResp> pageByParam(int page, int limit);


}
