package com.guanweiming.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author https://github.com/zziaguan/
 */
@ConfigurationProperties("tools.cors")
public class CorsProperties {
    private String maxAge = "3600";
    private String allowOrigin = "*";
    private String allowMethods = "GET,HEAD,OPTIONS,POST,PUT,DELETE";

    public String getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(String maxAge) {
        this.maxAge = maxAge;
    }

    public String getAllowOrigin() {
        return allowOrigin;
    }

    public void setAllowOrigin(String allowOrigin) {
        this.allowOrigin = allowOrigin;
    }

    public String getAllowMethods() {
        return allowMethods;
    }

    public void setAllowMethods(String allowMethods) {
        this.allowMethods = allowMethods;
    }
}
