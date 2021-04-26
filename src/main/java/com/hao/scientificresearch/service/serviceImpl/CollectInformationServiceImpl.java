package com.hao.scientificresearch.service.serviceImpl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hao.scientificresearch.entity.CollectInformation;
import com.hao.scientificresearch.entity.Project;
import com.hao.scientificresearch.entity.ProjectResearcher;
import com.hao.scientificresearch.entity.Researcher;
import com.hao.scientificresearch.exception.ParamException;
import com.hao.scientificresearch.mapper.CollectInformationMapper;
import com.hao.scientificresearch.model.enums.ProjectCategoryEnum;
import com.hao.scientificresearch.model.enums.ProjectLevelEnum;
import com.hao.scientificresearch.model.param.ProjectInformationParam;
import com.hao.scientificresearch.model.param.UserInformationParam;
import com.hao.scientificresearch.model.resp.CollectInformationResp;
import com.hao.scientificresearch.model.resp.FieldResp;
import com.hao.scientificresearch.model.resp.UserInformationResp;
import com.hao.scientificresearch.service.ICollectInformationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hao.scientificresearch.service.IProjectResearcherService;
import com.hao.scientificresearch.service.IProjectService;
import com.hao.scientificresearch.service.IResearcherService;
import com.hao.scientificresearch.utils.FieldListUtil;
import com.hao.scientificresearch.utils.FieldMapUtil;
import com.hao.scientificresearch.vo.DataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 信息收集表 服务实现类
 * </p>
 *
 * @author hao
 * @since 2021-04-24
 */
@Service
public class CollectInformationServiceImpl extends ServiceImpl<CollectInformationMapper, CollectInformation> implements ICollectInformationService {

    @Autowired
    private IResearcherService researcherService;
    @Autowired
    private IProjectService projectService;
    @Autowired
    private IProjectResearcherService projectResearcherService;

    @Override
    public boolean add(HttpSession session, String collectName, Integer informationType, String fieldStr) {
        if (ObjectUtil.isEmpty(fieldStr)) {
            throw new ParamException("参数不能为空");
        }
        //拼接字符串，存储各字段拼接的字符串
        StringBuilder sb = new StringBuilder();
        String projectName = null;
        if (informationType == 1) {
            UserInformationParam param = JSONUtil.toBean(fieldStr, UserInformationParam.class);
            if (param.getCollege() != null) sb.append(param.getCollege()).append(",");
            if (param.getEmail() != null) sb.append(param.getEmail()).append(",");
            if (param.getMajor() != null) sb.append(param.getMajor()).append(",");
            if (param.getPhone() != null) sb.append(param.getPhone()).append(",");
            if (param.getTitle() != null) sb.append(param.getTitle()).append(",");
            if (param.getUsername() != null) sb.append(param.getUsername()).append(",");
        } else if (informationType == 0) {
            ProjectInformationParam param = JSONUtil.toBean(fieldStr, ProjectInformationParam.class);
            if (param.getCategory() != null) sb.append(param.getCategory()).append(",");
            if (param.getLeaderName() != null) sb.append(param.getLeaderName()).append(",");
            if (param.getLevel() != null) sb.append(param.getLevel()).append(",");
            if (param.getState() != null) sb.append(param.getState()).append(",");
            if(param.getDescribed() !=null) sb.append(param.getDescribed()).append(",");
            if(param.getProjectName() == null) throw new ParamException("项目名不能为空");
            projectName = param.getProjectName();
        }

        if ("".equals(sb.toString())) {
            throw new ParamException("请至少选择一个要收集的字段");
        }
        System.out.println("接收的参数名:" + collectName);
        System.out.println("接收的参数类型:" + informationType);
        System.out.println("接收的参数字段:" + fieldStr);
        CollectInformation information1;
        //获取各用户
        String[] split = collectName.split(",");
        checkCollectName(split);
        if(informationType == 0){
            checkProjectMember(split,projectName);
        }
        System.out.println("姓名字符数组:" + Arrays.toString(split));
        if (split.length > 0) {
            for (String str : split) {
                CollectInformation one = this.getOne(Wrappers.lambdaQuery(CollectInformation.class).eq(CollectInformation::getUsername, str).eq(CollectInformation::getInformationType, informationType));
                if (one != null) {
                    this.removeById(one);
                }
                information1 = new CollectInformation();
                information1.setFieldName(sb.toString());
                information1.setInformationType(informationType);
                information1.setUsername(str);
                information1.setProjectName(projectName);
                information1.setIsWrited(false);
                Object loginUser = session.getAttribute("loginUser");
                if(loginUser instanceof Researcher){
                    information1.setCollector(((Researcher) loginUser).getName());
                }
                System.out.println("组装的实体:" + information1.toString());
                this.save(information1);
            }
        }
        return true;
    }

