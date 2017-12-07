package com.guanweiming.common.annotation;

/**
 * @author 关
 */
public class RequestLimitException extends Exception {

    public RequestLimitException() {
        super("Http请求超出设定的限制");
    }

    public RequestLimitException(String message) {
        super(message);
    }

}
