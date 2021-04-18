package com.hao.scientificresearch.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hao.scientificresearch.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 项目文件表
 * </p>
 *
 * @author hao
 * @since 2021-04-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("b_project_file")
public class ProjectFile extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 项目id
     */
    private Integer projectId;

    /**
     * 项目所处阶段#0新建1立项2中期检查3结题4延期
     */
    private Integer projectState;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 上传时间
     */
    private LocalDateTime uploadTime;


}
