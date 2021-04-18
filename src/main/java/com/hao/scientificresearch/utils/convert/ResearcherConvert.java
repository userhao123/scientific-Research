package com.hao.scientificresearch.utils.convert;

import cn.hutool.core.collection.CollectionUtil;
import com.hao.scientificresearch.entity.Researcher;
import com.hao.scientificresearch.model.enums.EducationEnum;
import com.hao.scientificresearch.model.param.LoginParam;
import com.hao.scientificresearch.model.resp.ResearchResp;

import java.util.ArrayList;
import java.util.List;

public class ResearcherConvert {

    public static ResearchResp change2Resp(Researcher entity){
        ResearchResp researchResp = null;
        if(entity!=null){
            researchResp = new ResearchResp();
            researchResp.setId(entity.getId());
            researchResp.setCollege(entity.getCollege());
            researchResp.setEducation(EducationEnum.getName(entity.getEducation()));
            researchResp.setEmail(entity.getEmail());
            researchResp.setGender(entity.getGender());
            researchResp.setMajor(entity.getMajor());
            researchResp.setName(entity.getName());
            researchResp.setPhone(entity.getPhone());
            researchResp.setTitle(entity.getTitle());
            researchResp.setUsername(entity.getUsername());
            researchResp.setRole(entity.getRole());
        }
        return researchResp;
    }

    public static Researcher change2entity(LoginParam param){
        Researcher researcher = null;
        if(param!=null){
            researcher = new Researcher();
            researcher.setUsername(param.getUsername());
            researcher.setPassword(param.getPassword());
        }
        return researcher;
    }

    public static List<ResearchResp> change2List(List<Researcher> entity){
        List<ResearchResp> list = null;
        if(!CollectionUtil.isEmpty(entity)){
            list = new ArrayList<>();
            for(Researcher r:entity){
                list.add(change2Resp(r));
            }
        }
        return list;
    }
}
