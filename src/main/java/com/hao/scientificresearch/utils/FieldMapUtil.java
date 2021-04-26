package com.hao.scientificresearch.utils;

import java.util.HashMap;
import java.util.Map;

public class FieldMapUtil {

    public static Map<String,String> projectMap = initProjectMap();

    public static Map<String,String> userMap = initUserMap();

    private static Map<String,String> initUserMap(){
        Map<String,String> userMap1 = new HashMap<>();
        userMap1.put("username","用户名");
        userMap1.put("title","职称");
        userMap1.put("phone","电话");
        userMap1.put("college","学院");
        userMap1.put("major","专业");
        userMap1.put("email","邮箱");
        return userMap1;
    }

    private  static Map<String,String> initProjectMap(){
        Map<String,String> projectMap1 = new HashMap<>();
        projectMap1.put("projectName","项目名");
        projectMap1.put("state","项目阶段");
        projectMap1.put("leaderName","项目负责人");
        projectMap1.put("category","项目类别");
        projectMap1.put("level","项目等级");
        projectMap1.put("described","项目描述");
        return projectMap1;
    }

}
