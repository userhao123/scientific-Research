package com.hao.scientificresearch.model.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProjectAuditResp {

    private Integer id;
    private String auditorName,projectName,auditStatus,auditState,remark;
    private LocalDateTime auditTime;
}
