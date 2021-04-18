package com.hao.scientificresearch.model.enums;

public enum ProjectLevelEnum {

    SCHOOL(0,"校级"),
    CITY(1,"市级"),
    PROVINCE(2,"省级"),
    COUNTRY(3,"国家级");

    private int code;
    private String name;

    ProjectLevelEnum(int code, String name){
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
        for (ProjectLevelEnum temp: ProjectLevelEnum.values()){
            if(temp.getName().equals(name)) return temp.getCode();
        }
        return null;
    }

    public static String getName(int code){
        for (ProjectLevelEnum temp: ProjectLevelEnum.values()){
            if(temp.getCode()==code) return temp.getName();
        }
        return null;
    }
}
