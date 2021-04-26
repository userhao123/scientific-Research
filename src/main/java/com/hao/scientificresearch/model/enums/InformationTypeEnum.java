package com.hao.scientificresearch.model.enums;

public enum InformationTypeEnum {

    PROJECT_TABLE(0,"科研信息表"),
    RESEARCHER_TABLE(1,"用户信息表");

    private int code;
    private String table;

    InformationTypeEnum(int code,String table){
        this.code = code;
        this.table = table;
    }

    public int getCode() {
        return code;
    }

    public String getTable() {
        return table;
    }


    public static Integer getCode(String name){
        for (InformationTypeEnum temp: InformationTypeEnum.values()){
            if(temp.getTable().equals(name)) return temp.getCode();
        }
        return null;
    }

    public static String getName(int code){
        for (InformationTypeEnum temp: InformationTypeEnum.values()){
            if(temp.getCode()==code) return temp.getTable();
        }
        return null;
    }



}
