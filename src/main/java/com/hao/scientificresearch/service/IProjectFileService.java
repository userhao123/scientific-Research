package com.hao.scientificresearch.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hao.scientificresearch.entity.ProjectFile;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hao.scientificresearch.model.resp.ProjectFileResp;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 项目文件表 服务类
 * </p>
 *
 * @author hao
 * @since 2021-04-16
 */
public interface IProjectFileService extends IService<ProjectFile> {

    boolean add(Integer projectId, Integer projectState, MultipartFile file);

    Page<ProjectFileResp> pageByParam(int page, int limit, String condition, Integer state);

    boolean delete(Integer id);

    boolean batchDelete(List<Integer> ids);


}
