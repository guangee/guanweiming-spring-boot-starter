package com.guanweiming.common.cors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author https://github.com/zziaguan/
 */
@Slf4j
public class CorsFilter extends OncePerRequestFilter {

    private String maxAge;
    private String allowOrigin;
    private String allowMethods;

    CorsFilter(String maxAge, String allowOrigin, String allowMethods) {
        this.maxAge = maxAge;
        this.allowOrigin = allowOrigin;
        this.allowMethods = allowMethods;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, javax.servlet.FilterChain filterChain) throws ServletException, IOException {
        response.setHeader("Access-Control-Max-Age", maxAge);
        response.setHeader("Access-Control-Allow-Origin", allowOrigin);
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", allowMethods);
        response.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers,Authorization");
        long start = System.currentTimeMillis();
        filterChain.doFilter(request, response);
        long end = System.currentTimeMillis();
        log.debug("use time:{} ms", (end - start));
    }

}
