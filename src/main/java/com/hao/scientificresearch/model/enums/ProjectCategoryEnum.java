package com.hao.scientificresearch.model.enums;

public enum ProjectCategoryEnum {

    BASIC(0,"基础研究"),
    APPLICATION(1,"应用研究"),
    EXPERIMENT(2,"实验与发展");

    private int code;
    private String name;

    ProjectCategoryEnum(int code, String name){
        this.code=code;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public static Integer getCode(String name){
        for (ProjectCategoryEnum temp: ProjectCategoryEnum.values()){
            if(temp.getName().equals(name)) return temp.getCode();
        }
        return null;
    }

    public static String getName(int code){
        for (ProjectCategoryEnum temp: ProjectCategoryEnum.values()){
            if(temp.getCode()==code) return temp.getName();
        }
        return null;
    }
}
