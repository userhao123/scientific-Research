package com.hao.scientificresearch.service.serviceImpl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hao.scientificresearch.entity.Achievement;
import com.hao.scientificresearch.entity.Project;
import com.hao.scientificresearch.exception.ParamException;
import com.hao.scientificresearch.mapper.AchievementMapper;
import com.hao.scientificresearch.model.enums.AchievementKindEnum;
import com.hao.scientificresearch.model.param.AchievementParam;
import com.hao.scientificresearch.model.param.AchievementSearchParam;
import com.hao.scientificresearch.model.resp.AchievementResp;
import com.hao.scientificresearch.service.IAchievementService;
import com.hao.scientificresearch.service.IProjectService;
import com.hao.scientificresearch.utils.convert.AchievementConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 科研成果表 服务实现类
 * </p>
 *
 * @author hao
 * @since 2021-03-31
 */
@Service
public class AchievementServiceImpl extends ServiceImpl<AchievementMapper, Achievement> implements IAchievementService {
    @Autowired
    private IProjectService projectService;

    @Override
    public Page<AchievementResp> pageByParam(int page, int limit, String condition) {
        AchievementSearchParam param = null;
        if (condition != null && !condition.equals("")) {
            param = JSONUtil.toBean(condition, AchievementSearchParam.class);
        }
        LambdaQueryWrapper<Achievement> wrapper = Wrappers.lambdaQuery(Achievement.class);
        if (ObjectUtil.isNotEmpty(param)) {
            assert param != null;
            wrapper.eq(StringUtils.isNotBlank(param.getName()), Achievement::getName, param.getName());
            if (param.getProjectName() != null && !param.getProjectName().equals("")) {
                Project one = projectService.getOne(Wrappers.lambdaQuery(Project.class).eq(Project::getName, param.getProjectName()));
                if (one == null) {
                    throw new ParamException("查询无数据");
                }
                wrapper.eq(Achievement::getProjectId, one.getId());
            }
            if (param.getKind() != null && !param.getKind().equals(""))
                wrapper.eq(Achievement::getKind, AchievementKindEnum.getCode(param.getKind()));
        }
        Page<Achievement> page1 = this.page(new Page<>(page, limit), wrapper);
        List<Achievement> records = page1.getRecords();
        Page<AchievementResp> respPage = new Page<>(page1.getCurrent(), page1.getSize(), page1.getTotal());
        if (CollectionUtil.isNotEmpty(records)) {
            List<AchievementResp> respList = new ArrayList<>();
            Map<Integer, Project> map = projectService.list().stream().collect(Collectors.toMap(Project::getId, p -> p));
            for (Achievement achievement : records) {
                AchievementResp resp = AchievementConvert.change2resp(achievement);
                Project project = map.get(achievement.getProjectId());
                if (project != null) {
                    resp.setProjectName(project.getName());
                }
                respList.add(resp);
            }
            respPage.setRecords(respList);
        }
        return respPage;
    }

    @Override
    public boolean add(AchievementParam param) {
        Project project = projectService.getOne(Wrappers.lambdaQuery(Project.class).eq(Project::getName, param.getProjectName()));
        if (ObjectUtil.isEmpty(project)) {
            throw new ParamException("该项目名不存在");
        }
        Achievement achievement = AchievementConvert.change2entity(param);
        achievement.setProjectId(project.getId());
        return this.save(achievement);
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
    public boolean update(AchievementParam param) {
        Project project = projectService.getOne(Wrappers.lambdaQuery(Project.class).eq(Project::getName, param.getProjectName()));
        if (ObjectUtil.isEmpty(project)) {
            throw new ParamException("该项目名不存在");
        }
        Achievement achievement = AchievementConvert.change2entity(param);
        achievement.setProjectId(project.getId());
        return this.updateById(achievement);
    }
}
