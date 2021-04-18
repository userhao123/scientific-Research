package com.hao.scientificresearch.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hao.scientificresearch.aspect.OperationLogAnno;
import com.hao.scientificresearch.entity.Project;
import com.hao.scientificresearch.model.param.ProjectParam;
import com.hao.scientificresearch.model.param.ResponseParam;
import com.hao.scientificresearch.model.resp.AddProjectFileResp;
import com.hao.scientificresearch.model.resp.ProjectResp;
import com.hao.scientificresearch.service.IProjectService;
import com.hao.scientificresearch.vo.DataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 科研项目信息表 前端控制器
 * </p>
 *
 * @author hao
 * @since 2021-03-31
 */
@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private IProjectService projectService;

    @GetMapping("/page")
    public DataVO<ProjectResp> page(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit, @RequestParam(required = false) String condition,@RequestParam(required = false) Integer state) {
        Page<ProjectResp> respPage = projectService.pageByParam(page, limit, condition,state);
        if (respPage != null) {
            return DataVO.PageSuccess(respPage);
        }
        return DataVO.Fail("无数据");
    }

    @OperationLogAnno(desc = "添加项目")
    @PostMapping("/add")
    public AddProjectFileResp add(@RequestBody ProjectParam param) {
        return projectService.add(param);
    }

    @OperationLogAnno(desc = "删除项目")
    @GetMapping("/delete")
    public ResponseParam delete(@RequestParam Integer id) {
        boolean b = projectService.delete(id);
        if (b) {
            return new ResponseParam(1, "删除成功");
        }
        return new ResponseParam(2, "删除失败");
    }

    @OperationLogAnno(desc = "修改项目")
    @PostMapping("/update")
    public ResponseParam update(@RequestBody Project param) {
        boolean b = projectService.update(param);
        if (b) {
            return new ResponseParam(1, "修改成功");
        }
        return new ResponseParam(2, "修改失败");
    }


}

