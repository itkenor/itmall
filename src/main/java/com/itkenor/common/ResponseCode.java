package com.itkenor.common;

/**
 * @PACKAGE_NAME:com.itkenor.common
 * @Auther: itkenor
 * @Date: 2018/4/28 16:07
 * @Description:
 */
public enum  ResponseCode {

    SUCCESS(0,"SUCCESS"),
    ERROR(1,"ERROR"),
    NEED_LOGIN(10,"NEED_LOGIN"),
    ILLEGAL_ARUMENT(2,"ILLEGAL_ARUMENT");

    private final int code;
    private final String desc;

    ResponseCode(int code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public int getCode(){
        return this.code;
    }
    public String getDesc(){
        return this.desc;
    }
}
