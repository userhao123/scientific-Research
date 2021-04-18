package com.hao.scientificresearch.model.enums;

public enum AwardLevelEnum {


    FIRST(0,"一等奖"),
    SECOND(1,"二等奖"),
    THIRD(2,"三等奖");

    private int code;
    private String name;

    AwardLevelEnum(int code, String name){
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
        for (AwardLevelEnum temp: AwardLevelEnum.values()){
            if(temp.getName().equals(name)) return temp.getCode();
        }
        return null;
    }

    public static String getName(int code){
        for (AwardLevelEnum temp: AwardLevelEnum.values()){
            if(temp.getCode()==code) return temp.getName();
        }
        return null;
    }
}
