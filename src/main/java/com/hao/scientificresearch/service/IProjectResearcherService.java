package com.hao.scientificresearch.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hao.scientificresearch.entity.ProjectResearcher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hao.scientificresearch.model.param.ProjectResearcherAddParam;
import com.hao.scientificresearch.model.resp.ProjectResearcherResp;
import com.hao.scientificresearch.model.resp.ProjectResp;

/**
 * <p>
 * 项目与参与人关系表（多对多） 服务类
 * </p>
 *
 * @author hao
 * @since 2021-03-31
 */
public interface IProjectResearcherService extends IService<ProjectResearcher> {

    Page<ProjectResearcherResp> pageByParam(int page, int limit, String condition);

    boolean add(ProjectResearcherAddParam param);

    boolean delete(ProjectResearcherAddParam param);
}
