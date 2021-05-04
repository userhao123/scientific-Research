package com.hao.scientificresearch.service.serviceImpl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hao.scientificresearch.entity.Project;
import com.hao.scientificresearch.entity.ProjectAudit;
import com.hao.scientificresearch.entity.ProjectResearcher;
import com.hao.scientificresearch.entity.Researcher;
import com.hao.scientificresearch.exception.ParamException;
import com.hao.scientificresearch.mapper.ProjectAuditMapper;
import com.hao.scientificresearch.model.enums.ProjectStateEnum;
import com.hao.scientificresearch.model.param.AuditParam;
import com.hao.scientificresearch.model.param.MailParam;
import com.hao.scientificresearch.model.resp.ProjectAuditResp;
import com.hao.scientificresearch.model.resp.ProjectResp;
import com.hao.scientificresearch.service.*;
import com.hao.scientificresearch.utils.convert.AuditConvert;
import com.hao.scientificresearch.utils.convert.ProjectAuditConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Autowired
    IProjectResearcherService projectResearcherService;
    @Autowired
    private IMailService mailService;
    @Override
    public boolean audit(AuditParam param, HttpSession session) {
        if(param==null){
            throw new ParamException("参数不能为空");
        }
        Object loginUser = session.getAttribute("loginUser");
        if(loginUser instanceof Researcher){
            param.setAuditorId(((Researcher) loginUser).getId());
        }
        checkAuth(param);
        ProjectAudit audit = AuditConvert.change2entity(param);
        boolean b = this.save(audit);
        if(b){
            Project project = projectService.getOne(Wrappers.lambdaQuery(Project.class).eq(Project::getId, param.getProjectId()));
            if(project==null){
                throw new ParamException("审核无对应项目");
            }
            //修改项目阶段和状态
            Integer code = ProjectStateEnum.getCode(param.getAuditState());
            if(code == null){
                throw new ParamException("项目阶段异常");
            }
            //project.setRemark(param.getRemark());
            project.setState(code+1);
            project.setAudit(param.getAuditState()+param.getAuditStatus());
            boolean b1 = projectService.updateById(project);
            if(b1){
                //发送邮件通知项目成员审核通过
                List<ProjectResearcher> list = projectResearcherService.list(Wrappers.lambdaQuery(ProjectResearcher.class).eq(ProjectResearcher::getProjectId, project.getId()));
                List<Integer> researcherIds = list.stream().map(ProjectResearcher::getResearcherId).collect(Collectors.toList());
                Map<Integer, Researcher> researcherMap = researcherService.list().stream().collect(Collectors.toMap(Researcher::getId, r -> r));
                int size = researcherIds.size();
                //组装收件邮箱
                String[] toStr = new String[size];
                for(int i = 0; i< size; i++){
                    toStr[i] = researcherMap.get(researcherIds.get(i)).getEmail();
                }
                boolean mail = MailServiceImpl.checkMail(toStr);
                if(!mail){
                    throw new ParamException("审核操作成功，但用户邮箱格式错误，不能发送邮件");
                }
                MailParam mailParam = createMailParam("3084921312@qq.com", toStr, "项目审核通知","你参与的项目:"+project.getName()+"审核通过了");
                mailService.sendMail(mailParam);
            }
            return b1;
        }
        return false;
    }

    @Override
    public Page<ProjectAuditResp> pageByParam(int page, int limit) {
        Page<ProjectAudit> page1 = this.page(new Page<>(page, limit));
        List<ProjectAudit> records = page1.getRecords();

        //组装返回对象
        Page<ProjectAuditResp> respPage = new Page<>(page1.getCurrent(), page1.getSize(), page1.getTotal());
        List<ProjectAuditResp> resps = new ArrayList<>();

        if(CollectionUtil.isNotEmpty(records)){
            //查询审核人
            List<Researcher> researcherList = researcherService.list();
            Map<Integer, Researcher> researcherMap = researcherList.stream().collect(Collectors.toMap(Researcher::getId, r -> r));
            //查询项目
            List<Project> projectList = projectService.list();
            Map<Integer, Project> projectMap = projectList.stream().collect(Collectors.toMap(Project::getId, p -> p));
            for(ProjectAudit p:records){
                ProjectAuditResp projectAuditResp = ProjectAuditConvert.change2Resp(p);
                projectAuditResp.setAuditorName(researcherMap.get(p.getAuditorId()).getName());
                projectAuditResp.setProjectName(projectMap.get(p.getProjectId()).getName());
                resps.add(projectAuditResp);
            }
            respPage.setRecords(resps);

        }
        return respPage;
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
        if(param.getAuditState().equals("结题")){
            throw new ParamException("已结题不能审核");
        }
    }

    public MailParam createMailParam(String from,String[] to,String subject,String text){
        MailParam mailParam = new MailParam();
        mailParam.setFrom(from);
        mailParam.setTo(to);
        mailParam.setSubject(subject);
        mailParam.setText(text);
        mailParam.setDate(new Date());
        return mailParam;
    }

}
