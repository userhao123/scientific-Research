package com.hao.scientificresearch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HtmlController {

    //单体应用中的请求控制，返回视图
    @GetMapping("/{url}")
    public String url(@PathVariable String url) {
        return url;
    }

    @GetMapping("/page/{url1}")
    public String url2(@PathVariable(name = "url1") String url1) {
        System.out.println("路径:" + "   " + url1);
        return "/page/" + url1;
    }

    @GetMapping("/page/{url1}/{url2}")
    public String url2(@PathVariable(name = "url1") String url1, @PathVariable(name = "url2") String url2) {
        System.out.println("路径:" + "   " + url2);
        return "/page/" + url1 + "/" + url2.substring(0, url2.length() - 5);
    }

    @GetMapping("/page/{url1}/{url2}/{url3}")
    public String url2(@PathVariable(name = "url1") String url1, @PathVariable(name = "url2") String url2, @PathVariable(name = "url3") String url3) {
        System.out.println("路径:" + "   " + url3);
        return "/page/" + url1 + "/" + url2 + "/" + url3.substring(0, url3.length() - 5);
    }
}
