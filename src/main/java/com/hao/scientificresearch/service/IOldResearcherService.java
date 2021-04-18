package com.hao.scientificresearch.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hao.scientificresearch.entity.OldResearcher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 科研人员信息表 服务类
 * </p>
 *
 * @author hao
 * @since 2021-03-13
 */
public interface IOldResearcherService extends IService<OldResearcher> {

    List<OldResearcher> getList();

    Page<OldResearcher> pageByParam(int page, int limit, String condition);

    boolean add(OldResearcher param);

    boolean delete(Integer id);

    boolean update(OldResearcher param);
}
