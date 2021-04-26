package com.hao.scientificresearch.utils.convert;

import com.hao.scientificresearch.entity.ProjectAudit;
import com.hao.scientificresearch.model.enums.ProjectStateEnum;
import com.hao.scientificresearch.model.param.AuditParam;

import java.time.LocalDateTime;

public class AuditConvert {


    public static ProjectAudit change2entity(AuditParam param){
        ProjectAudit projectAudit = null;
        if(param!=null){
            projectAudit=new ProjectAudit();
            projectAudit.setAuditorId(param.getAuditorId());
            projectAudit.setAuditState(ProjectStateEnum.getCode(param.getAuditState()));
            projectAudit.setAuditTime(LocalDateTime.now());
            projectAudit.setAuditStatus(param.getAuditStatus());
            projectAudit.setRemark(param.getRemark());
            projectAudit.setProjectId(param.getProjectId());
        }
        return projectAudit;
    }
}
