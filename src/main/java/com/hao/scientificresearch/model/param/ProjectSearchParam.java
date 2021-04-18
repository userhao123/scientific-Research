package com.hao.scientificresearch.model.param;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ProjectSearchParam {

    private String name;
    private String leadName;
    private String number;
}
