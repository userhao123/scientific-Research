package com.hao.scientificresearch.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hao.scientificresearch.entity.ProjectResearcher;
import com.hao.scientificresearch.model.param.ProjectResearcherAddParam;
import com.hao.scientificresearch.model.param.ResponseParam;
import com.hao.scientificresearch.model.resp.ProjectResearcherResp;
import com.hao.scientificresearch.service.IProjectResearcherService;
import com.hao.scientificresearch.vo.DataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 项目与参与人关系表（多对多） 前端控制器
 * </p>
 *
 * @author hao
 * @since 2021-03-31
 */
@RestController
@RequestMapping("/project-researcher")
public class ProjectResearcherController {

    @Autowired
    private IProjectResearcherService projectResearcherService;

    @GetMapping("/page")
    public DataVO<ProjectResearcherResp> page(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit, @RequestParam(required = false) String condition){
        Page<ProjectResearcherResp> respPage = projectResearcherService.pageByParam(page, limit, condition);
        return DataVO.PageSuccess(respPage);
    }

    @PostMapping("/add")
    public ResponseParam add(@RequestBody ProjectResearcherAddParam param){
        boolean b = projectResearcherService.add(param);
        if (b) {
            return new ResponseParam(1, "添加成功");
        }
        return new ResponseParam(2, "添加失败");
    }

    @PostMapping("/delete")
    public ResponseParam delete(@RequestBody ProjectResearcherAddParam param){
        boolean b = projectResearcherService.delete(param);
        if (b) {
            return new ResponseParam(1, "删除成功");
        }
        return new ResponseParam(2, "删除失败");
    }


}

