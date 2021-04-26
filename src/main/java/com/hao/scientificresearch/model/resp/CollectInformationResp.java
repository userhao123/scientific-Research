package com.hao.scientificresearch.model.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectInformationResp {
    private Integer informationType;
    private List<FieldResp> fieldName;
}
