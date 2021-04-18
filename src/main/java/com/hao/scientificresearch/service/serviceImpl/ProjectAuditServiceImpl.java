package com.hao.scientificresearch.service.serviceImpl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hao.scientificresearch.entity.Project;
import com.hao.scientificresearch.entity.ProjectAudit;
import com.hao.scientificresearch.entity.Researcher;
import com.hao.scientificresearch.exception.ParamException;
import com.hao.scientificresearch.mapper.ProjectAuditMapper;
import com.hao.scientificresearch.model.param.AuditParam;
import com.hao.scientificresearch.service.IProjectAuditService;
import com.hao.scientificresearch.service.IProjectService;
import com.hao.scientificresearch.service.IResearcherService;
import com.hao.scientificresearch.utils.convert.AuditConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 项目审核表 服务实现类
 * </p>
 *
 * @author hao
 *
 */
@Service
public class ProjectAuditServiceImpl extends ServiceImpl<ProjectAuditMapper, ProjectAudit> implements IProjectAuditService {

    @Autowired
    IProjectService projectService;
    @Autowired
    IResearcherService researcherService;
    @Override
    public boolean audit(AuditParam param) {
        checkAuth(param);
        ProjectAudit audit = AuditConvert.change2entity(param);
        boolean b = this.save(audit);
        if(b){
            Project project = projectService.getOne(Wrappers.lambdaQuery(Project.class).eq(Project::getId, param.getProjectId()));
            if(project==null){
                throw new ParamException("审核无对应项目");
            }
            project.setState(param.getAuditState()+1);
//            project.setAudit(ProjectStateEnum.getName(param.getAuditState())+param.getAuditStatus());
//            project.setRemark(param.getRemark());
            return projectService.saveOrUpdate(project);
        }
        return false;
    }

    private void checkAuth(AuditParam param){
        if(ObjectUtil.isEmpty(param)){
            throw new ParamException("审核参数不能为空");
        }
        Researcher researcher = researcherService.getOne(Wrappers.lambdaQuery(Researcher.class).eq(Researcher::getId, param.getAuditorId()));
        if(ObjectUtil.isEmpty(researcher)){
            throw new ParamException("审核人未注册");
        }
        if(researcher.getRole().equals("user")){
            throw new ParamException("普通用户无权限");
        }
        if(param.getAuditState()==4){
            throw new ParamException("已结题不能审核");
        }
    }
}
