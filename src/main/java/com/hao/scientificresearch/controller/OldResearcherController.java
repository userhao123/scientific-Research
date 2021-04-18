package com.hao.scientificresearch.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hao.scientificresearch.entity.OldResearcher;
import com.hao.scientificresearch.model.param.ResponseParam;
import com.hao.scientificresearch.service.IOldResearcherService;
import com.hao.scientificresearch.vo.DataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 科研人员信息表 前端控制器
 * </p>
 *
 * @author hao
 * @since 2021-03-13
 */

@RestController
@RequestMapping("/oldResearcher")
public class OldResearcherController {

    @Autowired
    IOldResearcherService researcherService;

    /**
     * 不分页查询
     */
    @GetMapping("/list")
    public DataVO<OldResearcher> getList() {
        List<OldResearcher> list = researcherService.getList();
        if (list != null) {
            return DataVO.Success(list);
        }
        return DataVO.Fail("无数据");
    }

    /*
     * 分页查询
     */
    @RequestMapping("/page")
    public DataVO<OldResearcher> page(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "0") int limit, @RequestParam(defaultValue = "") String condition) {
        System.out.println("page" + page + "limit" + limit);
        System.out.println("条件:"+condition);
        Page<OldResearcher> result = researcherService.pageByParam(page, limit,condition);
        if (result != null) {
            return DataVO.PageSuccess(result);
        }
        return DataVO.Fail("数据为空");
    }

    @PostMapping("/add")
    public ResponseParam add(@RequestBody OldResearcher param){
        boolean b=researcherService.add(param);
        if(b){
            return new ResponseParam(1,"添加成功");
        }
        return new ResponseParam(2,"添加失败");
    }

    @GetMapping("/delete")
    public ResponseParam delete(@RequestParam Integer id){
        boolean b = researcherService.delete(id);
        if(b){
            return new ResponseParam(1,"删除成功");
        }
        return new ResponseParam(2,"删除失败");
    }

    @PostMapping("/update")
    public ResponseParam update(@RequestBody OldResearcher param){
        boolean b = researcherService.update(param);
        if(b){
            return new ResponseParam(1,"修改成功");
        }
        return new ResponseParam(2,"修改失败");
    }


}

