package com.guanweiming.common.utils.limit;

/**
 * @author guanweiming
 */
public class RequestLimitException extends RuntimeException {

    public RequestLimitException() {
        super("HTTP请求超出设定的限制");
    }

    public RequestLimitException(String message) {
        super(message);
    }

}