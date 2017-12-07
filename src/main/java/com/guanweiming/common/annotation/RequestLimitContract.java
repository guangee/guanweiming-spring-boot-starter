package com.guanweiming.common.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;


/**
 * @author 关
 */
@Aspect
@Component
public class RequestLimitContract {

    private final RedisTemplate<String, String> redisTemplate;


    @Autowired
    public RequestLimitContract(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Before("within(@org.springframework.web.bind.annotation.RestController *) && @annotation(limit)")
    public void requestLimit(final JoinPoint joinPoint, RequestLimit limit) throws RequestLimitException {

        int sum=10;


        Object[] args = joinPoint.getArgs();
        HttpServletRequest request = null;

        for (Object arg : args) {
            if (arg instanceof HttpServletRequest) {
                request = (HttpServletRequest) arg;
                break;
            }
        }

        if (request == null) {
            throw new RequestLimitException("方法中缺失HttpServletRequests参数");
        }
        String ip = request.getRemoteAddr();
        String url = request.getRequestURL().toString();
        String key = "req_limit_".concat(url).concat(ip);
        long count = redisTemplate.opsForValue().increment(key, 1);
        if (count == 1) {
            redisTemplate.expire(key, limit.time(), TimeUnit.MILLISECONDS);
        }
        if (count > limit.count()) {
            throw new RequestLimitException();
        }
    }

}
