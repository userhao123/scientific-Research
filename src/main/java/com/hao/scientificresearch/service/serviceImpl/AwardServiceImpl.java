package com.hao.scientificresearch.service.serviceImpl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hao.scientificresearch.entity.Award;
import com.hao.scientificresearch.entity.Project;
import com.hao.scientificresearch.exception.ParamException;
import com.hao.scientificresearch.mapper.AwardMapper;
import com.hao.scientificresearch.model.param.AwardParam;
import com.hao.scientificresearch.model.param.AwardSearchParam;
import com.hao.scientificresearch.model.resp.AwardResp;
import com.hao.scientificresearch.service.IAwardService;
import com.hao.scientificresearch.service.IProjectService;
import com.hao.scientificresearch.utils.convert.AwardConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 科研奖励表 服务实现类
 * </p>
 *
 * @author hao
 * @since 2021-03-31
 */
@Service
public class AwardServiceImpl extends ServiceImpl<AwardMapper, Award> implements IAwardService {

    @Autowired
    private IProjectService projectService;
    @Override
    public Page<AwardResp> pageByParam(int page, int limit, String condition) {
        AwardSearchParam param = null;
        if(StringUtils.isNotBlank(condition)){
            param = JSONUtil.toBean(condition,AwardSearchParam.class);
        }
        LambdaQueryWrapper<Award> wrapper = Wrappers.lambdaQuery(Award.class);
        if(ObjectUtil.isNotEmpty(param)){
            assert param != null;
            wrapper.eq(StringUtils.isNotBlank(param.getName()),Award::getName,param.getName());
            wrapper.eq(StringUtils.isNotBlank(param.getAwardUnit()),Award::getAwardUnit,param.getAwardUnit());
            if(param.getProjectName()!=null&&!param.getProjectName().equals("")){
                Project one = projectService.getOne(Wrappers.lambdaQuery(Project.class).eq(Project::getName, param.getProjectName()));
                if(one==null){
                    throw new ParamException("查询无数据");
                }
                wrapper.eq(Award::getProjectId,one.getId());
            }

        }
        Page<Award> page1 = this.page(new Page<>(page, limit), wrapper);
        List<Award> records = page1.getRecords();
        Page<AwardResp> respPage = new Page<>(page1.getCurrent(), page1.getSize(), page1.getTotal());
        if (CollectionUtil.isNotEmpty(records)) {
            List<AwardResp> respList = new ArrayList<>();
            Map<Integer, Project> map = projectService.list().stream().collect(Collectors.toMap(Project::getId, p -> p));
            for(Award award:records){
                AwardResp awardResp = AwardConvert.change2resp(award);
                Project project = map.get(awardResp.getProjectId());
                if(project!=null){
                    awardResp.setProjectName(project.getName());
                }
                respList.add(awardResp);
            }
            respPage.setRecords(respList);
        }
        return respPage;

    }

    @Override
    public boolean add(AwardParam param) {
        Award award = AwardConvert.change2entity(param);
        Project project = projectService.getOne(Wrappers.lambdaQuery(Project.class).eq(Project::getName, param.getProjectName()));
        if(ObjectUtil.isEmpty(project)){
            throw new ParamException("该项目名不存在");
        }
        award.setProjectId(project.getId());
        return this.save(award);
    }

    @Override
    public boolean delete(Integer id) {
        if (id == null) {
            throw new ParamException("id不能为空");
        }
        if (this.getById(id) == null) {
            throw new ParamException("删除项不存在");
        }
        return this.removeById(id);
    }

    @Override
    public boolean update(AwardParam param) {
        Project project = projectService.getOne(Wrappers.lambdaQuery(Project.class).eq(Project::getName, param.getProjectName()));
        if(ObjectUtil.isEmpty(project)){
            throw new ParamException("该项目名不存在");
        }
        Award award = AwardConvert.change2entity(param);
        award.setProjectId(project.getId());
        return this.updateById(award);
    }
}
