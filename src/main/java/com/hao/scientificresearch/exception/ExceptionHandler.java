package com.hao.scientificresearch.exception;

import com.hao.scientificresearch.vo.DataVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(ParamException.class)
    public DataVO<String> paramException(ParamException e){
        if(StringUtils.isBlank(e.getMessage())){
            return DataVO.Fail("参数异常");
        }
        if(e.getMessage().equals("查询无数据")){
            return DataVO.tableSuccess("无数据");
        }
        return DataVO.Fail(e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NoLoginException.class)
    public DataVO<String> noLoginException(NoLoginException e){
        if(StringUtils.isBlank(e.getMessage())){
            return DataVO.Fail(1000,"未登录，请先登录");
        }
        return DataVO.Fail(1000,e.getMessage());
    }
}
