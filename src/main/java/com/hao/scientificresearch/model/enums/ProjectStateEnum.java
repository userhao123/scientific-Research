package com.hao.scientificresearch.model.enums;

public enum  ProjectStateEnum {
    NEW(0,"新建"),
    START(1,"立项"),
    MIDDLE(2,"中期检查"),
    FINISH(3,"结题"),
    DELAY(4,"延期");

    private int code;
    private String name;

    ProjectStateEnum(int code,String name){
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
        for (ProjectStateEnum temp:ProjectStateEnum.values()){
            if(temp.getName().equals(name)) return temp.getCode();
        }
        return null;
    }

    public static String getName(int code){
        for (ProjectStateEnum temp:ProjectStateEnum.values()){
            if(temp.getCode()==code) return temp.getName();
        }
        return null;
    }
}
