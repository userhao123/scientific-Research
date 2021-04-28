package com.hao.scientificresearch.service.serviceImpl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hao.scientificresearch.entity.ProjectFunds;
import com.hao.scientificresearch.mapper.ProjectFundsMapper;
import com.hao.scientificresearch.service.IProjectFundsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 经费支付表 服务实现类
 * </p>
 *
 * @author hao
 * @since 2021-04-28
 */
@Service
public class ProjectFundsServiceImpl extends ServiceImpl<ProjectFundsMapper, ProjectFunds> implements IProjectFundsService {

    @Override
    public Page<ProjectFunds> pageByParam(int page, int limit) {
        Page<ProjectFunds> page1 = this.page(new Page<>(page, limit));
        return page1;
    }

    @Override
    public boolean delete(Integer id) {
        return this.removeById(id);
    }
}
