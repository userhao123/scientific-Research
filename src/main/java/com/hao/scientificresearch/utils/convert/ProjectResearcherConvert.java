package com.hao.scientificresearch.utils.convert;

import cn.hutool.json.JSONUtil;
import com.hao.scientificresearch.entity.ProjectResearcher;
import com.hao.scientificresearch.model.param.ProjectResearcherAddParam;
import com.hao.scientificresearch.model.resp.ProjectResearcherResp;

import java.util.List;

public class ProjectResearcherConvert {

    public static ProjectResearcherResp change2resp(Integer projectId, String projectName, List<Integer> ids,List<String> names){
        ProjectResearcherResp resp = new ProjectResearcherResp();
        resp.setProjectId(projectId);
        resp.setProjectName(projectName);
        resp.setResearcherIds(ids);
        resp.setResearcherName(JSONUtil.toJsonStr(names));
        return resp;
    }

}
