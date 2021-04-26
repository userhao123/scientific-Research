package com.hao.scientificresearch.utils.convert;

import cn.hutool.core.collection.CollectionUtil;
import com.hao.scientificresearch.entity.Project;
import com.hao.scientificresearch.model.enums.ProjectCategoryEnum;
import com.hao.scientificresearch.model.enums.ProjectLevelEnum;
import com.hao.scientificresearch.model.enums.ProjectStateEnum;
import com.hao.scientificresearch.model.param.ProjectParam;
import com.hao.scientificresearch.model.resp.ProjectResp;
import com.hao.scientificresearch.utils.GenNumber;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProjectConvert {

    public static ProjectResp change2resp(Project entity){
        ProjectResp projectResp = null;
        if(entity!=null){
            projectResp = new ProjectResp();
            projectResp.setAudit(entity.getAudit());
            projectResp.setCategory(ProjectCategoryEnum.getName(entity.getCategory()));
            projectResp.setCreateTime(entity.getCreateTime());
            projectResp.setDescribed(entity.getDescribed());
            projectResp.setFinishTime(entity.getFinishTime());
            projectResp.setId(entity.getId());
            projectResp.setLeaderId(entity.getLeaderId());
            projectResp.setLevel(ProjectLevelEnum.getName(entity.getLevel()));
            projectResp.setName(entity.getName());
            projectResp.setNumber(entity.getNumber());
            projectResp.setRemark(entity.getRemark());
            projectResp.setState(ProjectStateEnum.getName(entity.getState()));
        }
        return projectResp;
    }

    public static Project change2project(ProjectParam entity){
        Project project = null;
        if(entity!=null){
            project = new Project();
            project.setCategory(entity.getCategory());
            project.setDescribed(entity.getDescribed());
            project.setLevel(entity.getLevel());
            project.setName(entity.getName());
            project.setCreateTime(LocalDateTime.now());
            project.setNumber(GenNumber.genNum("pro"));
        }
        return project;
    }

    public static List<ProjectResp> change2list(List<Project> entity){
        List<ProjectResp> list = null;
        if(!CollectionUtil.isEmpty(entity)){
            list = new ArrayList<>();
            for(Project r:entity){
                list.add(change2resp(r));
            }
        }
        return list;
    }
}
