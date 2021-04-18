package com.hao.scientificresearch.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hao.scientificresearch.entity.Award;
import com.hao.scientificresearch.model.param.AwardParam;
import com.hao.scientificresearch.model.resp.AwardResp;

/**
 * <p>
 * 科研奖励表 服务类
 * </p>
 *
 * @author hao
 * @since 2021-03-31
 */
public interface IAwardService extends IService<Award> {


    Page<AwardResp> pageByParam(int page, int limit, String condition);

    boolean add(AwardParam param);

    boolean delete(Integer id);

    boolean update(AwardParam param);
}
