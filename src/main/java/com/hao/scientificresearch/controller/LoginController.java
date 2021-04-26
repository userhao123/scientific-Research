package com.hao.scientificresearch.controller;

import com.hao.scientificresearch.aspect.LoginLogAnno;
import com.hao.scientificresearch.model.param.LoginParam;
import com.hao.scientificresearch.model.param.ResponseParam;
import com.hao.scientificresearch.service.IAdministratorService;
import com.hao.scientificresearch.service.IResearcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/logout")
    public ResponseParam logout(HttpSession session){
        if(session!=null){
            session.removeAttribute("userLogin");
        }
        return new ResponseParam(1,"退出登录");
    }

}
