package com.itkenor.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * @PACKAGE_NAME:com.itkenor.common
 * @Auther: itkenor
 * @Date: 2018/4/28 15:56
 * @Description:通用的数据端响应对象
 */
//该注解表示在序列化对象时，如果对象为null，key也会消失
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServerResponse<T> implements Serializable {

    private int status;
    private String msg;
    private T data;


    /**
     *
     * @param status
     */
    private ServerResponse(int status){
        this.status = status;
    }

    /**
     *
     * @param status
     * @param data
     */
    private ServerResponse(int status , T data){
        this.status = status;
        this.data = data;
    }

    /**
     *
     * @param status
     * @param msg
     */
    private ServerResponse(int status , String msg){
        this.status = status;
        this.msg = msg;
    }

    /**
     * @param status
     * @param msg
     * @param data
     */
    private ServerResponse(int status , String msg , T data){
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    //该注解表示忽略该成员的序列化
    @JsonIgnore
    public boolean isSuccess(){
        return this.status==ResponseCode.SUCCESS.getCode();
    }

    //获取属性值
    public int getStatus(){
        return this.status;
    }
    public String getMsg(){
        return this.msg;
    }
    public T getData(){
        return this.data;
    }

    /**
     * 该方法用于创建私有的、带int类参数的构造函数
     * 用来保存<T>的的状态
     * @param <T>
     * @return
     */
    public static <T> ServerResponse<T> createBySuccess(){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode());
    }

    /**
     * 当<T>的类型为string时
     * 该方法可以用来区分调用私有构造函数
     * 调用ServerResponse(int status , String msg)
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> ServerResponse<T> createBySuccessMessage(String msg){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg);
    }

    /**
     * 当<T>的类型为string时
     * 该方法可以用来区分调用私有构造函数
     * 调用ServerResponse(int status , T data)
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ServerResponse<T> createBySuccess(T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),data);
    }

    /**
     * 根据正确码，填充自定义的提示信息
     * 将正确码以及提示信息，数据填入构造函数中
     * @param msg
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ServerResponse<T> createBySuccess(String msg , T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg,data);
    }

    /**
     * 根据错误码返回错误信息
     * 将错误码以及错误信信息填入构造函数中
     * @param <T>
     * @return
     */
    public static <T> ServerResponse<T> createByError(){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
    }

    /**
     * 根据错误码创建自定义的错误提示
     * 将错误码以及错误信信息填入构造函数中
     * @param errorMessage
     * @param <T>
     * @return
     */
    public static <T> ServerResponse<T> createByError(String errorMessage){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),errorMessage);
    }

    /**
     * 自定义错误码即错误信息
     * 将错误码以及错误信信息填入构造函数中
     * @param errorCode
     * @param errorMessage
     * @param <T>
     * @return
     */
    public static <T> ServerResponse<T> createByErrorCodeMessage(int errorCode,String errorMessage){
        return new ServerResponse<T>(errorCode,errorMessage);
    }
}
