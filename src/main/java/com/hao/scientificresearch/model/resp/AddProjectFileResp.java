package com.hao.scientificresearch.model.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

// 添加项目时返回给前端的返回对象
@Data
@EqualsAndHashCode
@AllArgsConstructor
public class AddProjectFileResp {

    private int code;
    private String msgs;
    private Integer projectId;
    private Integer projectState;
}
