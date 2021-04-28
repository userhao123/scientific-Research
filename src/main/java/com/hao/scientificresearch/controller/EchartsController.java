package com.hao.scientificresearch.controller;

import com.hao.scientificresearch.model.resp.EchartsCountResp;
import com.hao.scientificresearch.service.IAchievementService;
import com.hao.scientificresearch.service.IAwardService;
import com.hao.scientificresearch.service.IProjectService;
import com.hao.scientificresearch.service.IResearcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/echarts")
public class EchartsController {
    @Autowired
    private IProjectService projectService;
    @Autowired
    private IResearcherService researcherService;
    @Autowired
    private IAwardService awardService;
    @Autowired
    private IAchievementService achievementService;


    @GetMapping("/showNumber")
    public EchartsCountResp showNumber(){
        int count1 = projectService.count();
        int count2 = researcherService.count();
        int count3 = awardService.count();
        int count4 = achievementService.count();
        return new EchartsCountResp(count1, count2, count3, count4);
    }

}
