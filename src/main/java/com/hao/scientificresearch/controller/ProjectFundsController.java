package com.hao.scientificresearch.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hao.scientificresearch.entity.ProjectFunds;
import com.hao.scientificresearch.model.param.ResponseParam;
import com.hao.scientificresearch.model.resp.ProjectAuditResp;
import com.hao.scientificresearch.service.IProjectFundsService;
import com.hao.scientificresearch.vo.DataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 经费支付表 前端控制器
 * </p>
 *
 * @author hao
 * @since 2021-04-28
 */
@RestController
@RequestMapping("/project-funds")
public class ProjectFundsController {

    @Autowired
    private IProjectFundsService projectFundsService;

    @GetMapping("/page")
    public DataVO<ProjectFunds> page(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit){
        Page<ProjectFunds> respPage = projectFundsService.pageByParam(page, limit);
        return DataVO.PageSuccess(respPage);
    }

    @GetMapping("/delete")
    public ResponseParam delete(Integer id){
        boolean b = projectFundsService.delete(id);
        if (b) {
            return new ResponseParam(1, "删除成功");
        }
        return new ResponseParam(2, "删除失败");
    }

}

