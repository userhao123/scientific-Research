package com.hao.scientificresearch.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hao.scientificresearch.entity.Project;
import com.hao.scientificresearch.model.param.ProjectParam;
import com.hao.scientificresearch.model.resp.AddProjectFileResp;
import com.hao.scientificresearch.model.resp.ProjectResp;

/**
 * <p>
 * 科研项目信息表 服务类
 * </p>
 *
 * @author hao
 * @since 2021-03-31
 */
public interface IProjectService extends IService<Project> {

    Page<ProjectResp> pageByParam(int page, int limit, String condition,Integer state);

    AddProjectFileResp add(ProjectParam param);

    boolean delete(Integer id);

    boolean update(Project param);


}
