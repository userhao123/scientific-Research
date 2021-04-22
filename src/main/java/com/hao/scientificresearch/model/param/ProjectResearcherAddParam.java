package com.hao.scientificresearch.model.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectResearcherAddParam {
    private Integer projectId;
    private String projectName;
    private String researcherName;
}
