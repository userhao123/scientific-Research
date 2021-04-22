package com.hao.scientificresearch.service.serviceImpl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.hao.scientificresearch.entity.Project;
import com.hao.scientificresearch.entity.ProjectResearcher;
import com.hao.scientificresearch.entity.Researcher;
import com.hao.scientificresearch.exception.ParamException;
import com.hao.scientificresearch.mapper.ProjectResearcherMapper;
import com.hao.scientificresearch.model.param.ProjectResearchSerarchParam;
import com.hao.scientificresearch.model.param.ProjectResearcherAddParam;
import com.hao.scientificresearch.model.resp.ProjectResearcherResp;
import com.hao.scientificresearch.model.resp.ProjectResp;
import com.hao.scientificresearch.service.IProjectResearcherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hao.scientificresearch.service.IProjectService;
import com.hao.scientificresearch.service.IResearcherService;
import com.hao.scientificresearch.utils.convert.ProjectResearcherConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 项目与参与人关系表（多对多） 服务实现类
 * </p>
 *
 * @author hao
 * @since 2021-03-31
 */
@Service
public class ProjectResearcherServiceImpl extends ServiceImpl<ProjectResearcherMapper, ProjectResearcher> implements IProjectResearcherService {

    @Autowired
    private IProjectService projectService;
    @Autowired
    private IResearcherService researcherService;

    @Override
    public Page<ProjectResearcherResp> pageByParam(int page, int limit, String condition) {
        LambdaQueryWrapper<ProjectResearcher> prWrapper = Wrappers.lambdaQuery(ProjectResearcher.class);
        LambdaQueryWrapper<Project> pWrapper = Wrappers.lambdaQuery(Project.class);
        LambdaQueryWrapper<Researcher> rWrapper = Wrappers.lambdaQuery(Researcher.class);
        if (StringUtils.isNotBlank(condition)) {
            ProjectResearchSerarchParam param = JSONUtil.toBean(condition, ProjectResearchSerarchParam.class);
            System.out.println("接收的搜索参数:" + param);
            if (StringUtils.isNotBlank(param.getProjectName())) {
                Project project = projectService.getOne(pWrapper.eq(Project::getName, param.getProjectName()));
                if (project == null) {
                    //全局异常处理中对查询无数据这字符串返回给前端不同的值
                    throw new ParamException("查询无数据");
                }
                prWrapper.eq(ProjectResearcher::getProjectId, project.getId());
            }
            if (StringUtils.isNotBlank(param.getResearcherName())) {
                List<Researcher> researchers = researcherService.list(rWrapper.eq(Researcher::getName, param.getResearcherName()));
                if (CollectionUtil.isEmpty(researchers)) {
                    //全局异常处理中对查询无数据这字符串返回给前端不同的值
                    throw new ParamException("查询无数据");
                }
                List<Integer> researcherIds = researchers.stream().map(Researcher::getId).collect(Collectors.toList());
                List<ProjectResearcher> projectResearcherList = this.list(Wrappers.lambdaQuery(ProjectResearcher.class).in(ProjectResearcher::getResearcherId, researcherIds));
                List<Integer> collect = projectResearcherList.stream().map(ProjectResearcher::getProjectId).collect(Collectors.toList());
                prWrapper.in(ProjectResearcher::getProjectId, collect);
            }
        }

        Page<ProjectResearcher> page1 = this.page(new Page<>(page, limit), prWrapper);
        List<ProjectResearcher> records = page1.getRecords();
        //组装返回对象
        Page<ProjectResearcherResp> respPage = new Page<>(page1.getCurrent(), page1.getSize());
        if (CollectionUtil.isNotEmpty(records)) {
            Map<Integer, String> researcherMap = researcherService.list().stream().collect(Collectors.toMap(Researcher::getId, Researcher::getName));
            Map<Integer, String> projectMap = projectService.list().stream().collect(Collectors.toMap(Project::getId, Project::getName));
            //以项目id分组
            Map<Integer, List<ProjectResearcher>> listMap = records.stream().collect(Collectors.groupingBy(ProjectResearcher::getProjectId));

            List<Integer> researcherIds;
            List<String> researcherNames = new ArrayList<>();
            ProjectResearcherResp resp;
            List<ProjectResearcherResp> resps = new ArrayList<>();
            for (Map.Entry<Integer, List<ProjectResearcher>> entry : listMap.entrySet()) {

                researcherIds = entry.getValue().stream().map(ProjectResearcher::getResearcherId).collect(Collectors.toList());
                for (Integer id : researcherIds) {
                    researcherNames.add(researcherMap.get(id));
                }
                resp = ProjectResearcherConvert.change2resp(entry.getKey(), projectMap.get(entry.getKey()), researcherIds, researcherNames);
                resps.add(resp);
                //组装完一条返回数据对象后，清空成员名数组
                researcherNames.clear();
            }
            respPage.setTotal(resps.size());
            respPage.setRecords(resps);
        }
        return respPage;
    }

    @Override
    public boolean add(ProjectResearcherAddParam param) {
        if (ObjectUtil.isEmpty(param)) {
            throw new ParamException("添加参数不能为空");
        }
        List<Researcher> researchers = researcherService.list(Wrappers.lambdaQuery(Researcher.class).eq(Researcher::getName, param.getResearcherName()));
        if (CollectionUtil.isEmpty(researchers)) {
            throw new ParamException("该成员未注册");
        }
        int count = this.count(Wrappers.lambdaQuery(ProjectResearcher.class).eq(ProjectResearcher::getProjectId, param.getProjectId())
                .eq(ProjectResearcher::getResearcherId, researchers.get(0).getId()));
        if (count > 0) {
            throw new ParamException("成员已加入项目");
        }

        ProjectResearcher projectResearcher = new ProjectResearcher();
        projectResearcher.setResearcherId(researchers.get(0).getId());
        projectResearcher.setProjectId(param.getProjectId());
        return this.save(projectResearcher);
    }

    @Override
    public boolean delete(ProjectResearcherAddParam param) {
        if(ObjectUtil.isEmpty(param)){
            throw new ParamException("参数不能为空");
        }
        List<Researcher> researchers = researcherService.list(Wrappers.lambdaQuery(Researcher.class).eq(Researcher::getName, param.getResearcherName()));
        if(CollectionUtil.isEmpty(researchers)){
            throw new ParamException("用户未注册");
        }
        ProjectResearcher projectResearcher = this.getOne(Wrappers.lambdaQuery(ProjectResearcher.class).eq(ProjectResearcher::getProjectId, param.getProjectId())
                .eq(ProjectResearcher::getResearcherId, researchers.get(0).getId()));
        if(projectResearcher == null){
            throw new ParamException("该用户未加入项目中");
        }
        return this.removeById(projectResearcher.getId());
    }
}
