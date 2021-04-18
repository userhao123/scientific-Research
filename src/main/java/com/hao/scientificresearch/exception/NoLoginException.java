package com.hao.scientificresearch.exception;

public class NoLoginException extends RuntimeException {

    private String message;

    public NoLoginException(){
        super();
    }

    public NoLoginException(String message){
        this.message=message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
