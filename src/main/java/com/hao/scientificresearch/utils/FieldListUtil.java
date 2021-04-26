package com.hao.scientificresearch.utils;

import com.hao.scientificresearch.model.resp.FieldResp;

import java.util.ArrayList;
import java.util.List;

public class FieldListUtil {

    public static final List<FieldResp> userList = initUserList();
    public static final List<FieldResp> projectList = initProjectList();


    private static List<FieldResp> initUserList(){
        List<FieldResp> userList = new ArrayList<>();
        userList.add(new FieldResp("用户名","username"));
        userList.add(new FieldResp("职称","title"));
        userList.add(new FieldResp("电话","phone"));
        userList.add(new FieldResp("学院","college"));
        userList.add(new FieldResp("专业","major"));
        userList.add(new FieldResp("邮箱","email"));
        return userList;
    }

    private static List<FieldResp> initProjectList(){
        List<FieldResp> projectList = new ArrayList<>();
        projectList.add(new FieldResp("项目阶段","state"));
        projectList.add(new FieldResp("项目负责人","leaderName"));
        projectList.add(new FieldResp("项目类别","category"));
        projectList.add(new FieldResp("项目级别","level"));
        projectList.add(new FieldResp("项目描述","described"));
        return projectList;
    }
}
