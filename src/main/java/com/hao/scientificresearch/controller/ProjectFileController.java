package com.hao.scientificresearch.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hao.scientificresearch.aspect.OperationLogAnno;
import com.hao.scientificresearch.model.param.ResponseParam;
import com.hao.scientificresearch.model.resp.ProjectFileResp;
import com.hao.scientificresearch.service.IProjectFileService;
import com.hao.scientificresearch.vo.DataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 项目文件表 前端控制器
 * </p>
 *
 * @author hao
 * @since 2021-04-16
 */
@RestController
@RequestMapping("/project-file")
public class ProjectFileController {

    @Autowired
    private IProjectFileService projectFileService;

    @OperationLogAnno(desc = "上传文件")
    @PostMapping(value = "/upload" )
    public void upload(@RequestParam MultipartFile file,@RequestParam("projectId") Integer projectId,@RequestParam("projectState") Integer projectState){
        System.out.println("项目id"+projectId+"项目阶段:"+projectState);
        System.out.println("文件名:"+file.getOriginalFilename());
        projectFileService.add(projectId, projectState, file);

    }

    @GetMapping("/page")
    public DataVO<ProjectFileResp> page(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit, @RequestParam(required = false) String condition, @RequestParam(required = false) Integer state){
        Page<ProjectFileResp> respPage = projectFileService.pageByParam(page, limit, condition, state);
        return DataVO.PageSuccess(respPage);
    }

    @GetMapping("/delete")
    public ResponseParam delete(Integer id){
        boolean b = projectFileService.delete(id);
        if (b) {
            return new ResponseParam(1, "删除成功");
        }
        return new ResponseParam(2, "删除失败");
    }

    @PostMapping("/batchDelete")
    public ResponseParam batchDelete(@RequestBody List<ProjectFileResp> list){
        List<Integer> ids = list.stream().map(ProjectFileResp::getId).collect(Collectors.toList());
        boolean b = projectFileService.batchDelete(ids);
        if (b) {
            return new ResponseParam(1, "批量删除成功");
        }
        return new ResponseParam(2, "批量删除失败");
    }

}

