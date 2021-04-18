package com.hao.scientificresearch.model.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class ResponseParam {

    private Integer code;
    private String msgs;
}
