package com.guanweiming.common.services;

import com.guanweiming.common.utils.HttpKit;
import com.guanweiming.common.utils.Result;
import com.guanweiming.common.utils.limit.RequestLimitException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author guanweiming
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result<String> handleBanRequest(Exception e) {
        HttpServletRequest request = HttpKit.getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            log.info("{}:{}", key, request.getHeader(key));
        }
        log.error("系统错误:{}", HttpKit.getRequest().getRequestURI(), e);
        return Result.createByErrorMessage("系统错误" + e.getMessage());
    }



    @ExceptionHandler(RequestLimitException.class)
    public Result<String> bizException(RequestLimitException e) {
        log.debug("请求频率限制:{}", e.getMessage());
        return Result.createByErrorMessage(e.getMessage());
    }


    /**
     * 参数校验不通过
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return Result.createByErrorMessage("参数校验错误:" + StringUtils.join(errors, ","));
    }
}