    @Override
    public List<CollectInformationResp> getInfoListByUserName(String username) {
        if (username == null) {
            throw new ParamException("参数不能为空");
        }
        CollectInformationResp resp = null;
        List<CollectInformation> list1 = this.list(Wrappers.lambdaQuery(CollectInformation.class).eq(CollectInformation::getUsername, username));
        List<CollectInformationResp> respList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(list1)) {
            for (CollectInformation collectInformation : list1) {
                resp = new CollectInformationResp();
                resp.setInformationType(collectInformation.getInformationType());
                String[] split = collectInformation.getFieldName().split(",");
                List<FieldResp> list = new ArrayList<>();
                Map<String, String> map = null;
                if (collectInformation.getInformationType() == 0) {
                    map = FieldMapUtil.projectMap;
                } else if (collectInformation.getInformationType() == 1) {
                    map = FieldMapUtil.userMap;
                }

                if (split.length > 0 && CollectionUtil.isNotEmpty(map)) {
                    for (String str : split) {
                        list.add(new FieldResp(map.get(str), str));
                    }
                }
                resp.setFieldName(list);
                respList.add(resp);
            }
        }

        return respList;
    }

    @Override
    public CollectInformationResp getInfoByUserName(String username, Integer informationType) {
        if (username == null) {
            throw new ParamException("参数不能为空");
        }
        CollectInformationResp resp = null;
        CollectInformation one = this.getOne(Wrappers.lambdaQuery(CollectInformation.class).eq(CollectInformation::getUsername, username).eq(CollectInformation::getInformationType, informationType));

        if (one != null) {
            resp = new CollectInformationResp();
            resp.setInformationType(informationType);
            String[] split = one.getFieldName().split(",");
            List<FieldResp> list = new ArrayList<>();
            Map<String, String> map = null;
            if (informationType == 0) {
                map = FieldMapUtil.projectMap;
                String projectName = one.getProjectName();
                list.add(new FieldResp("projectName",projectName));
            } else if (informationType == 1) {
                map = FieldMapUtil.userMap;
            }
            if (split.length > 0 && CollectionUtil.isNotEmpty(map)) {
                for (String str : split) {
                    list.add(new FieldResp(map.get(str), str));
                }
            }
            resp.setFieldName(list);

        }

        return resp;
    }

    @Override
    public boolean updateInfo(Integer informationType, String infoStr, String name) {
        Researcher researcher = researcherService.getOne(Wrappers.lambdaQuery(Researcher.class).eq(Researcher::getName, name));
        if (researcher == null) {
            throw new ParamException("无此用户的信息");
        }
        if (informationType == 1) {
            UserInformationParam param = JSONUtil.toBean(infoStr, UserInformationParam.class);
            if (param.getCollege() != null) researcher.setCollege(param.getCollege());
            if (param.getEmail() != null) researcher.setEmail(param.getEmail());
            if (param.getMajor() != null) researcher.setMajor(param.getMajor());
            if (param.getPhone() != null) researcher.setPhone(param.getPhone());
            if (param.getTitle() != null) researcher.setTitle(param.getTitle());
            if (param.getUsername() != null) researcher.setUsername(param.getUsername());
            return researcherService.updateById(researcher);
        }else if(informationType == 0){
            ProjectInformationParam param = JSONUtil.toBean(infoStr, ProjectInformationParam.class);
            Project project = projectService.getOne(Wrappers.lambdaQuery(Project.class).eq(Project::getName, param.getProjectName()));
            if(param.getDescribed() !=null) project.setDescribed(param.getDescribed());
            if(param.getLevel() != null) project.setLevel(ProjectLevelEnum.getCode(param.getLevel()));
            if(param.getCategory()!=null) project.setCategory(ProjectCategoryEnum.getCode(param.getCategory()));
            if(param.getLeaderName()!=null) {
                Researcher one = researcherService.getOne(Wrappers.lambdaQuery(Researcher.class).eq(Researcher::getName, param.getLeaderName()));
                project.setLeaderId(one.getId());
            }
            return projectService.updateById(project);
        }

        return true;
    }

    @Override
    public Page<UserInformationResp> pageByParam(HttpSession session,int page, int limit) {
        Object loginUser = session.getAttribute("loginUser");
        String loginUsername = null;
        if(loginUser instanceof Researcher){
            loginUsername = ((Researcher) loginUser).getName();
        }
        LambdaQueryWrapper<CollectInformation> wrapper = Wrappers.lambdaQuery(CollectInformation.class).eq(CollectInformation::getInformationType, 1).eq(CollectInformation::getCollector, loginUsername);
        Page<CollectInformation> page1 = this.page(new Page<>(page, limit), wrapper);
        List<CollectInformation> records = page1.getRecords();
        if (CollectionUtil.isEmpty(records)) {
            throw new ParamException("查询无数据");
        }
        //组装返回对象
        Page<UserInformationResp> respPage = new Page<>(page1.getCurrent(), page1.getSize(), page1.getTotal());
        List<UserInformationResp> userInformationRespList = new ArrayList<>();
        //查询用户表
        Map<String, Researcher> researcherMap = researcherService.list().stream().collect(Collectors.toMap(Researcher::getName, r -> r));
        for (CollectInformation c : records) {
            UserInformationResp resp = change2resp(c, researcherMap.get(c.getUsername()));
            if (resp != null) {
                userInformationRespList.add(resp);
            }
        }
        if (CollectionUtil.isNotEmpty(userInformationRespList)) {
            respPage.setRecords(userInformationRespList);
        }
        return respPage;
    }

    private UserInformationResp change2resp(CollectInformation col, Researcher researcher) {
        UserInformationResp resp = null;
        if (col != null) {
            String fieldName = col.getFieldName();
            String[] split = fieldName.split(",");
            resp = new UserInformationResp();
            for (String str : split) {
                switch (str) {
                    case "email":
                        resp.setEmail(researcher.getEmail());
                        break;
                    case "phone":
                        resp.setPhone(researcher.getPhone());
                        break;
                    case "college":
                        resp.setCollege(researcher.getCollege());
                        break;
                    case "title":
                        resp.setTitle(researcher.getTitle());
                        break;
                    case "major":
                        resp.setMajor(researcher.getMajor());
                        break;
                    case "username":
                        resp.setUsername(researcher.getUsername());
                        break;
                }
            }
            resp.setName(researcher.getName());
        }
        return resp;
    }

    private void checkCollectName(String[] name) {
        Map<String, Researcher> researcherMap = researcherService.list().stream().collect(Collectors.toMap(Researcher::getName, r -> r));
        for (String str : name) {
            if (researcherMap.get(str) == null) {
                throw new ParamException("存在未注册用户,不能其收集信息");
            }
        }
    }

    private void checkProjectMember(String[] name,String projectName){
        Project project = projectService.getOne(Wrappers.lambdaQuery(Project.class).eq(Project::getName, projectName));
        if(project==null){
            throw new ParamException("项目不存在");
        }
        List<ProjectResearcher> list = projectResearcherService.list(Wrappers.lambdaQuery(ProjectResearcher.class).eq(ProjectResearcher::getProjectId, project.getId()));
        List<Integer> researcherIds = list.stream().map(ProjectResearcher::getResearcherId).collect(Collectors.toList());
        for(String str:name){
            Researcher researcher = researcherService.getOne(Wrappers.lambdaQuery(Researcher.class).eq(Researcher::getName, str));
            boolean b = researcherIds.contains(researcher.getId());
            if(!b){
                throw new ParamException("有用户不是该项目成员");
            }
        }
    }
}
