package com.hao.scientificresearch.model.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EchartsCountResp {
    private Integer projectNum,researcherNum,awardNum,achievementNum;
}
