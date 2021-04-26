package com.hao.scientificresearch.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hao.scientificresearch.model.param.AuditParam;
import com.hao.scientificresearch.model.param.ResponseParam;
import com.hao.scientificresearch.model.resp.ProjectAuditResp;
import com.hao.scientificresearch.model.resp.ProjectFileResp;
import com.hao.scientificresearch.service.IProjectAuditService;
import com.hao.scientificresearch.vo.DataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * <p>
 * 项目审核表 前端控制器
 * </p>
 *
 * @author hao
 * @since 2021-04-05
 */
@RestController
@RequestMapping("/project-audit")
public class ProjectAuditController {

    @Autowired
    private IProjectAuditService projectAuditService;

    @PostMapping("/audit")
    public ResponseParam audit(@RequestBody AuditParam param, HttpSession session){
        System.out.println("审核参数:"+param);
        boolean b = projectAuditService.audit(param,session);
        if (b) {
            return new ResponseParam(1, "审核成功");
        }
        return new ResponseParam(2, "审核失败");
    }

    @GetMapping("/page")
    public DataVO<ProjectAuditResp> page(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit){
        Page<ProjectAuditResp> respPage = projectAuditService.pageByParam(page, limit);
        return DataVO.PageSuccess(respPage);
    }

}

