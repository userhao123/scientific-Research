package com.hao.scientificresearch.model.enums;

public enum EducationEnum {

    UNDERGRADUATE(0,"本科"),
    MASTER(1,"硕士"),
    DOCTER(2,"博士"),
    COLLEGE(3,"专科");

    private int code;
    private String name;

    EducationEnum(int code, String name){
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static Integer getCode(String name){
        for (EducationEnum temp:EducationEnum.values()){
            if(temp.getName().equals(name)) return temp.getCode();
        }
        return null;
    }

    public static String getName(int code){
        for (EducationEnum temp:EducationEnum.values()){
            if(temp.getCode()==code) return temp.getName();
        }
        return null;
    }

    @Override
    public String toString() {
        return "EducationEnum{" +
                "code=" + code +
                ", name='" + name + '\'' +
                '}';
    }
}
