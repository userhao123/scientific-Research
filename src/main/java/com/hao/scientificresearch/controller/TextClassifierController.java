package com.hao.scientificresearch.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hao.scientificresearch.exception.ParamException;
import com.hao.scientificresearch.model.param.ResponseParam;
import com.hao.scientificresearch.textClassifier.TextClassifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/textClassifier")
public class TextClassifierController {

    @GetMapping("/classifier")
    public ResponseParam classifier(@RequestParam String texts){
        if(StringUtils.isBlank(texts)){
            throw new ParamException("参数不能为空");
        }
        System.out.println("分类请求");
        try {
            String s = TextClassifier.classifier(texts);
            return new ResponseParam(1,s);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ParamException("分类发生异常");
        }

    }
}
