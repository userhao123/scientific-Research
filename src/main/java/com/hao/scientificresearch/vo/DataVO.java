package com.hao.scientificresearch.vo;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

//返回给前端的结果对象
@Data
@AllArgsConstructor
@EqualsAndHashCode
public class DataVO<T> {
    private Integer code;
    private String msgs;
    private Integer count;
    private List<T> data;


    public static <T> DataVO<T> Success(String msg, List<T> data) {
        return new DataVO<>(0, msg, data.size(), data);
    }

    public static <T> DataVO<T> Success(List<T> data) {
        return new DataVO<>(0, "数据返回成功", data.size(), data);
    }

    public static <T> DataVO<T> PageSuccess(Page<T> data) {
        return new DataVO<>(0, "数据返回成功", (int) data.getTotal(), data.getRecords());
    }

    public static <T> DataVO<T> tableSuccess(String msgs) {
        return new DataVO<>(0, msgs, 0, null);
    }



    public static <T> DataVO<T> Fail(String msg) {
        return new DataVO<>(4000, msg, 0, null);
    }

    public static <T> DataVO<T> Fail() {
        return new DataVO<>(4000, "数据返回失败", 0, null);
    }

    public static <T> DataVO<T> Fail(Integer code,String msg){
        return new DataVO<>(code,msg,0,null);
    }
}
