package com.hao.scientificresearch.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hao.scientificresearch.entity.ProjectFunds;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hao.scientificresearch.model.resp.ProjectAuditResp;

/**
 * <p>
 * 经费支付表 服务类
 * </p>
 *
 * @author hao
 * @since 2021-04-28
 */
public interface IProjectFundsService extends IService<ProjectFunds> {

    Page<ProjectFunds> pageByParam(int page, int limit);

    boolean delete(Integer id);

}
