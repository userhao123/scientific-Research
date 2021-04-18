package com.hao.scientificresearch.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hao.scientificresearch.aspect.OperationLogAnno;
import com.hao.scientificresearch.model.param.AwardParam;
import com.hao.scientificresearch.model.param.ResponseParam;
import com.hao.scientificresearch.model.resp.AwardResp;
import com.hao.scientificresearch.service.IAwardService;
import com.hao.scientificresearch.vo.DataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 科研奖励表 前端控制器
 * </p>
 *
 * @author hao
 * @since 2021-03-31
 */
@RestController
@RequestMapping("/award")
public class AwardController {

    @Autowired
    IAwardService AwardService;

    @GetMapping("/page")
    public DataVO<AwardResp> page(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "") String condition) {
        Page<AwardResp> respPage = AwardService.pageByParam(page, limit, condition);
        if (respPage != null) {
            return DataVO.PageSuccess(respPage);
        }
        return DataVO.Fail("无数据");
    }

    @OperationLogAnno(desc = "添加奖项")
    @PostMapping("/add")
    public ResponseParam add(@RequestBody AwardParam param) {
        boolean b = AwardService.add(param);
        if (b) {
            return new ResponseParam(1, "添加成功");
        }
        return new ResponseParam(2, "添加失败");
    }

    @OperationLogAnno(desc = "删除奖项")
    @GetMapping("/delete")
    public ResponseParam delete(@RequestParam Integer id) {
        boolean b = AwardService.delete(id);
        if (b) {
            return new ResponseParam(1, "删除成功");
        }
        return new ResponseParam(2, "删除失败");
    }

    @OperationLogAnno(desc = "修改奖项")
    @PostMapping("/update")
    public ResponseParam update(@RequestBody AwardParam param) {
        System.out.println("id参数:"+param.toString());
        boolean b = AwardService.update(param);
        if (b) {
            return new ResponseParam(1, "修改成功");
        }
        return new ResponseParam(2, "修改失败");
    }

}

