package com.hao.scientificresearch.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hao.scientificresearch.entity.Achievement;
import com.hao.scientificresearch.model.param.AchievementParam;
import com.hao.scientificresearch.model.resp.AchievementResp;

/**
 * <p>
 * 科研成果表 服务类
 * </p>
 *
 * @author hao
 * @since 2021-03-31
 */
public interface IAchievementService extends IService<Achievement> {

    Page<AchievementResp> pageByParam(int page, int limit, String condition);

    boolean add(AchievementParam param);

    boolean delete(Integer id);

    boolean update(AchievementParam param);

}
