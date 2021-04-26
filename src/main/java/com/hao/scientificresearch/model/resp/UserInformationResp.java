package com.hao.scientificresearch.model.resp;

import com.hao.scientificresearch.model.param.UserInformationParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserInformationResp extends UserInformationParam {
    private String name;
}
