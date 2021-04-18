package com.hao.scientificresearch.controller;


import com.hao.scientificresearch.model.param.AuditParam;
import com.hao.scientificresearch.model.param.ResponseParam;
import com.hao.scientificresearch.service.IProjectAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseParam audit(AuditParam param){
        boolean b = projectAuditService.audit(param);
        if (b) {
            return new ResponseParam(1, "审核成功");
        }
        return new ResponseParam(2, "审核失败");
    }

}

