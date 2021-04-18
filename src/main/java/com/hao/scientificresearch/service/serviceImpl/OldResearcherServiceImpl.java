package com.hao.scientificresearch.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hao.scientificresearch.entity.OldResearcher;
import com.hao.scientificresearch.exception.ParamException;
import com.hao.scientificresearch.mapper.OldResearcherMapper;
import com.hao.scientificresearch.service.IOldResearcherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 科研人员信息表 服务实现类
 * </p>
 *
 * @author hao
 * @since 2021-03-13
 */
@Service
public class OldResearcherServiceImpl extends ServiceImpl<OldResearcherMapper, OldResearcher> implements IOldResearcherService {

    @Override
    public List<OldResearcher> getList() {
        return this.list();
    }

    @Override
    public Page<OldResearcher> pageByParam(int page, int limit, String condition) {
        LambdaQueryWrapper<OldResearcher> wrapper = Wrappers.lambdaQuery(OldResearcher.class);
        if(!StringUtils.isBlank(condition)){
            wrapper.eq(OldResearcher::getEducation, condition).or().eq(OldResearcher::getMajor, condition).or().eq(OldResearcher::getName, condition)
                    .or().eq(OldResearcher::getTitle, condition);
        }
        wrapper.orderByDesc(OldResearcher::getId);
        return this.page(new Page<>(page, limit), wrapper);
    }

    @Override
    public boolean add(OldResearcher param) {
        boolean b = false;
        if (param != null) {
            b = this.save(param);
        }
        return b;
    }

    @Override
    public boolean delete(Integer id) {
        if (id == null) {
            throw new ParamException("无此数据");
        }
        return this.removeById(id);
    }

    @Override
    public boolean update(OldResearcher param) {
        if (param == null) {
            throw new ParamException("参数不能为空");
        }
        return this.updateById(param);
    }
}
