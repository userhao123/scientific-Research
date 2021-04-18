package com.hao.scientificresearch.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hao.scientificresearch.aspect.OperationLogAnno;
import com.hao.scientificresearch.model.param.AchievementParam;
import com.hao.scientificresearch.model.param.ResponseParam;
import com.hao.scientificresearch.model.resp.AchievementResp;
import com.hao.scientificresearch.service.IAchievementService;
import com.hao.scientificresearch.vo.DataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 科研成果表 前端控制器
 * </p>
 *
 * @author hao
 * @since 2021-03-31
 */
@RestController
@RequestMapping("/achievement")
public class AchievementController {

    @Autowired
    IAchievementService achievementService;

    @GetMapping("/page")
    public DataVO<AchievementResp> page(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "") String condition) {
        Page<AchievementResp> respPage = achievementService.pageByParam(page, limit, condition);
        if (respPage != null) {
            return DataVO.PageSuccess(respPage);
        }
        return DataVO.Fail("查询数据无数据");
    }

    @OperationLogAnno(desc = "添加成果")
    @PostMapping("/add")
    public ResponseParam add(@RequestBody AchievementParam param) {
        boolean b = achievementService.add(param);
        if (b) {
            return new ResponseParam(1, "添加成功");
        }
        return new ResponseParam(2, "添加失败");
    }

    @OperationLogAnno(desc = "删除成果")
    @GetMapping("/delete")
    public ResponseParam delete(@RequestParam Integer id) {
        boolean b = achievementService.delete(id);
        if (b) {
            return new ResponseParam(1, "删除成功");
        }
        return new ResponseParam(2, "删除失败");
    }

    @OperationLogAnno(desc = "修改成果")
    @PostMapping("/update")
    public ResponseParam update(@RequestBody AchievementParam param) {
        System.out.println("参数:"+param.toString());
        boolean b = achievementService.update(param);
        if (b) {
            return new ResponseParam(1, "修改成功");
        }
        return new ResponseParam(2, "修改失败");
    }

}

