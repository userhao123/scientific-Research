package com.hao.scientificresearch.controller;

import com.hao.scientificresearch.aspect.LoginLogAnno;
import com.hao.scientificresearch.model.param.LoginParam;
import com.hao.scientificresearch.model.param.ResponseParam;
import com.hao.scientificresearch.service.IResearcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/userLogin")
public class LoginController {
    @Autowired
    private IResearcherService researcherService;

    @LoginLogAnno(desc = "登录")
    @PostMapping("/login")
    public ResponseParam login(@RequestBody LoginParam param, HttpSession session){
        boolean b = researcherService.login(param,session);
        if(b){
            return new ResponseParam(1,"登录成功");
        }
        return new ResponseParam(2,"登录失败");
    }

}
