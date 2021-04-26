package com.hao.scientificresearch.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hao.scientificresearch.entity.Researcher;
import com.hao.scientificresearch.model.param.ResponseParam;
import com.hao.scientificresearch.model.param.UserInformationParam;
import com.hao.scientificresearch.model.resp.CollectInformationResp;
import com.hao.scientificresearch.model.resp.FieldResp;
import com.hao.scientificresearch.model.resp.UserInformationResp;
import com.hao.scientificresearch.service.ICollectInformationService;
import com.hao.scientificresearch.service.IResearcherService;
import com.hao.scientificresearch.utils.FieldListUtil;
import com.hao.scientificresearch.vo.DataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 信息收集表 前端控制器
 * </p>
 *
 * @author hao
 * @since 2021-04-24
 */
@Controller
@RequestMapping("/collect-information")
public class CollectInformationController {

    @Autowired
    private ICollectInformationService informationService;

    @GetMapping("/pageCollectInformation")
    @ResponseBody
    public DataVO<UserInformationResp>  pageCollectInformation(HttpSession session, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit){
        Page<UserInformationResp> respPage = informationService.pageByParam(session,page, limit);
        return DataVO.PageSuccess(respPage);
    }

    @PostMapping("/addCollectInfo")
    @ResponseBody
    public ResponseParam addCollectInfo(HttpSession session,@RequestParam String collectName,@RequestParam Integer informationType,@RequestParam String fieldStr){
        boolean b = informationService.add(session,collectName,informationType,fieldStr);
        if (b) {
            return new ResponseParam(1, "添加成功");
        }
        return new ResponseParam(2, "添加失败");
    }

    @PostMapping("/saveInfo")
    @ResponseBody
    public ResponseParam saveInfo(@RequestParam("informationType") Integer informationType,@RequestParam("infoStr") String infoStr,HttpSession session){
        Object loginUser = session.getAttribute("loginUser");
        String name = null;
        if(loginUser instanceof Researcher){
            name = ((Researcher) loginUser).getName();
        }
        boolean b = informationService.updateInfo(informationType, infoStr, name);
        if (b) {
            return new ResponseParam(1, "提交成功");
        }
        return new ResponseParam(2, "提交失败");
    }

    @GetMapping("/collectInfoByUsername")
    public String collectInfoByUsername(HttpSession session, Model model,Integer informationType){
        Object loginUser = session.getAttribute("loginUser");
        if(loginUser==null){
            return null;
        }
        if(loginUser instanceof Researcher){
            CollectInformationResp info = informationService.getInfoByUserName(((Researcher) loginUser).getName(),informationType);
            model.addAttribute("info",info);
            //向前端传递项目名
            model.addAttribute("projectName",info.getFieldName().get(0).getFieldName());
        }
        return "/page/more/info_write";
    }

    @GetMapping("/openInfoPage")
    public String openInfoPage(Model model){
        List<FieldResp> userList = FieldListUtil.userList;
        List<FieldResp> projectList = FieldListUtil.projectList;
        model.addAttribute("userList",userList);
        model.addAttribute("projectList",projectList);
        return "/page/more/information_collect";
    }

}

