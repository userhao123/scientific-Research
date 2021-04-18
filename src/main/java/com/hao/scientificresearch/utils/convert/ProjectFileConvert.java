package com.hao.scientificresearch.utils.convert;

import com.hao.scientificresearch.entity.ProjectFile;
import com.hao.scientificresearch.model.enums.ProjectStateEnum;
import com.hao.scientificresearch.model.resp.ProjectFileResp;

public class ProjectFileConvert {

    public static ProjectFileResp change2Resp(ProjectFile entity){
        ProjectFileResp resp = null;
        if(entity!=null){
            resp = new ProjectFileResp();
            resp.setProjectState(ProjectStateEnum.getName(entity.getProjectState()));
            resp.setFileName(entity.getFileName());
            resp.setFilePath(entity.getFilePath());
            resp.setId(entity.getId());
            resp.setUploadTime(entity.getUploadTime());
        }
        return resp;
    }
}
