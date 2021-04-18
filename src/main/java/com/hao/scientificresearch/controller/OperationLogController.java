package com.hao.scientificresearch.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hao.scientificresearch.aspect.OperationLogAnno;
import com.hao.scientificresearch.entity.OperationLog;
import com.hao.scientificresearch.model.param.ResponseParam;
import com.hao.scientificresearch.service.IOperationLogService;
import com.hao.scientificresearch.vo.DataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 登录日志表 前端控制器
 * </p>
 *
 * @author hao
 * @since 2021-04-11
 */
@RestController
@RequestMapping("/operation-log")
public class OperationLogController {

    @Autowired
    private IOperationLogService operationLogService;

    @GetMapping("/page")
    public DataVO<OperationLog> pageByParam(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "") String condition){
        Page<OperationLog> logPage = operationLogService.pageByParam(page, limit, condition);
        return DataVO.PageSuccess(logPage);
    }

    @OperationLogAnno(desc = "删除操作日志")
    @GetMapping("/delete")
    public ResponseParam delete(@RequestParam("id") Integer id){
        boolean b = operationLogService.delete(id);
        if(b){
            return new ResponseParam(1,"删除成功");
        }else {
            return new ResponseParam(2,"删除失败");
        }
    }

}

