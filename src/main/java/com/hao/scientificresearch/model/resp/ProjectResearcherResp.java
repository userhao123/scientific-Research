package com.hao.scientificresearch.model.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResearcherResp {

    private Integer projectId;
    private String projectName;
    private List<Integer> researcherIds;
    //项目成员名字符串
    private String researcherName;

}
