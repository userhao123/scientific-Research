package com.hao.scientificresearch.utils.convert;

import cn.hutool.core.collection.CollectionUtil;
import com.hao.scientificresearch.entity.Achievement;
import com.hao.scientificresearch.model.enums.AchievementKindEnum;
import com.hao.scientificresearch.model.param.AchievementParam;
import com.hao.scientificresearch.model.resp.AchievementResp;
import com.hao.scientificresearch.utils.GenNumber;

import java.util.ArrayList;
import java.util.List;

public class AchievementConvert {

    public static AchievementResp change2resp(Achievement entity){
        AchievementResp resp = null;
        if(entity!=null){
            resp = new AchievementResp();
            resp.setDescribed(entity.getDescribed());
            resp.setForm(entity.getForm());
            resp.setGetTime(entity.getGetTime());
            resp.setKind(AchievementKindEnum.getName(entity.getKind()));
            resp.setName(entity.getName());
            resp.setProjectId(entity.getProjectId());
            resp.setNumber(entity.getNumber());
            resp.setId(entity.getId());
        }
        return resp;
    }


    public static Achievement change2entity(AchievementParam entity){
        Achievement resp = null;
        if(entity!=null){
            resp = new Achievement();
            resp.setDescribed(entity.getDescribed());
            resp.setForm(entity.getForm());
            resp.setGetTime(entity.getGetTime());
            resp.setKind(entity.getKind());
            resp.setName(entity.getName());
            resp.setId(entity.getId());
            resp.setNumber(GenNumber.genNum("ach"));
        }
        return resp;
    }

    public static List<AchievementResp> change2List(List<Achievement> entity){
        List<AchievementResp> list = null;
        if(!CollectionUtil.isEmpty(entity)){
            list = new ArrayList<>();
            for(Achievement r:entity){
                list.add(change2resp(r));
            }
        }
        return list;
    }

}
