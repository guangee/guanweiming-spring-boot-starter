package com.guanweiming.common.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zziaguan
 */
@AllArgsConstructor
@Getter
public enum ResponseCode {
    SUCCESS(0, "SUCCESS"),
    ERROR(1, "ERROR"),
    NEED_LOGIN(10, "NEED_LOGIN"),
    ACCESS_DENIED(3, "权限不足"),
    ILLEGAL_ARGUMENT(2, "ILLEGAL_ARGUMENT");


    private final int code;
    private final String desc;
}
