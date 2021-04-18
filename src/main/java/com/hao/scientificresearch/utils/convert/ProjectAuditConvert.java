package com.hao.scientificresearch.utils.convert;

import com.hao.scientificresearch.entity.ProjectAudit;
import com.hao.scientificresearch.model.enums.ProjectStateEnum;
import com.hao.scientificresearch.model.resp.ProjectAuditResp;

public class ProjectAuditConvert {

    public static ProjectAuditResp change2Resp(ProjectAudit entity){
        ProjectAuditResp resp = null;
        if(entity!=null){
            resp = new ProjectAuditResp();
            resp.setAuditState(ProjectStateEnum.getName(entity.getAuditState()));
            resp.setAuditStatus(entity.getAuditStatus());
            resp.setId(entity.getId());
            resp.setRemark(entity.getRemark());
            resp.setAuditTime(entity.getAuditTime());
        }
        return resp;
    }
}
