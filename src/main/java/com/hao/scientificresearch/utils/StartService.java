package com.hao.scientificresearch.utils;

import com.hao.scientificresearch.model.resp.UrlResp;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//实现ApplicationRunner接口项目启动时自动执行指定方法
@Component
public class StartService implements ApplicationRunner {

    public static List<UrlResp> SPIDER_LIST = new ArrayList<>();

    @Override
    public void run(ApplicationArguments args) throws Exception {
       //项目启动时自动执行爬虫，爬虫科研动态数据
        SPIDER_LIST = Spider.spider_page();
    }
}
