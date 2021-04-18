package com.hao.scientificresearch.utils.convert;

import cn.hutool.core.collection.CollectionUtil;
import com.hao.scientificresearch.entity.Award;
import com.hao.scientificresearch.model.enums.AwardLevelEnum;
import com.hao.scientificresearch.model.param.AwardParam;
import com.hao.scientificresearch.model.resp.AwardResp;

import java.util.ArrayList;
import java.util.List;

public class AwardConvert {

    public static AwardResp change2resp(Award entity){
        AwardResp resp = null;
        if(entity!=null){
            resp = new AwardResp();
            resp.setAwardUnit(entity.getAwardUnit());
            resp.setLevel(AwardLevelEnum.getName(entity.getLevel()));
            resp.setGetTime(entity.getGetTime());
            resp.setName(entity.getName());
            resp.setProjectId(entity.getProjectId());
            resp.setId(entity.getId());
        }
        return resp;
    }

    public static Award change2entity(AwardParam param){
        Award award = null;
        if(param!=null){
            award = new Award();
            award.setAwardUnit(param.getAwardUnit());
            award.setGetTime(param.getGetTime());
            award.setLevel(param.getLevel());
            award.setName(param.getName());
            award.setId(param.getId());
        }
        return award;
    }

    public static List<AwardResp> change2List(List<Award> entity){
        List<AwardResp> list = null;
        if(!CollectionUtil.isEmpty(entity)){
            list = new ArrayList<>();
            for(Award r:entity){
                list.add(change2resp(r));
            }
        }
        return list;
    }

}
