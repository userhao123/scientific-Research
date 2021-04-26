package com.hao.scientificresearch.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hao.scientificresearch.entity.CollectInformation;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hao.scientificresearch.model.resp.CollectInformationResp;
import com.hao.scientificresearch.model.resp.UserInformationResp;
import com.hao.scientificresearch.vo.DataVO;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 信息收集表 服务类
 * </p>
 *
 * @author hao
 * @since 2021-04-24
 */
public interface ICollectInformationService extends IService<CollectInformation> {

    boolean add(HttpSession session, String username, Integer informationType, String fieldStr);

    List<CollectInformationResp> getInfoListByUserName(String username);

    CollectInformationResp getInfoByUserName(String username,Integer informationType);

    boolean updateInfo(Integer informationType,String infoStr,String name);

    Page<UserInformationResp> pageByParam(HttpSession session,int page, int limit);

}
