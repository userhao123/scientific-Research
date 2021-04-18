package com.hao.scientificresearch.model.enums;

public enum AchievementKindEnum {


    BASIC(0,"基础理论成果"),
    TECHNOLOGY(1,"应用技术成果"),
    SCIENCE(2,"软科学成果");

    private int code;
    private String name;

    AchievementKindEnum(int code, String name){
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
        for (AchievementKindEnum temp: AchievementKindEnum.values()){
            if(temp.getName().equals(name)) return temp.getCode();
        }
        return null;
    }

    public static String getName(int code){
        for (AchievementKindEnum temp: AchievementKindEnum.values()){
            if(temp.getCode()==code) return temp.getName();
        }
        return null;
    }
}
