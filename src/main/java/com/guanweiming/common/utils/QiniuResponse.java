package com.guanweiming.common.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.guanweiming.common.utils.ResponseCode;

import java.io.Serializable;

/**
 * @author zziaguan
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class QiniuResponse implements Serializable {

    private final String uptoken;
    private final int status;

    private QiniuResponse(int status, String uptoken) {
        this.uptoken = uptoken;
        this.status = status;
    }

    public String getUptoken() {
        return uptoken;
    }

    public int getStatus() {
        return status;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    public static QiniuResponse createBySuccess(String uptoken) {
        return new QiniuResponse(ResponseCode.SUCCESS.getCode(), uptoken);
    }


    public static QiniuResponse createByError() {
        return new QiniuResponse(ResponseCode.ERROR.getCode(), null);
    }

}
