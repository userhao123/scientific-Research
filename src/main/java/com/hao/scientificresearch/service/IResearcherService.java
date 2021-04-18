package com.hao.scientificresearch.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hao.scientificresearch.entity.Researcher;
import com.hao.scientificresearch.model.param.LoginParam;
import com.hao.scientificresearch.model.param.ResearcherSearchParam;
import com.hao.scientificresearch.model.resp.ResearchResp;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 科研人员信息表 服务类
 * </p>
 *
 * @author hao
 * @since 2021-03-31
 */
public interface IResearcherService extends IService<Researcher> {

    List<ResearchResp> getList();

    Page<ResearchResp> pageByParam(int page, int limit, ResearcherSearchParam condition);

    boolean add(Researcher param);

    boolean delete(Integer id);

    boolean update(Researcher param);

    boolean login(LoginParam param,HttpSession session);
}
