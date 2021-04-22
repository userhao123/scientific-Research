package com.hao.scientificresearch.service.serviceImpl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hao.scientificresearch.entity.Project;
import com.hao.scientificresearch.entity.ProjectResearcher;
import com.hao.scientificresearch.entity.Researcher;
import com.hao.scientificresearch.exception.ParamException;
import com.hao.scientificresearch.mapper.ProjectMapper;
import com.hao.scientificresearch.model.param.ProjectParam;
import com.hao.scientificresearch.model.param.ProjectSearchParam;
import com.hao.scientificresearch.model.resp.AddProjectFileResp;
import com.hao.scientificresearch.model.resp.ProjectResp;
import com.hao.scientificresearch.service.IProjectResearcherService;
import com.hao.scientificresearch.service.IProjectService;
import com.hao.scientificresearch.service.IResearcherService;
import com.hao.scientificresearch.utils.convert.ProjectConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 科研项目信息表 服务实现类
 * </p>
 *
 * @author hao
 * @since 2021-03-31
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements IProjectService {

    @Autowired
    private IResearcherService researcherService;
    @Autowired
    private IProjectResearcherService projectResearcherService;


    @Override
    public Page<ProjectResp> pageByParam(int page, int limit, String condition,Integer state) {
        ProjectSearchParam param = JSONUtil.toBean(condition, ProjectSearchParam.class);
        LambdaQueryWrapper<Project> wrapper = Wrappers.lambdaQuery(Project.class);
        if (ObjectUtil.isNotEmpty(param)) {
            wrapper.like(StringUtils.isNotBlank(param.getName()),Project::getName, param.getName())
                    .eq(StringUtils.isNotBlank(param.getNumber()),Project::getNumber,param.getNumber());
            if(StringUtils.isNotBlank(param.getLeadName())){
                Researcher one = researcherService.getOne(Wrappers.lambdaQuery(Researcher.class).eq(Researcher::getName, param.getLeadName()));
                if(one == null){
                    //全局异常处理中对查询无数据这字符串返回给前端不同的值
                    throw new ParamException("查询无数据");
                }
                wrapper.eq(Project::getLeaderId,one.getId());
            }
        }
        if(state!=null){
            wrapper.eq(Project::getState,state);
        }
        Page<Project> projectPage = this.page(new Page<>(page, limit), wrapper);
        List<Project> records = projectPage.getRecords();
        Page<ProjectResp> respPage = new Page<>(projectPage.getCurrent(), projectPage.getSize(), projectPage.getTotal());
        if (CollectionUtil.isNotEmpty(records)) {
            List<ProjectResp> resps = new ArrayList<>();
            Map<Integer, Researcher> map = researcherService.list().stream().collect(Collectors.toMap(Researcher::getId, r -> r));
            for (Project p : records) {
                ProjectResp resp = ProjectConvert.change2resp(p);
                resp.setLeaderName(map.get(resp.getLeaderId()).getName());
                resps.add(resp);
            }
            respPage.setRecords(resps);
        }
        return respPage;
    }

    @Override
    public AddProjectFileResp add(ProjectParam param) {
        List<Researcher> researcherList = researcherService.list(Wrappers.lambdaQuery(Researcher.class).eq(Researcher::getName, param.getLeaderName()));
        if (CollectionUtil.isEmpty(researcherList)) {
            throw new ParamException("该负责人未注册");
        }
        int count = this.count(Wrappers.lambdaQuery(Project.class).eq(Project::getName, param.getName()));
        if(count > 0){
            throw new ParamException("项目名已存在");
        }

        Project project = ProjectConvert.change2project(param);
        project.setLeaderId(researcherList.get(0).getId());
        project.setState(0);
        boolean b = this.save(project);
        if(b){
            //保存项目成员
            ProjectResearcher projectResearcher = new ProjectResearcher();
            projectResearcher.setProjectId(project.getId());
            projectResearcher.setResearcherId(project.getLeaderId());
            projectResearcherService.save(projectResearcher);

            return new AddProjectFileResp(1,"添加项目成功",project.getId(),project.getState());
        }
        return new AddProjectFileResp(2,"添加项目失败",null,null);
    }

    @Override
    public boolean delete(Integer id) {
        if (id == null) {
            throw new ParamException("id不能为空");
        }
        if (this.getById(id) == null) {
            throw new ParamException("删除项不存在");
        }
        boolean b = this.removeById(id);
        if(b){
            //将项目成员表对应项目的成员也删除
            return projectResearcherService.remove(Wrappers.lambdaQuery(ProjectResearcher.class).eq(ProjectResearcher::getProjectId, id));
        }else {
            return false;
        }

    }

    @Override
    public boolean update(Project param) {
        Project pro = this.getById(param.getId());
        if (!pro.getName().equals(param.getName())) {
            int count = this.count(Wrappers.lambdaQuery(Project.class).eq(Project::getName, param.getName()));
            if (count > 0) {
                throw new ParamException("项目名已存在");
            }
        }
        return this.updateById(param);
    }
}
