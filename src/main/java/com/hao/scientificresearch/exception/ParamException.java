package com.hao.scientificresearch.exception;

//自定义参数异常类
public class ParamException extends RuntimeException {

    private String message;

    public ParamException(){
        super();
    }

    public ParamException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
