package com.guanweiming.common.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

/**
 * @author guanweiming
 */
@Data
public class Result<T> implements Serializable {
    private String msg;
    private int status;
    private T data;

    private Result(int status) {
        this.status = status;
    }

    private Result(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private Result(int status, T data) {
        this.status = status;
        this.data = data;
    }

    private Result(int status, String msg, T data) {
        this.msg = msg;
        this.status = status;
        this.data = data;
    }


    @JsonIgnore
    public boolean isSuccess() {
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    public String getMsg() {
        return msg;
    }

    public int getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }


    public static <T> Result<T> createBySuccess() {
        return new Result<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> Result<T> createBySuccess(T data) {
        return new Result<T>(ResponseCode.SUCCESS.getCode(), data);
    }

    public static <T> Result<T> createBySuccessMessage(String msg) {
        return new Result<T>(ResponseCode.SUCCESS.getCode(), msg);
    }

    public static <T> Result<T> createBySuccess(String msg, T data) {
        return new Result<T>(ResponseCode.SUCCESS.getCode(), msg, data);
    }

    public static <T> Result<T> createByError() {
        return new Result<T>(ResponseCode.ERROR.getCode());
    }

    public static <T> Result<T> createByErrorMessage(String msg) {
        return new Result<T>(ResponseCode.ERROR.getCode(), msg);
    }

    public static <T> Result<T> createBySimple(boolean success, T data, String msg) {
        return success ? createBySuccess(data) : createByErrorMessage(msg);
    }

    public static <T> Result<T> createByErrorCodeMessage(int errorCode, String msg) {
        return new Result<T>(errorCode, msg);
    }

    public static <T> Result<T> createByNeedLogin() {
        return new Result<T>(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
    }

    public static <T> Result<T> createByIllegalArgument() {
        return new Result<T>(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
    }

    public static <T> Result<T> createByAccessDenied(String msg) {
        return new Result<T>(ResponseCode.ACCESS_DENIED.getCode(), ResponseCode.ACCESS_DENIED.getDesc() + ":" + msg);
    }
}
