package com.hao.scientificresearch.model.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class ProjectFileResp {

    private Integer id;
    private String fileName,filePath,projectName,projectState;
    private LocalDateTime uploadTime;

}
