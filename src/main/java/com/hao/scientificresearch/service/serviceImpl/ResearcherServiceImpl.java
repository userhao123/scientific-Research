package com.hao.scientificresearch.service.serviceImpl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hao.scientificresearch.entity.Researcher;
import com.hao.scientificresearch.exception.ParamException;
import com.hao.scientificresearch.mapper.ResearcherMapper;
import com.hao.scientificresearch.model.enums.EducationEnum;
import com.hao.scientificresearch.model.param.LoginParam;
import com.hao.scientificresearch.model.param.ResearcherSearchParam;
import com.hao.scientificresearch.model.resp.ResearchResp;
import com.hao.scientificresearch.service.IResearcherService;
import com.hao.scientificresearch.utils.convert.ResearcherConvert;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 科研人员信息表 服务实现类
 * </p>
 *
 * @author hao
 * @since 2021-03-31
 */
@Service
public class ResearcherServiceImpl extends ServiceImpl<ResearcherMapper, Researcher> implements IResearcherService {

    @Override
    public List<ResearchResp> getList() {
        List<Researcher> list = this.list();
       return ResearcherConvert.change2List(list);
    }

    @Override
    public Page<ResearchResp> pageByParam(int page, int limit, ResearcherSearchParam condition) {
        LambdaQueryWrapper<Researcher> wrapper = Wrappers.lambdaQuery(Researcher.class);
        if(ObjectUtil.isNotEmpty(condition)){
            wrapper.like(condition.getUsername()!=null&&!condition.getUsername().equals(""),Researcher::getUsername,condition.getUsername())
                   .like(condition.getName()!=null&&!condition.getName().equals(""),Researcher::getName,condition.getName())
                   .eq(condition.getCollege()!=null&&!condition.getCollege().equals(""),Researcher::getCollege,condition.getCollege())
                   .eq(condition.getEducation()!=null&&!condition.getEducation().equals(""),Researcher::getEducation, EducationEnum.getCode(condition.getEducation()))
                   .eq(condition.getMajor()!=null&&!condition.getMajor().equals(""),Researcher::getMajor,condition.getMajor())
                   .eq(condition.getTitle()!=null&&!condition.getTitle().equals(""),Researcher::getTitle,condition.getTitle());
        }
        Page<Researcher> page1 = this.page(new Page<>(page, limit), wrapper);
        Page<ResearchResp> respPage = new Page<>(page1.getCurrent(), page1.getSize(), page1.getTotal());
        List<Researcher> records = page1.getRecords();
        if(!CollectionUtil.isEmpty(records)){
            List<ResearchResp> researchResps = ResearcherConvert.change2List(records);
            respPage.setRecords(researchResps);
        }
        return respPage;
    }


    @Override
    public boolean add(Researcher param) {
        int count = this.count(Wrappers.lambdaQuery(Researcher.class).eq(Researcher::getUsername, param.getUsername()));
        if(count>0){
            throw new ParamException("用户名已存在!");
        }
        return this.save(param);
    }

    @Override
    public boolean delete(Integer id) {
        if(id == null){
            throw new ParamException("id不能为空");
        }
        if(this.getById(id)==null){
            throw new ParamException("删除项不存在");
        }
        return this.removeById(id);
    }

    @Override
    public boolean update(Researcher param) {
        if (param == null) {
            throw new ParamException("参数不能为空");
        }
        Researcher byId = this.getById(param.getId());
        if(!byId.getUsername().equals(param.getUsername())){
            int count = this.count(Wrappers.lambdaQuery(Researcher.class).eq(Researcher::getUsername, param.getUsername()));
            if(count>0){
                throw new ParamException("用户名已存在!");
            }
        }

        return this.updateById(param);
    }

    @Override
    public boolean login(LoginParam param, HttpSession session) {
        if(ObjectUtil.isEmpty(param)){
            throw new ParamException("用户名或密码不能为空");
        }
        LambdaQueryWrapper<Researcher> wrapper = Wrappers.lambdaQuery(Researcher.class).eq(Researcher::getUsername, param.getUsername())
                                                        .or().eq(Researcher::getPhone, param.getUsername())
                                                        .or().eq(Researcher::getEmail, param.getUsername());
        Researcher researcher = this.getOne(wrapper);
        if(researcher == null){
            throw new ParamException("该用户不存在");
        }
        if(!researcher.getPassword().equals(param.getPassword())){
            throw new ParamException("密码不正确");
        }
        session.setAttribute("loginUser",researcher);
        return true;

    }
}
