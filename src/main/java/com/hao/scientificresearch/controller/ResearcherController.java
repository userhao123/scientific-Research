package com.hao.scientificresearch.controller;


import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hao.scientificresearch.aspect.OperationLogAnno;
import com.hao.scientificresearch.entity.Researcher;
import com.hao.scientificresearch.model.param.ResearcherSearchParam;
import com.hao.scientificresearch.model.param.ResetPwdParam;
import com.hao.scientificresearch.model.param.ResponseParam;
import com.hao.scientificresearch.model.resp.ResearchResp;
import com.hao.scientificresearch.service.IResearcherService;
import com.hao.scientificresearch.vo.DataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 科研人员信息表 前端控制器
 * </p>
 *
 * @author hao
 * @since 2021-03-31
 */
@RestController
@RequestMapping("/researcher")
public class ResearcherController {

    @Autowired
    private IResearcherService researcherService;

    /**
     * 不分页查询
     * @return DataVO
     */
    @GetMapping("/list")
    public DataVO<ResearchResp> list(){
        List<ResearchResp> list = researcherService.getList();
        if (list != null) {
            return DataVO.Success(list);
        }
        return DataVO.Fail("无数据");
    }

    /*
     * 分页查询
     */
    @RequestMapping("/page")
    public DataVO<ResearchResp> page(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit,@RequestParam(required = false) String condition) {
        ResearcherSearchParam param = JSONUtil.toBean(condition, ResearcherSearchParam.class);
        Page<ResearchResp> result = researcherService.pageByParam(page,limit,param);
        //实际上查询后返回result肯定不为空，可以不做判断
        if (result != null) {
            return DataVO.PageSuccess(result);
        }
        return DataVO.Fail("数据为空");
    }

    @OperationLogAnno(desc = "添加用户")
    @PostMapping("/add")
    public ResponseParam add(@RequestBody Researcher param){
        boolean b=researcherService.add(param);
        if(b){
            return new ResponseParam(1,"添加成功");
        }
        return new ResponseParam(2,"添加失败");
    }

    @OperationLogAnno(desc = "删除用户")
    @GetMapping("/delete")
    public ResponseParam delete(@RequestParam Integer id){
        boolean b = researcherService.delete(id);
        if(b){
            return new ResponseParam(1,"删除成功");
        }
        return new ResponseParam(2,"删除失败");
    }

    @OperationLogAnno(desc = "修改用户")
    @PostMapping("/update")
    public ResponseParam update(@RequestBody Researcher param){
        boolean b = researcherService.update(param);
        if(b){
            return new ResponseParam(1,"修改成功");
        }
        return new ResponseParam(2,"修改失败");
    }

    @PostMapping("/resetPwd")
    public ResponseParam resetPwd(@RequestBody ResetPwdParam param, HttpSession session){
        boolean b = researcherService.resetPwd(param, session);
        if(b){
            System.out.println("修改密码成功");
            return new ResponseParam(1,"修改密码成功");
        }
        return new ResponseParam(2,"修改密码失败");
    }


    //头像图片上传
    @PostMapping("/photoUpload")
    public ResponseParam photoUpload(HttpSession session,MultipartFile file){
        System.out.println("图片名:"+file.getOriginalFilename());
        boolean b = researcherService.savePhoto(session, file);
        if(b){
            return new ResponseParam(1,"上传成功");
        }
        return new ResponseParam(2,"上传成功");
    }


}

